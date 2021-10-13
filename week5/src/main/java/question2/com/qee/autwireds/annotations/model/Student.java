package question2.com.qee.autwireds.annotations.model;


import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Data
@Component
@Import({StudentId.class, StudentName.class, Family.class})

public class Student {

    @Resource
    private Long stuId;

    @Resource
    private String name;

    @Autowired
    private Family family;


}
