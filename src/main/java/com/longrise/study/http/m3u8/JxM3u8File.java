package com.longrise.study.http.m3u8;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * m3u8文件解析
 */
public class JxM3u8File {
    private String src = System.getProperty("user.dir") + "\\src\\main\\resources\\";

    /**
     * 获取包含m3u8列表文件
     */
    public void getM3u8File() {
        File file = new File(src + "hlm.m3u8");
        if (file.isDirectory()) {
            return;
        }
        try (FileReader fReader = new FileReader(file); BufferedReader bufferedReader = new BufferedReader(fReader)) {
            String row;
            int i = 0;
            while ((row = bufferedReader.readLine()) != null && (++i >0 )) {
                getM3u8Content(row, i);
                reFileName(row, i);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改m3u8文件部分信息
     */
    // http://hls.cntv.myalicdn.com/asp/hls/450/0303000a/3/default/20ebbf9f9ded46ad4c7bcb8745e82bd8/450.m3u8
    public void reFileName(String path, int id) {
        File ofile = new File(src + "m3u8s", id + ".m3u8");
        File nfile = new File(src + "m3u8ss", id + ".m3u8");
        path = path.substring(0, path.length() - 8);
        // System.out.println(path);
        try (FileReader fReader = new FileReader(ofile);
                BufferedReader bufferedReader = new BufferedReader(fReader);
                FileWriter outputStream = new FileWriter(nfile);
                BufferedWriter bufferedWriter = new BufferedWriter(outputStream)) {
            String data;
            while ((data = bufferedReader.readLine()) != null) {
                if (!data.startsWith("#")) {
                    data = path + data;
                }
                System.out.println(data);
                bufferedWriter.write(data);
                bufferedWriter.newLine();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取网络上m3u8文件内容
     * @param path 文件地址
     * @param id 序号
     */
    public void getM3u8Content(String path, int id) {
        File file = new File(src + "m3u8s", id + ".m3u8");
        URI uri = URI.create(path);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
        System.out.println(uri.getPath());
        System.out.println(file.getName());
        try {
            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
            try (OutputStream out = new FileOutputStream(file); InputStream in = response.body()) {
                out.write(in.readAllBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(final String[] args) {
        JxM3u8File jxM3u8File = new JxM3u8File();
        jxM3u8File.getM3u8File();
    }
}