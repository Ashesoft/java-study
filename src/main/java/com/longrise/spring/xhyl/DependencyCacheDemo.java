package com.longrise.spring.xhyl;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DependencyCacheDemo {

    // 初始化完毕的 Bean
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    // 正在初始化的 Bean 对应的工厂, 此时对象已经被实例化
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);
    private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);

    // 存放正在初始化的 Bean, 对象还没有被实例化之前就放进来了
    private final Set<String> singletonsCurrentlyInCreation = Collections.newSetFromMap(new ConcurrentHashMap<>(16));

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> beanClass) throws Exception {

        // 类名为 Bean 的名字
        String beanName = beanClass.getSimpleName();

        // 已经初始化好了, 或者正在初始化
        Object initObj = getSingleton(beanName, true);
        if (initObj != null) {
            return (T) initObj;
        }

        // bean 正在被初始化
        singletonsCurrentlyInCreation.add(beanName);

        // 实例化 bean
        Object object = beanClass.getDeclaredConstructor().newInstance();
        singletonFactories.put(beanName, () -> {
            return object;
        });

        // 开始初始化 bean, 即填充属性
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Class<?> fieldClass = field.getType();

            // 获取需要注入字段的 class
            field.set(object, getBean(fieldClass));
        }

        // 初始化完毕
        singletonObjects.put(beanName, object);
        singletonsCurrentlyInCreation.remove(beanName);
        earlySingletonObjects.remove(beanName);
        
        return (T) object;
    }

    /**
     * 获取对应的实例
     * 
     * @param beanName 获取实例所对应的名称
     * @param allowEarlyReference 是否允许循环依赖, 默认为 true, 当设置为false的时候, 当前项目存在循环依赖, 会启动失败
     * @return
     */
    private Object getSingleton(String beanName, boolean allowEarlyReference) {
        Object singletonObject = this.singletonObjects.get(beanName);
        if(singletonObject == null && isSingletonCurrentlyInCreation(beanName)){
            synchronized(this.singletonObjects){
                singletonObject = this.earlySingletonObjects.get(beanName);
                if (singletonObject == null && allowEarlyReference) {
                    ObjectFactory<?> singletonFactory = this.singletonFactories.get(beanName);
                    if(singletonFactory != null){
                        singletonObject = singletonFactory.getObject();
                        this.earlySingletonObjects.put(beanName, singletonObject);
                        this.singletonFactories.remove(beanName);
                    }
                }
            }
        }
        return singletonObject;
    }

    // 判断 bean 是否正在被初始化
    private boolean isSingletonCurrentlyInCreation(String beanName) {
        return this.singletonsCurrentlyInCreation.contains(beanName);
    }

}