package com.ata.bankapp.controller;

import com.ata.bankapp.client.TransactionClient;
import com.ata.bankapp.model.Transaction;
import com.ata.bankapp.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionClient transactionClient;

    @PostMapping("/create")
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        logger.info("Creating transaction: {}", transaction);
        Transaction createdTransaction = transactionClient.createTransaction(transaction);
        logger.info("Transaction created: {}", createdTransaction);
        return createdTransaction;
    }

    @GetMapping("/account/{accountId}")
    public List<Transaction> getTransactionsByAccountId(@PathVariable Long accountId) {
        logger.info("Fetching transactions for account ID: {}", accountId);
        List<Transaction> transactions = transactionClient.getTransactionsByAccountId(accountId);
        logger.info("Fetched {} transactions for account ID: {}", transactions.size(), accountId);
        return transactions;
    }

    @GetMapping("/{transactionId}")
    public Transaction getTransaction(@PathVariable Long transactionId) {
        logger.info("Fetching transaction with ID: {}", transactionId);
        Transaction transaction = transactionClient.getTransactionById(transactionId);
        logger.info("Fetched transaction with ID: {}", transactionId);
        return transaction;
    }
}
