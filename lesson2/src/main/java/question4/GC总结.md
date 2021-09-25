# GC 总结(以 GCLogAnalysis 为例)

在堆内存大小为100M的情况下，不管是哪种GC都会发生OutOfMemeroy 异常。
## Serial GC 
在内存依次从小到大进行设置，可以发现FullGC 的频次逐渐的减少，当到大1G 时，不在发生FullGC。说明了JVM GC垃圾搜集速率已经可以满足程序产生垃圾对象的速率。
当处于100M内存时，在后面几次一直处于FullGC，并且内存无法回收，且持续增长导致最后的OOM。可以看到最后几次的老年代GC 时长只有2ms，只是因为内存太小。
其引起GC的原因都是Allocation Failure,即内存分配失败。

##Parallel GC
在使用该GC时，发现发生GC的原因有些不在是Allocation Failure，而是Ergonomics。其语义是JVM 调优导致的一次GC,Parallel GC收集器是一种注重吞吐量的GC收集器，
使得应用程序的吞吐量(吞吐量=程序运行时间/（程序运行时间+GC时间）)达到一个可控的范围，可以通过最大GC停顿时间的-XX:MaxGCPauseMillis和控制吞吐量的参数-XX:GCTimeRatio进行控制，
当晋升老年代的对象的平均大小大于老年代的剩余空间大小，会进行一次Full GC。并且虚拟机会进行预估下一次需要分配的内存大小，如果预测不满足下次分配内存空间就会进行一次Full GC.
在内存设置为500M时，基本是2次Young GC ,就会触发 一次Full GC(由JVM调优引起)，而在100M时，先是调优引起的FullGC ，到最后无法回收导致Allocation Failure原因的Full GC，直到OOM.
而当内存提升至1G时，则只有young GC。

##CMS GC
在CMS 并发标记阶段，比如在100M的内存情况下，由于老年代的空间不足以容纳晋升的对象或者直接分配在老年代对象，则会出现concurrent mode failure，接着CMS 会退化Serial GC 进行一次Full GC.
在日志可以看到 CMS 收集的几个阶段：初始标记，并发标记，并发预清除，并发可中断预清除，最终标记，标记清除，标记置位。一般在CMS收集时，会进行一次年轻代收集，为了得到新生代对象对老年代对象的引用。
在500M内存时，就没有出现concurrent mode failure，说明老年代空间足以容纳晋升对象。


##G1 GC
在G1 日志中，也可以明细看到GC 的流程，首先开始的是年轻代的GC阶段（G1 Evacuation Pause）可以看到并行的工作线程是4个，先是扫描CSet中的外部root(Ext Root Scanning,包括线程栈，
jni,全局变量等)。
