package com.example.demo.service;

import com.example.demo.client.AuthorizedUserFeignClient;
import com.example.demo.service.dto.feign.UserDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@AuthorizedUserFeignClient(name = "uaa")
@RequestMapping("/api")
public interface UserServiceFeign {

    @GetMapping("/users/{username}/username")
    UserDTO getUserByUsername(@PathVariable String username);



}
