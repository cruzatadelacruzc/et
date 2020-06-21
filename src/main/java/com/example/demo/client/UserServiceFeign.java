package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "uaa")
public interface UserServiceFeign {

    @GetMapping("/api/users/{username}")
    UserDTO getUserByUsername(@PathVariable String username);



}
