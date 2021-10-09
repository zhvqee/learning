package question2.com.qee.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @ProjectName: learning
 * @Package: question2.threads
 * @ClassName: Thread09  通过 CompletableFuture
 * @Description:
 * @Date: 2021/10/8 3:05 下午
 * @Version: 1.0
 */
public class Thread09 extends ThreadBase {


    public static <T> List<T> allOf(List<CompletableFuture<T>> completableFutures) throws ExecutionException, InterruptedException {
        CompletableFuture<List<T>> listCompletableFuture = CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]))
                .thenApply(v -> completableFutures.stream().map(CompletableFuture::join).collect(Collectors.toList()));

        return listCompletableFuture.get();
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {


        Thread09 thread07 = new Thread09();
        List<CompletableFuture<String>> result = new ArrayList<>();
        for (int i = 0; i < THREAD_NUM; i++) {
            CompletableFuture<String> voidCompletableFuture = CompletableFuture.supplyAsync(thread07::execute);
            result.add(voidCompletableFuture);
        }

        List<String> stringList = allOf(result);
        for (String s : stringList) {
            System.out.println(s);
        }

        System.out.println("======================");

        List<CompletableFuture<String>> result2 = new ArrayList<>();
        for (int i = 0; i < THREAD_NUM; i++) {
            CompletableFuture<String> voidCompletableFuture = CompletableFuture.supplyAsync(thread07::execute);
            result2.add(voidCompletableFuture);
        }
        System.out.println("-->" + System.currentTimeMillis());
        CompletableFuture.allOf(result2.toArray(new CompletableFuture[0])).join();
        System.out.println("-->" + System.currentTimeMillis());
        for (CompletableFuture<String> str : result2) {
            System.out.println(str.get());
        }
    }
}
