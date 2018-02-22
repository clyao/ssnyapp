package com.ssnyapp.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(prefix = "ssnyapp", name = "spring-session-open", havingValue = "true")
public class SpringSessionConfig {

}
