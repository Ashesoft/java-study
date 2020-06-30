package com.longrise.study.http.htmls;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class JxURL {
    private String src = System.getProperty("user.dir") + "/src/main/resources/";

    public void createHTMLFile(String srcs, Node node) {
        File file = new File(srcs, "hlmtemp.txt");
        File hlm = new File(srcs, "/hlm/" + node.name);
        try (FileReader in = new FileReader(file);
                BufferedReader reader = new BufferedReader(in);
                FileWriter out = new FileWriter(hlm);
                BufferedWriter writer = new BufferedWriter(out);) {
            String r;
            while ((r = reader.readLine()) != null) {
                r = r.replace("@title", node.title).replace("@content", node.content);
                writer.write(r);
                writer.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getContent(Node node) {
        URI uri = URI.create(node.path);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
        try {
            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
            try (InputStream in = response.body();) {
                String html = new String(in.readAllBytes(), "GBK");
                Document doc = Jsoup.parse(html);
                Elements elements = doc.select("#f_article p");
                node.content = elements.toString();
                this.createHTMLFile(src, node);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class Node {
        private String name;
        private String title;
        private String content;
        private String path;

        Node(String name, String title, String path) {
            this.name = name;
            this.title = title;
            this.path = path;
        }
    }

    public void readURLTxt(String path) {
        File file = new File(path, "url.txt");
        try (FileReader reader = new FileReader(file); BufferedReader buffer = new BufferedReader(reader);) {
            String row;
            while ((row = buffer.readLine()) != null) {
                String[] arys = row.split(" ==> ");
                Node node = new Node(arys[0], arys[1], arys[2]);
                this.getContent(node);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JxURL jxURL = new JxURL();
        jxURL.readURLTxt(jxURL.src);
    }
}