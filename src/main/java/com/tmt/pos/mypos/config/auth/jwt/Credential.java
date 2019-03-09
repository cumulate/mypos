package com.tmt.pos.mypos.config.auth.jwt;

import com.tmt.pos.mypos.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Credential {
    private AuthToken authToken;
    private User user;
}
