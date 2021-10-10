package question1.com.qee.proxy.jdk.test;

public interface UserService {

    UserService getThis();


    String say(String msg);
}
