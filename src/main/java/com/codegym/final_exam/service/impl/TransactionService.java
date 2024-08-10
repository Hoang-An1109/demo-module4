package com.codegym.final_exam.service.impl;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.codegym.final_exam.dto.TransactionDTO;
import com.codegym.final_exam.model.Transaction;
import com.codegym.final_exam.payload.request.TransactionRequest;
import com.codegym.final_exam.payload.response.TransactionResponse;
import com.codegym.final_exam.repository.ITransactionRepository;
import com.codegym.final_exam.service.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionService {

    private final ITransactionRepository transactionRepository;
    @Override
    public List<TransactionDTO> findAll() {
        List<Transaction> transactions = transactionRepository.findAll();

        return transactions.stream().map(transaction ->
                TransactionDTO.builder()
                        .id(transaction.getId())
                        .transactionCode(transaction.getTransactionCode())
                        .name(transaction.getCustomer().getName())
                        .transactionDate(transaction.getTransactionDate())
                        .typeService(transaction.getTypeService())
                        .price(transaction.getPrice())
                        .area(transaction.getArea())
                        .build()).toList();
    }

    @Override
    public void save(TransactionRequest transactionRequest) {
        Transaction transaction = new Transaction();

        transaction.setTransactionCode(transactionRequest.getTransactionCode());
        transaction.setCustomer(transactionRequest.getCustomer());
        transaction.setTransactionDate(transactionRequest.getTransactionDate());
        transaction.setTypeService(transactionRequest.getTypeService());
        transaction.setPrice(transactionRequest.getPrice());
        transaction.setArea(transactionRequest.getArea());

        transactionRepository.save(transaction);
    }

    @Override
    public Transaction findById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public void update(Long id, TransactionRequest transactionRequest) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isPresent()) {
            Transaction updateTransaction = transaction.get();
            BeanUtils.copyProperties(transactionRequest, updateTransaction);
            transactionRepository.save(updateTransaction);
        }

    }

    @Override
    public List<TransactionResponse> findAllByTypeServiceOrNameCustomer(String searchTypeService, String searchNameCustomer) {
        List<Transaction> transactions = transactionRepository.findAllByTypeServiceOrNameCustomer(searchTypeService, searchNameCustomer);
        List<TransactionResponse> response = new ArrayList<>();
        for (Transaction transaction : transactions) {
            response.add(TransactionResponse.builder()
                    .id(transaction.getId())
                    .transactionCode(transaction.getTransactionCode())
                    .transactionDate(transaction.getTransactionDate())
                    .typeService(transaction.getTypeService())
                    .price(transaction.getPrice())
                    .area(transaction.getArea())
                    .name(transaction.getCustomer().getName())
                    .build());
        }
        return response;
    }

    @Override
    public List<TransactionResponse> findAllByTypeServiceAndNameCustomer(String searchTypeService, String searchNameCustomer) {
        List<Transaction> transactions = transactionRepository.findAllByTypeServiceAndNameCustomer(searchTypeService, searchNameCustomer);
        List<TransactionResponse> response = new ArrayList<>();
        for (Transaction transaction : transactions) {
            response.add(TransactionResponse.builder()
                    .id(transaction.getId())
                    .transactionCode(transaction.getTransactionCode())
                    .transactionDate(transaction.getTransactionDate())
                    .typeService(transaction.getTypeService())
                    .price(transaction.getPrice())
                    .area(transaction.getArea())
                    .name(transaction.getCustomer().getName())
                    .build());
        }
        return response;
    }
}
