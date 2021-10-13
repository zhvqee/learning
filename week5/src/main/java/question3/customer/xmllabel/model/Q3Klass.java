package question3.customer.xmllabel.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class Q3Klass {


    private Long clzzId;

    private List<Q3Student> studentList;
}
