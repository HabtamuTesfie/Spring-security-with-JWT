package com.test.productmanagement.security.payload.request;

import lombok.Data;

import java.util.Set;

@Data
public class SignupRequest {
    private String username;
    private String password;
}
