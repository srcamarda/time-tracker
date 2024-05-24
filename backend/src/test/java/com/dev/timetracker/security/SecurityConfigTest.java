package com.dev.timetracker.security;

import com.dev.timetracker.dto.user.DTOLoginUser;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
public class SecurityConfigTest {

    public static DTOLoginUser basicUser = new DTOLoginUser("moana", "21055356070");

//    @Bean
//    @Primary
//    public UserDetailsService userDetailsService(UserDetailsServiceAutoConfiguration userDetailsServiceAutoConfiguration) {
//
//        UserActive basicActiveUser = new UserActive(basicUser, Arrays.asList(
//                new SimpleGrantedAuthority(Role.SENIOR.toString());
//        ));
//
//        return new InMemoryUserDetailsManager(List.of(basicActiveUser));
//    }
}