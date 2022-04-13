# Guava 缓存详解及使用

## 缓存

缓存分为`本地缓存`与`分布式缓存`。本地缓存为了保证线程安全问题，一般使用`ConcurrentMap`的方式保存在内存之中，而常见的分布式缓存则有`Redis`，`MongoDB`等。

+ 一致性: 本地缓存由于数据存储于内存之中，每个实例都有自己的副本，可能会存在不一致的情况；分布式缓存则可有效避免这种情况
+ 开销: 本地缓存会占用JVM内存，会影响GC及系统性能；分布式缓存的开销则在于网络时延和对象序列化，故主要影响调用时延
+ 适用场景: 本地缓存适用于数据量较小或变动较少的数据；分布式缓存则适用于一致性要求较高及数量量大的场景(可弹性扩容)

> 本地缓存适用于数据量较小或变动较少的数据，因为变动多需要考虑到不同实例的缓存一致性问题，而数据量大则需要考虑缓存回收策略及GC相关的问题

## Guava Cache

Guava Cache 是`Google Fuava`中的一个内存缓存模块，用于将数据缓存到`JVM`内存中。

+ 提供了get、put封装操作，能够集成数据源 ；
+ 线程安全的缓存，与`ConcurrentMap`相似，但前者增加了更多的元素失效策略，后者只能显示的移除元素；
+ Guava Cache提供了多种基本的缓存回收方式
+ 监控缓存加载/命中情况

通常，Guava缓存适用于以下情况：

+ 愿意花费一些内存来提高速度。
+ 使用场景有时会多次查询key。
+ 缓存将不需要存储超出`RAM`容量的数据

## 详细配置

### 缓存的并发级别

Guava提供了设置并发级别的API，使得缓存支持并发的写入和读取。与ConcurrentHashMap类似，Guava cache的并发也是通过分离锁实现。在通常情况下，推荐将并发级别设置为服务器cpu核心数。

```java
CacheBuilder.newBuilder()
  //  设置并发级别未cpu核心数, 默认为4
  .concurrencyLevel(Runtime.getRuntime().availableProcessors()).build();
```

### 缓存的初始容量设置

我们在构建缓存时可以为缓存设置一个合理大小初始容量，由于Guava的缓存使用了分离锁的机制，扩容的代价非常昂贵。所以合理的初始容量能够减少缓存容器的扩容次数。

```java
CacheBuilder.newBuilder().
  // 设置初始容量为100
  .initialCaacity(100).build();
```

### 设置最大存储

Guava Cache可以在构建缓存对象时指定缓存所能够存储的最大记录数量。当Cache中的记录数量达到最大值后再调用put方法向其中添加对象，Guava会先从当前缓存的对象记录中选择一条删除掉，腾出空间后再将新的对象存储到Cache中。

```java
CacheBuilder.newBuilder()
  // 设置最大容量为1000
  .maximumSize(1000).build();
```

### 缓存清除策略

+ 基于存活时间的清除策略
  1. `expireAfterWrite` 写缓存后多久过期
  2. `expireAfterAccess` 读写缓存后多久过期

  存活使劲啊策略可以单独设置或组合配置

+ 基于容量的清除策略

  通过`CacheBuilder.maximumSize(long)`方法可以设置Cache的最大容量数，当缓存数量达到或接近该最大值时，Cache将清除掉那些最近最少使用的缓存

+ 基于权重的清除策略

  使用`CacheBuilder.weigher(Weigher)`指定一个权重函数，并且用`CacheBuilder.maximumWeight(long)`指定最大总重。

> 如每一项缓存所占据的内存空间大小都不一样，可以看作它们有不同的“权重”（weights）,作为执行清除策略时优化回收的对象

```java
LoadingCache<Key, Graph> graphs = CacheBuilder.newBuilder()
  .maximumWeight(100000)
  .weigher(new Weigher<Key, Graph>() {
    public int weigh(Key k, Graph g) {
      return g.vertices().size();
    }
  })
  .build(
    new CacheLoader<Key, Graph>() {
      public Graph load(Key key) { // no checked exception
        return createExpensiveGraph(key);
      }
    }
  );

```

+ 显示清除
  1. 清除单个key: `Cache.invalidate(key)`
  2. 批量清除key: `Cache.invalidateAll(keys)`
  3. 清除所有缓存项: `Cache.invalidateAll()`

+ 基于引用的清除策略
  在构建Cache实例过程中，通过设置使用弱引用的键、或弱引用的值、或软引用的值，从而使JVM在GC时顺带实现缓存的清除
  1. `CacheBuilder.weakKeys()`：使用弱引用存储键。当键没有其它（强或软）引用时，缓存项可以被垃圾回收
  2. `CacheBuilder.weakValues()`：使用弱引用存储值。当值没有其它（强或软）引用时，缓存项可以被垃圾回收
  3. `CacheBuilder.softValues()`：使用软引用存储值。软引用只有在响应内存需要时，才按照全局最近最少使用的顺序回收。考虑到使用软引用的性能影响，我们通常建议使用更有性能预测性的缓存大小限定

> 垃圾回收仅依赖`==`恒等式，使用弱引用键的缓存用而不是`equals()`，即同一对象引用。

## Cache

显示`put`操作置入内存

```java
private static Cache<Integer, Integer> numCache = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).build();

public static void main(String[] args) throws Exception {
  System.out.println(numCache.getIfPresent(1));
  Thread.sleep(1000);
  System.out.println(numCache.getIfPresent(1));
  Thread.sleep(1000);
  numCache.put(1, 5);
  System.out.println(numCache.getIfPresent(1));
  // console: null null 5
}
```

## LoadingCache

使用自定义`ClassLoader`加载数据，置入内存中。从`LoadingCache`中获取数据时，若数据存在则直接返回；若数据不存在，则根据`ClassLoader`的`load`方法加载数据至内存，然后返回该数据

```java
private static LoadingCache<Integer,Integer> numCache = CacheBuilder.newBuilder()
    .expireAfterWrite(5L, TimeUnit.MINUTES)
    .maximumSize(5000L)
    .build(new CacheLoader<Integer, Integer>() {
      @Override
      public Integer load(Integer key) throws Exception {
          System.out.println("no cache");
          return key * 5;
      }
    });

public static void main(String[] args) throws Exception {
  System.out.println(numCache.get(1));
  Thread.sleep(1000);
  System.out.println(numCache.get(1));
  Thread.sleep(1000);
  numCache.put(1, 6);
  System.out.println(numCache.get(1));
  // console: 5 5 6
}
```
