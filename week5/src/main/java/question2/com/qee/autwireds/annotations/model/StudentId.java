package question2.com.qee.autwireds.annotations.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentId {



    @Bean
    public Long stuId() {
        return 9537L;
    }
}
