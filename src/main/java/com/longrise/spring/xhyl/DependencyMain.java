package com.longrise.spring.xhyl;

/**
 * 两个 Map 就能搞定循环依赖, 为什么 Spring 要用三个呢
 * 
 * 解释:
 * 当我们从 singletonFactories 中根据 BeanName 获取相应的 ObjectFactory, 然后调用 getObject() 这个方法返回对应的 Bean.
 * 在本例子中 ObjectFactory 的实现很简单, 就是将实例化好的对象直接返回, 但是在 Spring 中就没有那么简单了, 执行的过程比较复杂,
 * 为了避免每次拿到 ObjectFactory 然后调用 getObject(), 直接把 ObjectFactory 创建的对象缓存起来, 这样就能提高效率了.
 * 比如 A 依赖 B 和 C, B 和 C 又依赖 A, 如果不做缓存那么初始化 B 和 C 都会调用 A 对应的 ObjectFactory 的 getObject() 方法.
 * 如果做缓存只需要 B 或者 C 调用一次即可.
 * 
 */
public class DependencyMain {
    public static void main(String[] args) throws Exception {
        // DependencyDemo dependencyDemo = new DependencyDemo();
        // show(dependencyDemo);
        DependencyCacheDemo dependencyCacheDemo = new DependencyCacheDemo();
        cache(dependencyCacheDemo);
    }

    // 有缓存
    public static void cache(DependencyCacheDemo dependencyCacheDemo) throws Exception {
        // 假装扫描出来对象
        Class<?>[] classes = {A.class, B.class};

        // 假装项目初始化所有bean
        for (Class<?> _class : classes) {
            dependencyCacheDemo.getBean(_class);
        }
        System.out.println(dependencyCacheDemo.getBean(B.class).getA() == dependencyCacheDemo.getBean(A.class)); // true
        System.out.println(dependencyCacheDemo.getBean(A.class).getB() == dependencyCacheDemo.getBean(B.class)); // true
    }

    // 没有缓存
    public static void show(DependencyDemo dependencyDemo) throws Exception {
        // 假装扫描出来对象
        Class<?>[] classes = {A.class, B.class};

        // 假装项目初始化所有bean
        for (Class<?> _class : classes) {
            dependencyDemo.getBean(_class);
        }
        System.out.println(dependencyDemo.getBean(B.class).getA() == dependencyDemo.getBean(A.class)); // true
        System.out.println(dependencyDemo.getBean(A.class).getB() == dependencyDemo.getBean(B.class)); // true
    }
}