package question2.com.qee.autwireds.annotations.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentName {

    @Bean
    public String stuName() {
        return "华安";
    }
}
