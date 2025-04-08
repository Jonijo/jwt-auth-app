package com.jwtauthapp.controller;



import com.jwtauthapp.dto.LoginUserDto;
import com.jwtauthapp.dto.RegisterUserDto;
import com.jwtauthapp.entity.LoginResponse;
import com.jwtauthapp.model.User;
import com.jwtauthapp.service.AuthenticationService;
import com.jwtauthapp.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class ProtectedExampleController {

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok().body("hello");
    }

}