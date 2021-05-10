package com.tzwjkl.utils.repeater.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RepeatServer {
    private final int port;
    private final Mode mode;

    public RepeatServer(ModeAndPort modeAndPort) {
        port = modeAndPort.getPort();
        mode = modeAndPort.getMode();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void serve() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("RepeatServer is serve on " + port + "...");
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("connected from " + socket.getRemoteSocketAddress());
            new Thread(() -> new RequestRepeater(socket, mode).repeat()).start();
        }
    }

}
