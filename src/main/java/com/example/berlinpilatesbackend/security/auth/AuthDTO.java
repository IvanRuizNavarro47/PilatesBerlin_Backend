package com.example.berlinpilatesbackend.security.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthDTO {

    private String token;
    private String info;
}