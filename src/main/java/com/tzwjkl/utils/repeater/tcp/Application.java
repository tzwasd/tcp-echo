package com.tzwjkl.utils.repeater.tcp;

import java.io.IOException;

public class Application {
    public static final String usage = "Usage: java -jar tcp-repeater.jar [MODE:]PORT";

    public static void main(String[] args) throws IOException {
        if (args != null && args.length == 1) {
            // parse port and mode from command args.
            // the default mode is TCP_UTF8, you can change it in ModeAndPort.java line 30.
            ModeAndPort modeAndPort = ModeAndPort.check(args[0]);
            if (modeAndPort != null) {
                RepeatServer repeatServer = new RepeatServer(modeAndPort);
                repeatServer.serve();
                return;
            }
        }
        System.out.println(usage);
    }

}
