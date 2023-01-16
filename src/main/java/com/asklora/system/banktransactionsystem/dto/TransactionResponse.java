package com.asklora.system.banktransactionsystem.dto;

import com.asklora.system.bankmodelorm.dto.ApiResponse;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionResponse {
    private Long userId;
    private String accountNumber;
    private String name;
    private String transactionId;
    private Double previousBalance;
    private Double amount;
    private Double totalAmount;
    private ApiResponse response;
}
