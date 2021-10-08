package org.wayne.getbean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @author: lwq
 */
@Configuration
@ComponentScan("org.wayne")
public class Config {



    @Bean
    User user1(){
        return new Tom();
    }
    @Bean
    User user2(){
        return new Jerry();
    }
}
