package com.ing.springboot.training.d04.s03.config;

import com.ing.springboot.training.d04.s03.aop.aspect.LoggingAspect;
import com.ing.springboot.training.d04.s03.aop.aspect.ProfilingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AspectJConfig {

    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }

    @Bean
    public ProfilingAspect profiler() {
        return new ProfilingAspect();
    }
}
