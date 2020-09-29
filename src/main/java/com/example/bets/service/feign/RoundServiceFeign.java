package com.example.bets.service.feign;

import com.example.bets.client.AuthorizedFeignClient;
import com.example.bets.service.feign.dto.RoundDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@AuthorizedFeignClient(name = "sportdata", decode404 = true)
@RequestMapping("/api")
public interface RoundServiceFeign {

    @GetMapping("/rounds/{id}")
    ResponseEntity<RoundDTO> getRoundById(@PathVariable Long id);
}
