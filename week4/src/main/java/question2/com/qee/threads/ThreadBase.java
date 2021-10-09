package question2.com.qee.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @ProjectName: learning
 * @Package: question2.threads
 * @ClassName: Thread01
 * @Description:
 * @Date: 2021/10/8 2:57 下午
 * @Version: 1.0
 */
public class ThreadBase {

    private List<String> result = new ArrayList<String>();

    public static final int THREAD_NUM = 10;

    public String execute() {
        Random random = new Random();
        int executeTimes = random.nextInt(1000);
        try {
            Thread.sleep(executeTimes + 1000);
        } catch (InterruptedException e) {
        }
        String res = Thread.currentThread().getName() + "==>" + executeTimes;
        synchronized (this) {
            result.add(res);
        }
        return res;
    }

    public List<String> getResult() {
        return result;
    }

}
