package com.dev.timetracker.security;

import com.dev.timetracker.dto.user.DTOLoginUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

public class SecurityConfigTest {

    public static DTOLoginUser basicUser = new DTOLoginUser("moana", "21055356070");
    public static DTOLoginUser testUser = new DTOLoginUser("testUser", "07621310049");
    public static UserDetails mockLogin = new User(testUser.username(), testUser.cpf(), Collections.emptyList());
}