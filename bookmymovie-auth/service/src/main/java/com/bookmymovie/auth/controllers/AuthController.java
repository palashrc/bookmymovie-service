package com.bookmymovie.auth.controllers;

import com.bookmymovie.auth.model.AccessRequest;
import com.bookmymovie.auth.model.AccessUserRequest;
import com.bookmymovie.auth.model.AccessUserResponse;
import com.bookmymovie.auth.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/access-new")
    @ResponseBody
    public void createAccess(@RequestBody AccessRequest roleRequest) { authService.createAccess(roleRequest); }

    @PostMapping("/access-update")
    @ResponseBody
    public void updateAccess(@RequestBody AccessRequest roleRequest) { authService.updateAccess(roleRequest); }

    @PostMapping("/access-user-new")
    @ResponseBody
    public AccessUserResponse createAccessUser(@RequestBody AccessUserRequest accessUserRequest) { return authService.createAccessUser(accessUserRequest); }
}
