package com.example.bets.service.feign;

import com.example.bets.client.AuthorizedFeignClient;
import com.example.bets.service.feign.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@AuthorizedFeignClient(name = "bets", decode404 = true)
@RequestMapping("/api")
public interface UserServiceFeign {

    @GetMapping("/users/{username}/username")
    ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username);


}
