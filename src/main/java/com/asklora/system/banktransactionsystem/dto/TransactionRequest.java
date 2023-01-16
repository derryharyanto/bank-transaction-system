package com.asklora.system.banktransactionsystem.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionRequest {
    private Long userId;
    private Double amount;
}
