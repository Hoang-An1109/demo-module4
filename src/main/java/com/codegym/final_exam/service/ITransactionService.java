package com.codegym.final_exam.service;

import com.codegym.final_exam.dto.TransactionDTO;
import com.codegym.final_exam.model.Transaction;
import com.codegym.final_exam.payload.request.TransactionRequest;
import com.codegym.final_exam.payload.response.TransactionResponse;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ITransactionService {
    List<TransactionDTO> findAll();

    void save(TransactionRequest transactionRequest);

    Transaction findById(Long id);

    void delete(Long id);

    List<TransactionResponse> findAllByTypeServiceOrNameCustomer(String searchTypeService, String searchNameCustomer);

    List<TransactionResponse> findAllByTypeServiceAndNameCustomer(String searchTypeService, String searchNameCustomer);
}
