package com.qee.autoconfigure;

import com.qee.autoconfigure.models.KClass;
import com.qee.autoconfigure.models.School;
import com.qee.autoconfigure.models.Student;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @ProjectName: learning
 * @Package: com.qee.autoconfigure
 * @ClassName: KlassStudentConfiguration
 * @Description:
 * @Date: 2021/10/18 8:25 下午
 * @Version: 1.0
 */
@Configuration
@ConditionalOnProperty(prefix = "qee.class-stu")
@EnableConfigurationProperties({ClassProperties.class, StudentProperties.class, SchoolProperties.class})
public class KlassStudentConfiguration {


    @Bean
    public School school(SchoolProperties schoolProperties) {

    }

    @Bean
    public KClass kClass(ClassProperties classProperties) {

    }

    @Bean
    public List<Student> studentList(StudentProperties studentProperties) {

    }


}
