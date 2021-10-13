package question2.com.qee.autwireds.xml.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class KClass {

    private Long id;

    private List<Student> studentList;

}
