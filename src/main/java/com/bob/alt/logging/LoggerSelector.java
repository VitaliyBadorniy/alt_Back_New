package com.bob.alt.logging;

public class LoggerSelector {

    public Logger log(String logType, String pathDir, String fileName, int fileQuality, long fileSize) {
        Logger logger;
        switch (logType) {
            case "db":
                logger = new LogDb();
                break;
            case "file":
                logger = new LogFile(pathDir, fileName, fileQuality, fileSize);
                break;
            case "console":
                logger = new LogConsole();
                break;
            default:
                logger = new LogNot();
        }
        return logger;
    }

}
