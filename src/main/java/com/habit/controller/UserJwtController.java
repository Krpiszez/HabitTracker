package com.habit.controller;

import com.habit.dto.request.LoginRequest;
import com.habit.dto.request.RegisterRequest;
import com.habit.dto.response.HTResponses;
import com.habit.dto.response.LoginResponse;
import com.habit.dto.response.ResponseMessages;
import com.habit.security.jwt.JwtUtils;
import com.habit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserJwtController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

//    @PostMapping("/register/{habitId}")
//    public ResponseEntity<HTResponses> createUser(@PathVariable Long habitId, @RequestBody @Valid RegisterRequest registerRequest){
//        userService.registerUser2(habitId, registerRequest);
//        HTResponses response = new HTResponses(ResponseMessages.USER_CREATED_RESPONSE, true);
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }

    @PostMapping("/register")
    public ResponseEntity<HTResponses> createUser(@RequestBody @Valid RegisterRequest registerRequest){
        userService.registerUser(registerRequest);
        HTResponses response = new HTResponses(ResponseMessages.USER_CREATED_RESPONSE, true);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateToken(userDetails);
        LoginResponse response = new LoginResponse(jwtToken);
        return ResponseEntity.ok(response);
    }


}
