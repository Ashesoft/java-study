package com.longrise.study.sjms.clms;

public class ChainPatternDemo {
    public static void main(String[] args) {
        AbstractLogger loggerChain = new ErrorLogger();
        loggerChain.logMessage(Level.INFO, "正常信息");
        loggerChain.logMessage(Level.DEBUG, "调试信息");
        loggerChain.logMessage(Level.ERROR, "错误信息");
    }
    /**
     * 有重复执行的情况, 这样会影响系统资源的开销
     * info = 正常信息
     * debug = 调试信息
     * info = 调试信息
     * error = 错误信息
     * debug = 错误信息
     * info = 错误信息
     */

    /**
     * 责任链模式
     * 主要解决：职责链上的处理者负责处理请求，客户只需要将请求发送到职责链上即可，无须关心请求的处理细节和请求的传递，所以职责链将请求的发送者和请求的处理者解耦了。
     * 何时使用：在处理消息的时候以过滤很多道。
     * 如何解决：拦截的类都实现统一接口。
     * 优点： 1、降低耦合度。它将请求的发送者和接收者解耦。 2、简化了对象。使得对象不需要知道链的结构。 3、增强给对象指派职责的灵活性。通过改变链内的成员或者调动它们的次序，允许动态地新增或者删除责任。 4、增加新的请求处理类很方便。
     * 缺点： 1、不能保证请求一定被接收。 2、系统性能将受到一定影响，而且在进行代码调试时不太方便，可能会造成循环调用。 3、可能不容易观察运行时的特征，有碍于除错。
     */
}