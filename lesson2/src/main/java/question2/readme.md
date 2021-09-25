
压测 gateway-server-0.0.1-SNAPSHOT.jar

jvm 堆
在分别使用SerialGC ,ParallelGC ,CMSGC ,G1GC，并且堆的内存设置不同的阶梯情况下GC情况分析
分别讲堆的大小设置为Xmx100m,Xms100m ; Xmx300m,Xms300m, Xmx500m,Xms500m , Xmx1G,Xms1G。

压测命令为 wrk -t12 -c100 -d30s  http://127.0.0.1:8088/api/hello


-XX:+UseSerialGC -XX:+PrintGCDetails -Xmx100m -Xms100m -Xloggc:/Users/zhuqi/Documents/workspace/learning/lesson2/src/main/java/question2/logs/serial_100_gc.log
结果:
Running 30s test @ http://127.0.0.1:8088/api/hello
12 threads and 100 connections
Thread Stats   Avg      Stdev     Max   +/- Stdev
Latency    39.26ms   82.77ms 867.21ms   88.55%
Req/Sec     1.14k   601.34     4.55k    60.91%
405044 requests in 30.07s, 48.36MB read
Requests/sec:  13469.28
Transfer/sec:      1.61MB
======================================================================================================================================================================================


-XX:+UseParallelGC -XX:+PrintGCDetails -Xmx100m -Xms100m -Xloggc:/Users/zhuqi/Documents/workspace/learning/lesson2/src/main/java/question2/logs/parallel_100_gc.log
Running 30s test @ http://127.0.0.1:8088/api/hello
12 threads and 100 connections
Thread Stats   Avg      Stdev     Max   +/- Stdev
Latency    58.38ms  109.82ms   1.01s    87.31%
Req/Sec   784.14    545.37     3.66k    56.64%
275965 requests in 30.10s, 32.95MB read
Requests/sec:   9167.59
Transfer/sec:      1.09MB
======================================================================================================================================================================================




-XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -Xmx100m -Xms100m -Xloggc:/Users/zhuqi/Documents/workspace/learning/lesson2/src/main/java/question2/logs/cms_100_gc.log
Running 30s test @ http://127.0.0.1:8088/api/hello
12 threads and 100 connections
Thread Stats   Avg      Stdev     Max   +/- Stdev
Latency    35.42ms   81.42ms 789.72ms   89.81%
Req/Sec     1.28k   662.06     3.70k    59.94%
456169 requests in 30.08s, 54.46MB read
Requests/sec:  15165.73
Transfer/sec:      1.81MB
======================================================================================================================================================================================






-XX:+UseG1GC -XX:+PrintGCDetails -Xmx100m -Xms100m -Xloggc:/Users/zhuqi/Documents/workspace/learning/lesson2/src/main/java/question2/logs/g1_100_gc.log
Running 30s test @ http://127.0.0.1:8088/api/hello
12 threads and 100 connections
Thread Stats   Avg      Stdev     Max   +/- Stdev
Latency    55.44ms  112.89ms   1.18s    87.85%
Req/Sec     1.01k   609.88     3.93k    58.45%
355983 requests in 30.09s, 42.50MB read
Requests/sec:  11832.00
Transfer/sec:      1.41MB
======================================================================================================================================================================================



-XX:+UseSerialGC -XX:+PrintGCDetails -Xmx300m -Xms300m -Xloggc:/Users/zhuqi/Documents/workspace/learning/lesson2/src/main/java/question2/logs/serial_300_gc.log
Running 30s test @ http://127.0.0.1:8088/api/hello
12 threads and 100 connections
Thread Stats   Avg      Stdev     Max   +/- Stdev
Latency    39.89ms   85.04ms   1.06s    89.19%
Req/Sec     1.11k   660.91     3.30k    54.35%
393407 requests in 30.09s, 46.97MB read
Requests/sec:  13072.85
Transfer/sec:      1.56MB
======================================================================================================================================================================================





-XX:+UseParallelGC -XX:+PrintGCDetails -Xmx300m -Xms300m -Xloggc:/Users/zhuqi/Documents/workspace/learning/lesson2/src/main/java/question2/logs/parallel_300_gc.log
Running 30s test @ http://127.0.0.1:8088/api/hello
12 threads and 100 connections
Thread Stats   Avg      Stdev     Max   +/- Stdev
Latency    49.30ms   90.93ms 937.46ms   87.29%
Req/Sec   778.32    448.63     4.39k    58.56%
275756 requests in 30.08s, 32.92MB read
Requests/sec:   9166.58
Transfer/sec:      1.09MB
======================================================================================================================================================================================



-XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -Xmx300m -Xms300m -Xloggc:/Users/zhuqi/Documents/workspace/learning/lesson2/src/main/java/question2/logs/cms_300_gc.log
Running 30s test @ http://127.0.0.1:8088/api/hello
12 threads and 100 connections
Thread Stats   Avg      Stdev     Max   +/- Stdev
Latency    53.33ms   97.52ms 897.18ms   86.55%
Req/Sec   823.19    529.36     4.17k    53.93%
291178 requests in 30.08s, 34.76MB read
Requests/sec:   9678.68
Transfer/sec:      1.16MB
======================================================================================================================================================================================



-XX:+UseG1GC -XX:+PrintGCDetails -Xmx300m -Xms300m -Xloggc:/Users/zhuqi/Documents/workspace/learning/lesson2/src/main/java/question2/logs/g1_300_gc.log
Running 30s test @ http://127.0.0.1:8088/api/hello
12 threads and 100 connections
Thread Stats   Avg      Stdev     Max   +/- Stdev
Latency    46.89ms   92.85ms 959.82ms   87.79%
Req/Sec     0.94k   546.81     3.32k    55.85%
332053 requests in 30.09s, 39.64MB read
Requests/sec:  11035.67
Transfer/sec:      1.32MB
======================================================================================================================================================================================





