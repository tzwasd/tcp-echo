package com.tzwjkl.utils.repeater.tcp;

public class ModeAndPort {

    private Mode mode;
    private int port;

    public Mode getMode() {
        return mode;
    }

    public int getPort() {
        return port;
    }

    public static ModeAndPort check(String arg) {
        ModeAndPort modeAndPort = new ModeAndPort();
        if (arg != null) {
            String port_str;
            if (arg.startsWith("http:")) {
                modeAndPort.mode = Mode.HTTP_UTF8;
                port_str = arg.substring(5);
            } else if (arg.startsWith("tcp:")) {
                modeAndPort.mode = Mode.TCP;
                port_str = arg.substring(4);
            } else if (arg.startsWith("tcp-utf8:")) {
                modeAndPort.mode = Mode.TCP_UTF8;
                port_str = arg.substring(9);
            } else {
                modeAndPort.mode = Mode.TCP_UTF8;
                port_str = arg;
            }
            if (port_str.matches("\\d{1,5}")) {
                int port = Integer.parseInt(port_str);
                if (port > 0 && port < 65536) {
                    modeAndPort.port = port;
                    return modeAndPort;
                }
            }
        }
        return null;
    }

    private ModeAndPort() {
    }

}
enum Mode {
    TCP, TCP_UTF8, HTTP_UTF8
}
