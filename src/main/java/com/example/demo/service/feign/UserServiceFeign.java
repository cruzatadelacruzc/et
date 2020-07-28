package com.example.demo.service.feign;

import com.example.demo.client.AuthorizedFeignClient;
import com.example.demo.service.feign.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@AuthorizedFeignClient(name = "uaa", decode404 = true)
@RequestMapping("/api")
public interface UserServiceFeign {

    @GetMapping("/users/{username}/username")
    ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username);


}
