package com.asklora.system.banktransactionsystem.service;

import com.asklora.system.bankmodelorm.entity.UserAccount;
import com.asklora.system.bankmodelorm.entity.UserTransaction;
import com.asklora.system.bankmodelorm.exception.RequestException;
import com.asklora.system.bankmodelorm.repository.UserAccountRepository;
import com.asklora.system.bankmodelorm.repository.UserTransactionRepository;
import com.asklora.system.banktransactionsystem.constant.TransactionType;
import com.asklora.system.banktransactionsystem.dto.TransactionRequest;
import com.asklora.system.banktransactionsystem.dto.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    UserAccountRepository userAccountRepository;

    @Autowired
    UserTransactionRepository userTransactionRepository;


    public TransactionResponse balanceTransaction(TransactionRequest request, TransactionType transactionType) throws RequestException {
        UserAccount userAccount=userAccountRepository.findUserAccountByUserId(request.getUserId());
        if (userAccount==null)
            throw new RequestException("User Id is not found");

        Double amountTransaction=request.getAmount();
        if (transactionType.equals(TransactionType.WITHDRAW)){
            amountTransaction=chargeLevyWithdrawAmount(amountTransaction);
        }
        else if (amountTransaction<500){
            throw new RequestException("The minimum deposit amount is 500");
        }

        Double totalBalance=userAccount.getBalance().doubleValue()+amountTransaction;

        validateWithdraw(request,totalBalance);

        UserTransaction userTransaction = saveTransactionData(userAccount,BigDecimal.valueOf(amountTransaction),BigDecimal.valueOf(totalBalance));
        userAccount.setBalance(BigDecimal.valueOf(totalBalance));
        userAccount.setCreatedDtime(new Date());
        userAccount.setUpdatedDtime(new Date());
        userAccountRepository.save(userAccount);

        return setTransactionResponse(userTransaction);
    }

    private void validateWithdraw(TransactionRequest request,Double totalBalance) throws RequestException {
        if (request.getAmount()<10){
            throw new RequestException("The withdrawal amount should be more than 10");
        }
        else if (totalBalance<10){
            throw new RequestException("Your balance remaining must be more than 10 after withdrawal");
        }
    }

    private Double chargeLevyWithdrawAmount(Double amount){
        if (amount>500 && amount<=5000){
            amount+=10;
        }
        else if(amount>5000) {
            amount+=20;
        }
        return 0-amount;
    }
    private TransactionResponse setTransactionResponse(UserTransaction userTransaction){
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setName(userTransaction.getUserId().getUserName());
        transactionResponse.setAccountNumber(userTransaction.getUserId().getAccountNumber());
        transactionResponse.setAmount(userTransaction.getAmountTransaction().abs().doubleValue());
        transactionResponse.setPreviousBalance(userTransaction.getPreviousBalance().doubleValue());
        transactionResponse.setTotalAmount(userTransaction.getTotalBalance().doubleValue());
        transactionResponse.setTransactionId(userTransaction.getTransactionId());
        transactionResponse.setUserId(userTransaction.getUserId().getUserId());

        return transactionResponse;
    }
    private UserTransaction saveTransactionData(UserAccount userAccount,BigDecimal amount,BigDecimal totalBalance){
        UserTransaction userTransaction =  new UserTransaction();
        userTransaction.setUserId(userAccount);
        userTransaction.setAmountTransaction(amount);
        userTransaction.setTransactionId(getNewTransactionId());
        userTransaction.setPreviousBalance(userAccount.getBalance());
        userTransaction.setTotalBalance(totalBalance);
        userTransaction.setCreatedDtime(new Date());
        userTransactionRepository.save(userTransaction);

        return userTransaction;
    }

    private String getNewTransactionId(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
