package com.longrise.stream.reactive;

import java.util.Random;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

// Flow 响应式流 使用了 Processor 中间件
public class ReactiveStreamProcessorDemo {
    public static void main(String[] args) {
        // 定义发布者, 发布的数据类型是 Integer
        // 直接使用 jdk 自带的SubmissionPublisher, 它实现了 Publisher 接口
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

        // 定义中间件处理器, 将消息处理成 int 类型的
        MyProcessor processor = new MyProcessor();

        // 发布者与中间件处理器建立关系
        publisher.subscribe(processor);

        // 定义最终的订阅者, 消费 int 类型的消息
        Subscriber<Integer> subscriber = new Subscriber<>() {
            private Subscription subscription;

            /**
             * 对于给定的订阅者, 在调用任何其他 Subscriber 方法之前调用此方法
             */
            @Override
            public void onSubscribe(Subscription subscription) {
                (this.subscription = subscription).request(1);
            }

            /**
             * 订阅者下一个消息调用此方法
             */
            @Override
            public void onNext(Integer item) {
                System.out.printf("subscriber 输入的字符串的长度为:%s%n", item);
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.subscription.request(1);
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
                System.err.println("subscriber 处理完成了");
            }

        };

        // 中间件处理器与最终订阅者建立关系
        processor.subscribe(subscriber);

        // 生产数据并发送给相关订阅者   
        Random random = new Random();
        random.ints().limit(300).boxed().map(String::valueOf).forEach(publisher::submit); // submit 是个阻塞方法
        
        try {
            TimeUnit.SECONDS.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            publisher.close();
            processor.close();
        }
    }
}