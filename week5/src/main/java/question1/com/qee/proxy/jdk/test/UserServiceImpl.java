package question1.com.qee.proxy.jdk.test;

import com.qee.common.JdkAop;

@JdkAop
public class UserServiceImpl implements UserService {
    @Override
    public UserService getThis() {
        return this;
    }

    @Override
    public String say(String msg) {
        return "hello world !!! ==>" + msg;
    }
}
