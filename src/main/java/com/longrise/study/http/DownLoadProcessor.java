package com.longrise.study.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 文件下载进度实现
 */
public class DownLoadProcessor {
    public static void download(String uri, File outFile, String header) {
        HttpClient client = HttpClient.newBuilder().build();
        Builder builder = HttpRequest.newBuilder(URI.create(uri)).GET();
        if(Objects.nonNull(header)){
            builder.setHeader("Cookie", header);
        }
        builder.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        builder.setHeader("Accept-Encoding", "gzip, deflate");
        builder.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6");
        builder.setHeader("DNT", "1");
        builder.setHeader("Upgrade-Insecure-Requests", "1");
        builder.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36 Edg/84.0.522.40");

        HttpRequest request = builder.build();
        try {
            HttpResponse<InputStream> response = client.send(request, BodyHandlers.ofInputStream());
            int statusCode = response.statusCode(); // 获取请求的状态码
            if (statusCode >= 200 && statusCode < 300) {
                try (InputStream in = response.body(); OutputStream out = new FileOutputStream(outFile)) {
                    int len = 0;
                    byte[] data = new byte[409600];
                    long progres = 0, // 累存每次请求文件的大小
                    // 获取请求文件的大小, 并切分成100份
                    contentLen = response.headers().firstValueAsLong("content-length").getAsLong() / 100;
                    while ((len = in.read(data)) != -1) {
                        out.write(data, 0, len);
                        progres += len;
                        long bfb = progres / contentLen; // 用累存的大小除以100份每份的大小求得文件下载进度
                        System.out.printf("%s正在下载中...%s%%%n", outFile.getName(), bfb);
                    }
                    System.out.printf("%s下载完毕", outFile.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println("服务器异常重新尝试......");
                Map<String, List<String>> map = response.headers().map();
                uri = map.get("location").get(0);
                header = map.get("set-cookie").get(0);
                download(uri, outFile, header);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        File file = new File("E:/", "out.mp4");
        download("http://cs.ananas.chaoxing.com/download/f8dc25b2c2807d0f7a9d5bf9c44f6031", file, null);
    }
}