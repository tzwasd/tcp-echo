package com.tzwjkl.utils.repeater.tcp;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RequestRepeater {
    private final Socket socket;
    private final Mode mode;

    public RequestRepeater(Socket socket, Mode mode) {
        this.socket = socket;
        this.mode = mode;
    }

    public void repeat() {
        switch (mode) {
            case HTTP_UTF8 -> {
                final String body = catch_requestString();
                String htmlBody = "<pre>" + body + "</pre>";
                String[] headers = new String[]{
                        "HTTP/1.0 200 OK",
                        "Connection: close",
                        "Content-Type: text/html",
                        "Content-Length: " + htmlBody.length(),
                        ""};
                write_responseString(headers, htmlBody);
            }
            case TCP -> // TODO
                    System.out.println("TOBE CONTINUED...");
            case TCP_UTF8 -> write_responseString(catch_requestString());
        }
    }

    public String catch_requestString() {
        String requestString = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            while (true) {
                String line = bufferedReader.readLine();
                if (null == line || line.isEmpty()) {
                    break;
                }
                stringBuilder.append(line).append(System.lineSeparator());
            }
            requestString = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return requestString;
    }

    private void write_responseString(String body) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
            bufferedWriter.write(body);
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write_responseString(String[] headers, String htmlBody) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
            for (String header : headers) {
                bufferedWriter.write(header);
                bufferedWriter.newLine();
            }
            bufferedWriter.write(htmlBody);
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
