package com.example.bets.service.feign;

import com.example.bets.client.AuthorizedFeignClient;
import com.example.bets.service.feign.dto.WalletDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AuthorizedFeignClient(name = "bets", decode404 = true)
@RequestMapping("/api")
public interface WalletServiceFeign {

    @GetMapping("/wallets/{username}/username")
    ResponseEntity<WalletDTO> getWalletByUsername(@PathVariable String username);

    @PutMapping("/wallets")
    ResponseEntity<WalletDTO> updateAmountWalletByUser(@RequestBody WalletDTO walletDTO);

}
