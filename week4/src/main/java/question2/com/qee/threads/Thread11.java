package question2.com.qee.threads;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ProjectName: learning
 * @Package: question2.threads
 * @ClassName: Thread11 通过共享内存 MappedByteBuffer
 * @Description:
 * @Date: 2021/10/8 3:05 下午
 * @Version: 1.0
 */
public class Thread11 extends ThreadBase {


    public static void main(String[] args) throws IOException, InterruptedException {

        Thread11 thread11 = new Thread11();
        RandomAccessFile file = new RandomAccessFile("./target", "rw");
        FileChannel channel = file.getChannel();
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 500);

        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(() -> {
                String execute = thread11.execute();
                //mappedByteBuffer.put
                mappedByteBuffer.put(execute.getBytes());
            }).start();
        }

        Thread.sleep(4000);

        while (mappedByteBuffer.hasRemaining()) {
            System.out.println((char) mappedByteBuffer.get());
        }


    }
}
