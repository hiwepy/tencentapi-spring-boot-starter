package com.tencentcloud.spring.boot.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link FileUtil}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
class FileUtilTest {

    @TempDir
    Path tempDir;

    @Test
    void readFileAsStringShouldReadFile() throws IOException {
        Path file = tempDir.resolve("test.txt");
        Files.writeString(file, "Hello World");
        String result = FileUtil.readFileAsString(file.toString());
        assertThat(result).isEqualTo("Hello World");
    }

    @Test
    void readFileAsStringShouldThrowForMissingFile() {
        assertThatThrownBy(() -> FileUtil.readFileAsString("/nonexistent/file.txt"))
                .isInstanceOf(IOException.class);
    }

    @Test
    void readFileByBytesShouldReadFile() throws IOException {
        Path file = tempDir.resolve("test.bin");
        Files.write(file, new byte[]{1, 2, 3, 4, 5});
        byte[] result = FileUtil.readFileByBytes(file.toString());
        assertThat(result).containsExactly(1, 2, 3, 4, 5);
    }

    @Test
    void readFileByBytesShouldThrowForMissingFile() {
        assertThatThrownBy(() -> FileUtil.readFileByBytes("/nonexistent/file.bin"))
                .isInstanceOf(IOException.class);
    }
}
