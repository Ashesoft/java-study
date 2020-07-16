package com.longrise.stream.reactive;

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow.Processor;
import java.util.concurrent.Flow.Subscription;

/**
 * MyProcessor 需要进程 SubmissionPublisher 类同时实现 Processor 接口
 * MyProcessor 既是发布者(Publisher)也是订阅者(Subscriber), 它起到一个承上启下的作用
 */
public class MyProcessor extends SubmissionPublisher<Integer> implements Processor<String, Integer> {
    
    private Subscription subscription;
    
    @Override
    public void onSubscribe(Subscription subscription) {
        (this.subscription = subscription).request(1); // 保存订阅关系, 并请求一条消息
    }

    @Override
    public void onNext(String item) {
        System.out.printf("myProcessor 接受到的消息是:%s%n", item);
        System.out.printf("myProcessor 消息被处理后是:%s%n", item.length());
        this.submit(item.length()); // 将处理后的消息发送给下一个订阅者
        this.subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        this.subscription.cancel();
    }

    @Override
    public void onComplete() {
        System.err.println("myProcessor 处理完成了");
    }

    
}