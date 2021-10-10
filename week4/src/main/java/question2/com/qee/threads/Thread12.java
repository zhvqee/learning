package question2.com.qee.threads;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

/**
 * @ProjectName: learning
 * @Package: question2.threads
 * @ClassName: Thread12 Pipe 实现
 * @Description:
 * @Date: 2021/10/8 3:05 下午
 * @Version: 1.0
 */
public class Thread12 extends ThreadBase {


    public static void main(String[] args) throws IOException, InterruptedException {

        Thread12 thread12 = new Thread12();
        Pipe pipe = Pipe.open();

        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(() -> {
                String execute = thread12.execute();
                try {
                    pipe.sink().write(ByteBuffer.wrap(execute.getBytes()));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }).start();
        }

        Pipe.SourceChannel source = pipe.source();
        Selector selector = Selector.open();
        source.configureBlocking(false);
        SelectionKey register = source.register(selector, source.validOps());
        register.attach(source);
        int i = 0;
        while (i < THREAD_NUM) {
            if (selector.select() > 0) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    Pipe.SourceChannel sourceChannel = (Pipe.SourceChannel) selectionKey.attachment();
                    int len = 0;
                    ByteBuffer allocate = ByteBuffer.allocate(1024);
                    while ((len = sourceChannel.read(allocate)) > 0) {
                        allocate.flip();
                        System.out.println(new String(allocate.array(), 0, len));
                        allocate.clear();
                    }
                    iterator.remove();
                }
                i++;
            }
        }
    }
}
