package com.longrise.study.http.link;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.concurrent.Executors;

/**
 * jdk11 HttpClient的学习
 * 
 * HttpURLConnection目前存在的问题
 * 1. 其基类URLConnection当初是设计为支持多协议的, 但其中大多已经成为非主流(ftp, gopher, ...)
 * 2. API的设计早于Http/1.1, 过度抽象
 * 3. 难以使用, 存在许多没有文档化的行为
 * 4. 它只支持阻塞模式(每个请求/响应占用一个线程)
 * 
 * HttpClient特性
 * 1. 以可扩展的面向对象的结构实现了HTTP全部的方法(GET, POST, PUT, DELETE, HEAD, OPTIONS, TRACE)
 * 2. 支持HTTPS协议
 * 3. 通过HTTP代理建立透明的连接
 * 4. 连接管理器支持多线程应用, 支持设置最大连接数, 同时支持蛇者每个主机的最大连接数, 发现并关闭过期的连接
 * 5. 自动处理Set-Cookie中的Cookie
 * 6. 插件式的自定义Cookie策略
 * 7. request的输出流可以避免流中内容直接缓冲到socket服务器
 * 8. response的输入流可以有效的从socket服务器直接读取相应内容
 */
public class HttpClientDemo {

    public void syncGet(){
        //HttpClient client = HttpClient.newHttpClient(); // 快速创建HttpClient实例

        // 创建客户端
        HttpClient client = HttpClient.newBuilder() // 创建builder并链式调用
        .version(Version.HTTP_2) // http 协议版本1.1或2
        .connectTimeout(Duration.ofMillis(5000)) // 连接超时时间
        .followRedirects(Redirect.NEVER) // 连接完成之后的转发策略
        .executor(Executors.newFixedThreadPool(5)) // 指定线程池
        // .authenticator(Authenticator.getDefault()) // 认证, 默认情况下 Authenticator.getDefault() 是 null 值, 会报错
        // .proxy(ProxySelector.getDefault()) // 代理地址
        // .cookieHandler(CookieHandler.getDefault()) // 缓存, 默认情况下 CookieHandler.getDefault() 是 null 值, 会报错
        .build(); // 创建完成
        
        // 创建请求对象
        HttpRequest request = HttpRequest.newBuilder()
        .header("Content-Type", "application.json") // 存入消息头, 消息头是保存在一张 TreeMap 里的
        .version(Version.HTTP_2) // http 协议版本
        .uri(URI.create("http://cn.bing.com")) // url 地址
        .timeout(Duration.ofMillis(5100)) // 请求超时时间
        .POST(BodyPublishers.ofString("hello")) // 发起一个 post 请求, 需要存入一个消息体
        // .GET() // 发起一个 get 请求, 不需要消息体
        // .method("POST", BodyPublishers.ofString("hello")) // 此方法是 POST() 和 GET() 方法的底层, 效果一样
        .build(); // 创建完成

        // 客户端通过请求对象发送请求
		try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            String body = response.body();
            System.out.println(body);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
    }
}