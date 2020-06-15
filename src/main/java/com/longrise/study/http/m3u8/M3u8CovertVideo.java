package com.longrise.study.http.m3u8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * 将得到m3u8文件通过ffmpeg下载合并成想要得文件
 */
public class M3u8CovertVideo {
    private static String ffmpeg = "cmd.exe /c start D:\\Users\\Administrator\\ffmpeg\\bin/ffmpeg.exe -i %s %s";
    private static String src = System.getProperty("user.dir") + "\\src\\main\\resources\\";

    public static void main(String[] args) {
        String osrc = "D:\\Videos\\hlm\\";
        File file = new File(src, "hlm.m3u8");
        try (
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {
            String row;
            int i = 0;
            while ((row = bufferedReader.readLine()) != null && (++i > 0)) {
                Runtime run = Runtime.getRuntime();
                String shell = String.format(ffmpeg, row, osrc + i + ".mp4");
                System.out.println(shell);
                Process p = run.exec(shell);
                try (
                    InputStreamReader in = new InputStreamReader(p.getInputStream(), Charset.forName("GBK")); 
                    BufferedReader inBr = new BufferedReader(in)
                ) {
                    String lineStr;
                    while ((lineStr = inBr.readLine()) != null) {
                        System.out.println(lineStr);
                    }
                    if (p.waitFor() != 0 || p.exitValue() != 0) { // 按照惯例，值0表示正常终止。
                        System.err.println("命令执行失败!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
/**
 * cmd /c dir 是执行完dir命令后关闭命令窗口。
 * 
 * cmd /k dir 是执行完dir命令后不关闭命令窗口。
 * 
 * cmd /c start dir 会打开一个新窗口后执行dir指令，原窗口会关闭。
 * 
 * cmd /k start dir 会打开一个新窗口后执行dir指令，原窗口不会关闭。
 */