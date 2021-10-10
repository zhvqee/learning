package question1.com.qee.proxy.jdk;

import com.qee.common.Advised;
import com.qee.common.Advisor;
import com.qee.common.Ordered;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class JdkAdvised implements Advised {

    private List<Advisor> advisorList = new ArrayList<>();

    private Object target;

    public JdkAdvised(Object target) {
        this.target = target;
    }

    @Override
    public void addAdvisor(Advisor advisor) {
        if (advisor == null || advisor.getPointcut() == null || advisor.getAdvice() == null) {
            throw new IllegalArgumentException("param illegal");
        }
        advisorList.add(advisor);
        advisorList.sort(Comparator.comparingInt(Ordered::order));
    }

    @Override
    public List<Advisor> getAdvisor() {
        return advisorList;
    }

    @Override
    public Object getTarget() {
        return target;
    }
}
