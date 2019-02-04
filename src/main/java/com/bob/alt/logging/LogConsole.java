package com.bob.alt.logging;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogConsole implements Logger {

    private SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    @Override
    public void log(Object o, Level level, String message) {
        System.out.println(
                dt.format(new Date())
                        + " " + level + " "
                        + o.getClass().getName()
                        + " : " + message
        );
    }

    @Override
    public void log(Object o, Level level, String message, String throwable) {
        System.out.println(
                dt.format(new Date())
                        + " " + level + " "
                        + o.getClass().getName()
                        + " : " + message
                        + " : " + throwable
        );
    }

}
