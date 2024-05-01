package com.dev.timetracker.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

/**
 * Authentication will be made using username/cpf
 * Nothing encrypted, just dummy password (NoOpPasswordEncoder allows that)
 * Needs understanding about session authentication. For now every request needs to receive username/cpf auth
 * The method being tested is calling the users/login endpoint, which will return the user credentials back if successful. Then it will be used to authenticate the other requests
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Bean
    @SuppressWarnings("deprecation")
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new AntPathRequestMatcher("/users/register", "/")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/users/login")).permitAll()
                        .anyRequest().authenticated())
                .formLogin(AbstractHttpConfigurer::disable) //TODO: Understand better all this formLogin settings
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("SELECT username, cpf, active FROM users WHERE username=?")
                .authoritiesByUsernameQuery("SELECT username, role FROM users WHERE username=?"); //TODO: Actually define security based on roles. Does nothing as it is
    }
}