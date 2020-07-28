package com.example.demo.service.feign;

import com.example.demo.client.AuthorizedFeignClient;
import com.example.demo.service.feign.dto.WalletDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AuthorizedFeignClient(name = "uaa", decode404 = true)
@RequestMapping("/api")
public interface WalletServiceFeign {

    @GetMapping("/wallets/{username}/username")
    ResponseEntity<WalletDTO> getWalletByUsername(@PathVariable String username);

    @PutMapping("/wallets")
    ResponseEntity<WalletDTO> updateAmountWalletByUser(@RequestBody WalletDTO walletDTO);

}
