package com.dev.timetracker.security;

import com.dev.timetracker.dto.user.DTOLoginUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

public class SecurityConfigTest {

    public static DTOLoginUser basicUser = new DTOLoginUser("jose", "123");
    public static UserDetails mockLogin = new User(basicUser.username(), basicUser.cpf(), Collections.emptyList());
}