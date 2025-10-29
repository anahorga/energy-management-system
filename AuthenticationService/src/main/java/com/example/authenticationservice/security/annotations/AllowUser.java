package com.example.authenticationservice.security.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority(T(com.mhp.pokimate.user.UserRole).USER)")
@Target(ElementType.METHOD)
public @interface AllowUser {
}

