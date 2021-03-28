##### SerialGC
* 串行。它与应用线程的执行是串行的，也就是说，执行应用线程的时候，不会执行GC，执行GC的时候，不能执行应用线程。使用的是分代算法，在新生代上，Serial使用复制算法进行收集，在老年代上，Serial使用标记-压缩算法进行收集。
  * 优点：回收算法简单，所以回收效率高。在单线程下，可以很大程度的减少调度带来的系统开销。
  * 缺点：GC线程会阻塞所有用户线程

##### ParallelGC

* parallel GC 使用多线程进行年轻代收集，Serial的多线程版本。

```
-XX:ParallelGCThreads=<线程数目>
```

* 运行例子:

```java
java -Xmx12m -Xms3m -Xmn1m -XX:PermSize=20m -XX:MaxPermSize=20m
       -XX:+UseParallelGC -jar D:\.....
```

##### Concurrent Mark Sweep (CMS)

* CMS 是一种希望最小化响应时间的垃圾收集算法，GC的某些步骤可以与应用线程并行的进行，它的收集周期为：
  * 初始标记(CMS-initial-mark) ：标记 Roots 能直接引用到的对象
  * 并发标记(CMS-concurrent-mark)：进行 GC Root Tracing
  * 重新标记(CMS-remark) ：修正并发标记期间由于用户程序运行而导致的变动
  * 并发清除(CMS-concurrent-sweep)：进行清除工作
* 初始标记和重新标记会导致 *stop the world*。由于最耗时的并发标记和并发清除都可以和用户程序同时进行，所以其实可以认为 GC 和用户程序是同时进行的。
* 缺陷：
  * 由于 GC 和用户程序同时进行，可能会有部分新产生的垃圾无法被直接回收，需要等到下一次 GC 时再回收
  * CPU 资源敏感，会占用一部分的CPU资源导致用户线程停顿，总吞吐量会下降。
* 相关参数：
  * 启用CMS：-XX:+UseConcMarkSweepGC。
  * CMS默认回收线程数目是 (ParallelGCThreads + 3)/4) ，ParallelGCThreads是年轻代并行收集线程数目；如果你需要明确设定，可以通过-XX:ParallelCMSThreads=2来设
  * CMS阶段整理内存：-XX:+UseCMSCompactAtFullCollection；
  * 设置每多少次CMS后进行一次内存整理：-XX:+CMSFullGCsBeforeCompaction=<次数>
  * 老年代进行CMS时的内存消耗百分比，默认为68：-XX:CMSInitiatingOccupancyFraction=75

##### G1 GC

Java 堆的内存布局与其他的收集器有很大区别，它将整个 Java 堆划分成多个大小相等的独立区域（Region），虽然还是有年轻代和老年代的概念，但是新生代和老年代不是物理隔离的，它们都是一部分 Region的集合，并且这些Region无需在物理上连续。建议机器内存较大时，且其他GC已经无法满足需求可以考虑使用G1

* G1 垃圾回收的过程有下面几步：
  * 初始标记（Initial Marking）
  * 并发标记（Concurrent Marking）
  * 最终标记（Final Marking）
  * 筛选回收（Live Data Counting and Evacuation）
* 相关参数：
  * -XX:+UseG1GC    使用G1GC
  * -XX:MaxGCPauseMillis=n  最大暂停时间，jvm不保证做到 单位为毫秒
  * -XX:InitiatingHeapOccupancyPercent=n    启动一个并发垃圾收集周期所需要达到的整堆占用比例。这个比例是指整个堆的占用比例而不是某一个代，如果这个值是0则代表‘持续做GC’。默认值是45
  * -XX:MaxTenuringThreshold=n 经过多少轮minor GC，对象会进入老年代
  * -XX:ParallelGCThreads=n 并行阶段使用线程数
  * -XX:ConcGCThreads=n 并发垃圾收集的线程数目
  * -XX:G1ReservePercent=n  防止晋升老年代失败而预留的空闲区域的数目
  * -XX:G1HeapRegionSize=n  空闲区域大小 最小1Mb 最大 32Mb.

##### 总结：不同的应用有各自的应用场景，调整参数要综合吞吐量，反应时间两方面考虑，以最终的测试结果为准





