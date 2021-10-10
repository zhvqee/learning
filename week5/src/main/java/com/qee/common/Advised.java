package com.qee.common;

import java.util.List;

public interface Advised {

    void addAdvisor(Advisor advisor);

    List<Advisor> getAdvisor();


    Object getTarget();

}
