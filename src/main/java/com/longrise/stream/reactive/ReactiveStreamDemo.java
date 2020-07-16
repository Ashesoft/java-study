package com.longrise.stream.reactive;

import java.util.Random;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.stream.IntStream;

// Flow 响应式流
public class ReactiveStreamDemo {
    public static void main(String[] args) {
        // 定义发布者, 发布的数据类型是 Integer
        // 直接使用 jdk 自带的SubmissionPublisher, 它实现了 Publisher 接口
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();

        // 定义订阅者, 实现 Subscriber 接口
        Subscriber<Integer> subscriber = new Subscriber<>() {
            private Subscription subscription;

            /**
             * 对于给定的订阅者, 在调用任何其他 Subscriber 方法之前调用此方法
             */
            @Override
            public void onSubscribe(Subscription subscription) {
                this.subscription = subscription; // 保存订阅关系, 需要用它来对发布者进行响应
                this.subscription.request(1); // 请求拿到一条消息
            }

            /**
             * 订阅者下一个消息调用此方法
             */
            @Override
            public void onNext(Integer item) {
                System.out.printf("%s接收到的消息是:%s%n", Thread.currentThread().getName(), item); // 对接受到的数据进行相关处理
                this.subscription.request(2); // 处理后继续请求拿到两条消息

                // 已经达到结果的目的, 也可以直接使用 cancel 告诉发布者不在接受数据了
                // this.subscription.cancel();
            }

            /**
             * 在 Publisher 或 Subscriber 遇到不可恢复的错误时调用此方法, 之后 Subscription 不会再调用 Subscriber 其他的方法
             * 如果 Publisher 遇到不允许将消息发送给 Subscriber 的错误, 则 Subscriber 会收到 onError 消息, 然后不会再接受其它消息了
             */
            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                this.subscription.cancel();
            }

            /**
             * 当已知不会再额外调用 Subscriber 的方法, 且没有发生有错误而导致终止订阅, 调用此方法.
             * 之后 Subscription 不会调用其它 Subscriber 的方法
             * 当知道没有更多的信息发送给订阅者时, 订阅者会受到 onComplete 方法.    
             */
            @Override
            public void onComplete() {
                System.out.println("处理完了");
            }

        };

        // 发布者和订阅者建立订阅关系
        publisher.subscribe(subscriber);

        // 生产数据并发布
        IntStream.range(1, 100).forEach(publisher::submit);
        Random random = new Random();
        random.ints(5, 100000, 1000000).forEach(publisher::submit);

        // 结束后关闭发布者
        publisher.close();
        try {
            TimeUnit.SECONDS.sleep(2); // 睡眠两秒钟, 以等待订阅者执行结束
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}