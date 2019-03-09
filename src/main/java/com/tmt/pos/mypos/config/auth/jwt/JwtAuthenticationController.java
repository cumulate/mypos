package com.tmt.pos.mypos.config.auth.jwt;


import com.tmt.pos.mypos.dao.UsersRepository;
import com.tmt.pos.mypos.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userService;

    @Autowired
    private UsersRepository usersRepository;

    //for rest authentication
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<AuthToken> authenticate(@RequestBody User loginUser) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUserName(), loginUser.getPassword()));
        // final User user = userService.findOne(loginUser.getUsername());
        org.springframework.security.core.userdetails.UserDetails user = userService.loadUserByUsername(loginUser.getUserName());
        final String token = jwtTokenHelper.generateToken(user);
        return ResponseEntity.ok(new AuthToken(token, user.getUsername()));
    }


    //for web authentication
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Credential> login(@RequestBody User loginUser) {

        AuthToken authToken =authenticateInternal(loginUser);
        User user = usersRepository.findOneByUserName(authToken.getUsername());
        return ResponseEntity.ok(new Credential(authToken, user));
    }

    private AuthToken authenticateInternal(User loginUser) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUserName(), loginUser.getPassword()));
        org.springframework.security.core.userdetails.UserDetails user = userService.loadUserByUsername(loginUser.getUserName());
        final String token = jwtTokenHelper.generateToken(user);
        return new AuthToken(token, user.getUsername());
    }




}