-XX:+UseSerialGC -XX:+PrintGCDetails -Xmx500m -Xms500m -Xloggc:/Users/zhuqi/Documents/workspace/learning/lesson2/src/main/java/question2/logs/serial_500_gc.log
Running 30s test @ http://127.0.0.1:8088/api/hello
12 threads and 100 connections
Thread Stats   Avg      Stdev     Max   +/- Stdev
Latency    34.64ms   76.08ms 734.03ms   89.27%
Req/Sec     1.33k   709.25     4.22k    58.68%
470421 requests in 30.07s, 56.16MB read
Requests/sec:  15645.93
Transfer/sec:      1.87MB
======================================================================================================================================================================================



-XX:+UseParallelGC -XX:+PrintGCDetails -Xmx500m -Xms500m -Xloggc:/Users/zhuqi/Documents/workspace/learning/lesson2/src/main/java/question2/logs/parallel_500_gc.log
Running 30s test @ http://127.0.0.1:8088/api/hello
12 threads and 100 connections
Thread Stats   Avg      Stdev     Max   +/- Stdev
Latency    44.15ms   88.70ms   1.01s    87.95%
Req/Sec     1.05k   636.66     5.77k    55.44%
370620 requests in 30.10s, 44.25MB read
Requests/sec:  12311.57
Transfer/sec:      1.47MB
======================================================================================================================================================================================


-XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -Xmx500m -Xms500m -Xloggc:/Users/zhuqi/Documents/workspace/learning/lesson2/src/main/java/question2/logs/cms_500_gc.log
Running 30s test @ http://127.0.0.1:8088/api/hello
12 threads and 100 connections
Thread Stats   Avg      Stdev     Max   +/- Stdev
Latency    49.57ms   93.82ms   1.05s    87.10%
Req/Sec     0.94k   600.72     2.69k    50.70%
331099 requests in 30.08s, 39.53MB read
Requests/sec:  11006.47
Transfer/sec:      1.31MB
======================================================================================================================================================================================


-XX:+UseG1GC -XX:+PrintGCDetails -Xmx500m -Xms500m -Xloggc:/Users/zhuqi/Documents/workspace/learning/lesson2/src/main/java/question2/logs/g1_500_gc.log
Running 30s test @ http://127.0.0.1:8088/api/hello
12 threads and 100 connections
Thread Stats   Avg      Stdev     Max   +/- Stdev
Latency    46.99ms   93.14ms 965.06ms   87.87%
Req/Sec     0.96k   565.17     7.91k    53.98%
338872 requests in 30.10s, 40.46MB read
Requests/sec:  11258.76
Transfer/sec:      1.34MB
======================================================================================================================================================================================



-XX:+UseSerialGC -XX:+PrintGCDetails -Xmx1g -Xms1g -Xloggc:/Users/zhuqi/Documents/workspace/learning/lesson2/src/main/java/question2/logs/serial_1g_gc.log
Running 30s test @ http://127.0.0.1:8088/api/hello
12 threads and 100 connections
Thread Stats   Avg      Stdev     Max   +/- Stdev
Latency    43.26ms   89.75ms 995.50ms   88.50%
Req/Sec     1.09k   665.55     7.26k    54.94%
382658 requests in 30.10s, 45.68MB read
Requests/sec:  12714.91
Transfer/sec:      1.52MB
======================================================================================================================================================================================



-XX:+UseParallelGC -XX:+PrintGCDetails -Xmx1g -Xms1g -Xloggc:/Users/zhuqi/Documents/workspace/learning/lesson2/src/main/java/question2/logs/parallel_1g_gc.log
Running 30s test @ http://127.0.0.1:8088/api/hello
12 threads and 100 connections
Thread Stats   Avg      Stdev     Max   +/- Stdev
Latency    44.47ms   85.42ms 857.96ms   87.73%
Req/Sec     0.89k   501.39     7.00k    60.43%
312856 requests in 30.09s, 37.35MB read
Requests/sec:  10398.33
Transfer/sec:      1.24MB
======================================================================================================================================================================================



-XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -Xmx1g -Xms1g -Xloggc:/Users/zhuqi/Documents/workspace/learning/lesson2/src/main/java/question2/logs/cms_1g_gc.log
Running 30s test @ http://127.0.0.1:8088/api/hello
12 threads and 100 connections
Thread Stats   Avg      Stdev     Max   +/- Stdev
Latency    45.50ms   83.07ms 808.98ms   86.98%
Req/Sec   847.16    481.43     2.95k    59.75%
300347 requests in 30.05s, 35.86MB read
Requests/sec:   9993.31
Transfer/sec:      1.19MB
======================================================================================================================================================================================



-XX:+UseG1GC -XX:+PrintGCDetails -Xmx1g -Xms1g -Xloggc:/Users/zhuqi/Documents/workspace/learning/lesson2/src/main/java/question2/logs/g1_1g_gc.log
Running 30s test @ http://127.0.0.1:8088/api/hello
12 threads and 100 connections
Thread Stats   Avg      Stdev     Max   +/- Stdev
Latency    43.39ms   82.57ms 860.61ms   87.61%
Req/Sec     0.93k   599.29     2.31k    50.83%
327556 requests in 30.08s, 39.11MB read
Requests/sec:  10889.10
Transfer/sec:      1.30MB
======================================================================================================================================================================================
