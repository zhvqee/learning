package question2.com.qee.autwireds.annotations.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Data
@ToString
@Component
@Import(ClassId.class)
public class KClass {

    @Resource
    private Long clzzId;

    @Autowired
    private List<Student> studentList;

}
