package com.bob.alt.logging;

public interface Logger {

    void log(Object o, Level level, String message);

    void log(Object o, Level level, String message, String throwable);

}
