package com.expense.tracker.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class BeanCounter {

    private static Logger LOGGER = LoggerFactory.getLogger(BeanCounter.class);
    @Bean
    public CommandLineRunner countBeans(ApplicationContext context){
        return result ->{
            String[] beans = context.getBeanDefinitionNames();
            LOGGER.info("total beans created : {}", beans.length);
            Arrays.stream(beans).forEach(name -> System.out.println(name));
        };
    }
}
