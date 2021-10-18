package com.qee.autoconfigure;

import com.qee.autoconfigure.models.School;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @ProjectName: learning
 * @Package: com.qee.autoconfigure
 * @ClassName: StudentProperties
 * @Description:
 * @Date: 2021/10/18 8:39 下午
 * @Version: 1.0
 */
@ConfigurationProperties(prefix = "qee.class-stu.student")
public class StudentProperties {


}
