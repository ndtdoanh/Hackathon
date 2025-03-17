package com.hacof.hackathon.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/identities")
@AllArgsConstructor
@Slf4j
public class IdentityController {
    //    private final IdentityClientServiceImpl identityClientService;
    //
    //    @GetMapping("/users")
    //    public ApiResponse<List<UserResponse>> getUsersFromIdentityService(@RequestHeader("Authorization") String
    // token) {
    //        log.info("Getting users from identity service");
    //        return identityClientService.getUsers(token);
    //    }

}
