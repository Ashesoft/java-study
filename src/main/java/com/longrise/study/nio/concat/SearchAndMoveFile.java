package com.longrise.study.nio.concat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * 查找并移动文件
 */
public class SearchAndMoveFile {
    private static String insrc = "D:/videos/80683929/";

    private static String outsrc = "D:/videos/";

    public static void main(String[] args) {
        readFile(insrc);
    }

    /**
     * 使用递归的方法搜索目标文件
     */
    public static void readFile(String src) {
        File file = new File(src);
        File[] files = file.listFiles();
        for (File _file : files) {
            if (_file.isDirectory()) {
                readFile(src + "/" + _file.getName());
            } else {
                if (_file.getName().endsWith("1.mp4")) {
                    File f = new File(outsrc, _file.getParentFile().getName() + "王德峰《红楼梦》品读讲座精选集.mp4");
                    try (FileOutputStream out = new FileOutputStream(f)) {
                        Files.copy(_file.toPath(), out); // 拷贝文件到新的目的地
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(_file.getParentFile().getName() + _file.getName());
                }
            }
        }
    }
}