package question2.com.qee.threads;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ProjectName: learning
 * @Package: question2.threads
 * @ClassName: Thread13  ReentrantLock Condition  PipedInputStream  PipedOutputStream实现
 * @Description:
 * @Date: 2021/10/8 3:05 下午
 * @Version: 1.0
 */
public class Thread13 extends ThreadBase {

    static class Pipe {

        PipedInputStream inputStream = new PipedInputStream();

        PipedOutputStream outputStream = new PipedOutputStream();

        private ReentrantLock lock = new ReentrantLock();

        private Condition completedWrite = lock.newCondition();

        private Condition completedRead = lock.newCondition();

        public Pipe() {
            try {
                inputStream.connect(outputStream);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        public void write(String str) {
            try {
                lock.lock();
                outputStream.write(str.getBytes());
                System.out.println("发送数据=>" + str);
                completedWrite.signal();
                completedRead.await();
            } catch (IOException | InterruptedException ioException) {
                ioException.printStackTrace();
            } finally {

                lock.unlock();
            }
        }

        public String read() {
            try {
                lock.lock();
                completedWrite.await();

                int available = 0;
                try {
                    available = inputStream.available();
                    byte[] buf = new byte[available];
                    inputStream.read(buf);
                    completedRead.signal();
                    return new String(buf, 0, available);

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {

                lock.unlock();
            }
            return null;
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        Thread13 thread13 = new Thread13();

        List<Pipe> list = new ArrayList<>();
        for (int i = 0; i < THREAD_NUM; i++) {
            list.add(new Pipe());
        }

        for (int i = 0; i < THREAD_NUM; i++) {
            int finalI = i;
            new Thread(() -> {
                Pipe pipe = list.get(finalI);
                System.out.println(pipe.read());
            }).start();
        }

        Thread.sleep(2000);
        for (int i = 0; i < THREAD_NUM; i++) {
            int finalI = i;
            new Thread(() -> {
                Pipe pipe = list.get(finalI);
                String execute = thread13.execute();
                pipe.write(execute);
            }).start();
        }


    }
}
