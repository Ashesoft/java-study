package com.longrise.study.proxy.cglib;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.Enhancer;

public class CglibMain {
    public static void main(String[] args) throws Exception {
        Message message = new Message(); // 目标对象
        Enhancer enhancer = new Enhancer(); // 负责执行代理操作的对象
        enhancer.setSuperclass(message.getClass()); // 设置一个父类
        enhancer.setCallback(new CglibProxy(message)); // 设置代理类
        // Message proxy = (Message) enhancer.create(); // 创建代理对象
        // proxy.send();
        Object create = enhancer.create();
        Method method = create.getClass().getDeclaredMethod("send");
        method.invoke(create);
    }
    
}