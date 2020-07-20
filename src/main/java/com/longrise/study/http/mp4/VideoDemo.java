package com.longrise.study.http.mp4;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.Builder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;


public class VideoDemo {
    private static String src = System.getProperty("user.dir") + "/src/main/resources/";

    public static void main(String[] args) {
        Path path = Path.of(src, "经济危机及其应对政策的国际比较.txt");
        try (Stream<String> lines = Files.lines(path)) {
            lines.map(s -> {
                Map<String, String> map = new HashMap<>();
                String[] strs = s.split("==>");
                map.put("name", strs[0] + ".mp4");
                map.put("url", strs[1] + "/");
                return map;
            }).forEach(obj -> {
                downVideo(obj.get("name"), obj.get("url"), null);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void downVideo(String name, String url, String header) {
        URI uri = URI.create(url);
        String src = "D:/Videos/jj/";
        Path path = Path.of(src);
        HttpClient client = HttpClient.newBuilder().build();
        Builder builder = HttpRequest.newBuilder(uri).GET();
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
            System.out.printf("%s 开始下载%n", name);
            HttpResponse<Path> response = client.send(request, HttpResponse.BodyHandlers.ofFileDownload(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE));
            System.out.printf("%s 下载完毕%n", name);
            Path body = response.body();
            System.out.println(body);
            File file = new File(body.toUri());
            file.renameTo(new File(body.toFile().getParent(), name));
            System.out.println("重命名......");

        } catch (IOException | InterruptedException e) {
            String str =  e.getMessage();
            System.err.println(e);
            System.out.println("重新尝试......");
            url = str.split("location=\\[")[1].split("]")[0];
            header = str.split("set-cookie=\\[")[1].split("]")[0].split(";")[0];
            downVideo(name, url, header);
        }
    }
}