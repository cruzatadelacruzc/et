package com.example.bets.service.feign.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WalletDTO {

    private Long id;

    private Double total_amount;

    private Long userId;

    private String createdDate;

    private String lastModifiedDate;
}
