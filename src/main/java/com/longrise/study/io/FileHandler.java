package com.longrise.study.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class FileHandler{
    private String omdir = "D:\\Downloads\\StoneStory\\";
    private String nmdir = "D:\\Downloads\\StoneStory\\new\\";
    private String cmd = "D:\\Users\\Administrator\\ffmpeg\\bin/ffmpeg -v quiet -i %s -metadata title=\"%s\" -metadata artist=\"%s\" -metadata album=\"%s\" %s";

    public void readFile(){
        String fileName = this.getClass().getResource("/hlm.txt").getPath();
        File file = new File(fileName);
        try(FileReader fr = new FileReader(file);BufferedReader br = new BufferedReader(fr)){
            String data = null;
            Runtime run = Runtime.getRuntime();
            while ((data = br.readLine()) != null) {
                String[] row = data.split(",");
                String shell = String.format(cmd, omdir + row[0], row[1], row[2], row[3], nmdir + row[0]);
                System.err.println(shell);

                Process p = run.exec(shell); // ping www.baidu.com
                try (
                    InputStreamReader in = new InputStreamReader(p.getInputStream(), Charset.forName("GBK")); 
                    BufferedReader inBr = new BufferedReader(in)
                ) {
                    String lineStr;
                    while ((lineStr = inBr.readLine()) != null) {
                        System.out.println(lineStr);
                    }
                    if (p.waitFor() != 0 || p.exitValue() == 1) {
                        System.err.println("命令执行失败!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new FileHandler().readFile();
    }
}