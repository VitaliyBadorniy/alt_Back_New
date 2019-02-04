package com.bob.alt.logging;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LogFileTest {

    @Test
    public void writeOk() throws IOException {
        String data = "Hello World!";
        final ByteBuffer buffer = ByteBuffer.wrap(data.getBytes());
        String pathFile = "./target.txt";
        int bytes = 0;
        try (FileChannel channel = FileChannel.open(Paths.get(pathFile), StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
            while (buffer.hasRemaining()) {
                bytes += channel.write(buffer);
            }
            assertEquals("Valid amount of bytes written", data.getBytes().length, bytes);
            Files.delete(Paths.get(pathFile));
        }

    }

}