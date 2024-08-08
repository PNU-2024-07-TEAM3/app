package com.ll.sapp;

import com.ll.sapp.user.SiteUser;
import com.ll.sapp.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Optional;

@Component
@RequestScope
@RequiredArgsConstructor
public class Rq {
    private Boolean isLogin;
    private User user;
    private SiteUser siteUser;
    private final UserService userService;

    public SiteUser getSiteUser() {
        if (isLogout()) return null;

        if (siteUser == null) {
            // entityManager 객체로 프록시 객체 얻기
            siteUser = userService.findByUsername(getUser().getUsername()).get();
        }

        return siteUser;
    }

    public boolean isLogout() {
        return !isLogin();
    }

    public boolean isLogin() {
        if (isLogin == null) getUser();

        return isLogin;
    }

    private User getUser() {
        if (isLogin == null) {
            user = Optional.ofNullable(SecurityContextHolder.getContext())
                    .map(SecurityContext::getAuthentication)
                    .filter(authentication -> authentication.getPrincipal() instanceof User)
                    .map(authentication -> (User) authentication.getPrincipal())
                    .orElse(null);

            isLogin = user != null;
        }

        return user;
    }
}