package com.tmt.pos.mypos.config.auth.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthToken {
    private String token;
    private String username;
}
