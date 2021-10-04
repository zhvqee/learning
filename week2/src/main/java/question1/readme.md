
# GC 分析

### 参数
在分别使用SerialGC ,ParallelGC ,CMSGC ,G1GC，并且堆的内存设置不同的阶梯情况下GC情况分析
分别讲堆的大小设置为Xmx100m,Xms100m ; Xmx300m,Xms300m, Xmx500m,Xms500m , Xmx1G,Xms1G。

-XX:+UseSerialGC -XX:+PrintGCDetails -Xmx100m -Xms100m -Xloggc:/Users/zhuqi/Documents/workspace/learning/lesson2/src/main/java/question1/logs/serial_100_gc.log
-XX:+UseParallelGC -XX:+PrintGCDetails -Xmx100m -Xms100m -Xloggc:/Users/zhuqi/Documents/workspace/learning/lesson2/src/main/java/question1/logs/parallel_100_gc.log
-XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -Xmx100m -Xms100m -Xloggc:/Users/zhuqi/Documents/workspace/learning/lesson2/src/main/java/question1/logs/cms_100_gc.log
-XX:+UseG1GC -XX:+PrintGCDetails -Xmx100m -Xms100m -Xloggc:/Users/zhuqi/Documents/workspace/learning/lesson2/src/main/java/question1/logs/g1_100_gc.log


-XX:+UseSerialGC -XX:+PrintGCDetails -Xmx300m -Xms300m -Xloggc:/Users/zhuqi/Documents/workspace/learning/lesson2/src/main/java/question1/logs/serial_300_gc.log
-XX:+UseParallelGC -XX:+PrintGCDetails -Xmx300m -Xms300m -Xloggc:/Users/zhuqi/Documents/workspace/learning/lesson2/src/main/java/question1/logs/parallel_300_gc.log
-XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -Xmx300m -Xms300m -Xloggc:/Users/zhuqi/Documents/workspace/learning/lesson2/src/main/java/question1/logs/cms_300_gc.log
-XX:+UseG1GC -XX:+PrintGCDetails -Xmx300m -Xms300m -Xloggc:/Users/zhuqi/Documents/workspace/learning/lesson2/src/main/java/question1/logs/g1_300_gc.log


-XX:+UseSerialGC -XX:+PrintGCDetails -Xmx500m -Xms500m -Xloggc:/Users/zhuqi/Documents/workspace/learning/lesson2/src/main/java/question1/logs/serial_500_gc.log
-XX:+UseParallelGC -XX:+PrintGCDetails -Xmx500m -Xms500m -Xloggc:/Users/zhuqi/Documents/workspace/learning/lesson2/src/main/java/question1/logs/parallel_500_gc.log
-XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -Xmx500m -Xms500m -Xloggc:/Users/zhuqi/Documents/workspace/learning/lesson2/src/main/java/question1/logs/cms_500_gc.log
-XX:+UseG1GC -XX:+PrintGCDetails -Xmx500m -Xms500m -Xloggc:/Users/zhuqi/Documents/workspace/learning/lesson2/src/main/java/question1/logs/g1_500_gc.log


-XX:+UseSerialGC -XX:+PrintGCDetails -Xmx1g -Xms1g -Xloggc:/Users/zhuqi/Documents/workspace/learning/lesson2/src/main/java/question1/logs/serial_1g_gc.log
-XX:+UseParallelGC -XX:+PrintGCDetails -Xmx1g -Xms1g -Xloggc:/Users/zhuqi/Documents/workspace/learning/lesson2/src/main/java/question1/logs/parallel_1g_gc.log
-XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -Xmx1g -Xms1g -Xloggc:/Users/zhuqi/Documents/workspace/learning/lesson2/src/main/java/question1/logs/cms_1g_gc.log
-XX:+UseG1GC -XX:+PrintGCDetails -Xmx1g -Xms1g -Xloggc:/Users/zhuqi/Documents/workspace/learning/lesson2/src/main/java/question1/logs/g1_1g_gc.log



