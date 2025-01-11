/*
package com.empproject.allocation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AuthConfig {
private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    log.info("AuthConfig: securityFilterChain Starts ");
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/projects/allocate", "/api/projects/allocate/*").authenticated()
                .anyRequest().permitAll()
                .and()
                .httpBasic();
        return http.build();
    }
}*/
