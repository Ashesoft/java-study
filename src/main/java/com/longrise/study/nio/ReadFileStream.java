package com.longrise.study.nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * 使用 stream 流的方式处理读取的文件
 */
public class ReadFileStream {
    private static String src = System.getProperty("user.dir") + "/src/main/resources/";
    public static void main(String[] args) {
        Path path = Path.of(src, "url.txt");
        try(Stream<String> lines = Files.lines(path);) {
            lines.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}