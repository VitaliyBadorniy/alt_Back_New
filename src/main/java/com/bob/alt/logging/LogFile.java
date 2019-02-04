package com.bob.alt.logging;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LogFile implements Logger {

    private String pathDir;
    private String fileName;
    private SimpleDateFormat dtl;
    private SimpleDateFormat dtf;
    private int fileQuality;
    private int curNum = 1;
    private long fileSize;
    private List<Path> pathList;
    private boolean isInit;
    private boolean isRollover;

    LogFile(String pathDir, String fileName, int fileQuality, long fileSize) {
        this.pathDir = pathDir;
        this.fileName = fileName;
        this.fileQuality = fileQuality;
        this.fileSize = fileSize;
        init();
    }

    @Override
    public void log(Object o, Level level, String message) {
        String data = dtl.format(new Date())
                + " : " + level
                + " : " + o.getClass().getName()
                + " : " + message
                + "\n";
        writeToFile(data);
    }

    @Override
    public void log(Object o, Level level, String message, String throwable) {
        String data = dtl.format(new Date())
                + " : " + level
                + " : " + o.getClass().getName()
                + " : " + message
                + " : " + throwable
                + "\n";
        writeToFile(data);
    }

    private void init() {

        dtl = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        dtf = new SimpleDateFormat("dd-MM-yyyy");

        if (!isInit) {
            if (fileQuality > 1) {

                isRollover = true;

                Optional<Path> maxPath = getPaths(pathDir).stream()
                        .max(Comparator.comparing(p -> getNum(p.getFileName().toString())));
                if (maxPath.isPresent() && (curNum < getNum(maxPath.get().getFileName().toString()))) {
                    curNum = getNum(maxPath.get().getFileName().toString());
                }
            }
            isInit = true;
        }
    }

    private void writeToFile(String data) {
        final ByteBuffer buffer = ByteBuffer.wrap(data.getBytes());
        String pathFile = pathDir + fileName + "_" + dtf.format(new Date()) + "_" + curNum + ".log";
        Path path = Paths.get(pathFile);
        if (Files.exists(path)) {
            try (FileChannel channel = FileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.APPEND)) {
                channel.write(buffer);

                if (isRollover) {
                    long curFileSize = channel.size();
                    if (curFileSize >= fileSize) {
                        if (curNum >= fileQuality) {
                            curNum = 1;
                        } else {
                            curNum++;
                        }

                        List<Path> pathOptional = getPaths(pathDir).stream()
                                .filter(p -> curNum == getNum(p.getFileName().toString()))
                                .collect(Collectors.toList());

                        for (Path p : pathOptional) {
                            pathFile = pathDir + p.getFileName().toString();
                            Files.delete(Paths.get(pathFile));
                        }

                    }
                }

            } catch (IOException e) {
                System.out.println("Error write to file.");
            }
        } else {
            try (FileChannel channel = FileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
                channel.write(buffer);
            } catch (IOException e) {
                System.out.println("Error write to file.");
            }
        }
    }

    private List<Path> getPaths(String pathDir) {
        try {
            pathList = Files.find(Paths.get(pathDir), 1,
                    (p, bfa) -> bfa.isRegularFile()
                            && p.getFileName().toString().matches(fileName + ".*\\.log"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Not found dir.");
        }
        return pathList;
    }

    private int getNum(String name) {
        int pos = 0;//позиция в тексте
        int q = 0;//количество совпадений
        Pattern pattern = Pattern.compile("_");
        for (int i = 0; i < name.length(); i++) {
            String s = String.valueOf(name.charAt(i));
            Matcher m = pattern.matcher(s);
            if (m.matches()) {
                pos = i;
                if (q > 0) {
                    break;
                }
                q++;
            }
        }
        String num = name.substring(pos + 1, name.length() - 4).trim();
        return Integer.valueOf(num);
    }
}
