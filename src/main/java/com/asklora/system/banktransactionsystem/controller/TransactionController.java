package com.asklora.system.banktransactionsystem.controller;

import com.asklora.system.bankmodelorm.dto.ApiResponse;
import com.asklora.system.bankmodelorm.exception.RequestException;
import com.asklora.system.banktransactionsystem.constant.TransactionType;
import com.asklora.system.banktransactionsystem.dto.TransactionRequest;
import com.asklora.system.banktransactionsystem.dto.TransactionResponse;
import com.asklora.system.banktransactionsystem.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/asklora/api/transaction/v1/")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @RequestMapping(value = "/deposit",method = RequestMethod.POST)
    public ResponseEntity<Object> depositAccount(@RequestBody TransactionRequest request){
        HttpStatus status;
        TransactionResponse transactionResponse;
        ApiResponse apiResponse=new ApiResponse();
        try {
            transactionResponse=transactionService.balanceTransaction(request, TransactionType.DEPOSIT);
            status=HttpStatus.OK;
            apiResponse.setMessage(status.getReasonPhrase());
            apiResponse.setStatus(status.value());
            transactionResponse.setResponse(apiResponse);
        }
        catch (RequestException re){
            apiResponse.setMessage(re.getMessage());
            apiResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            status=HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(apiResponse,status);

        }catch (Exception e){
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            status=HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(apiResponse,status);
        }
        return new ResponseEntity<>(transactionResponse,status);
    }

    @RequestMapping(value = "/withdraw",method = RequestMethod.POST)
    public ResponseEntity<Object> withdrawAccount(@RequestBody TransactionRequest request){
        HttpStatus status;
        TransactionResponse transactionResponse;
        ApiResponse apiResponse=new ApiResponse();
        try {
            transactionResponse=transactionService.balanceTransaction(request,TransactionType.WITHDRAW);
            status=HttpStatus.OK;
            apiResponse.setMessage(status.getReasonPhrase());
            apiResponse.setStatus(status.value());
            transactionResponse.setResponse(apiResponse);
        }
        catch (RequestException re){
            apiResponse.setMessage(re.getMessage());
            apiResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            status=HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(apiResponse,status);

        }catch (Exception e){
            apiResponse.setMessage(e.getMessage());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            status=HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(apiResponse,status);
        }
        return new ResponseEntity<>(transactionResponse,status);
    }

}
