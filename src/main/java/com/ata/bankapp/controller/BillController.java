package com.ata.bankapp.controller;

import com.ata.bankapp.client.BillClient;
import com.ata.bankapp.model.Bill;
import com.ata.bankapp.request.BillPaymentRequest;
import com.ata.bankapp.service.BillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    private static final Logger logger = LoggerFactory.getLogger(BillController.class);

    @Autowired
    private BillService billService;

    @Autowired
    private BillClient billClient;

    @PostMapping("/create")
    public Bill createBill(@RequestBody Bill bill) {
        logger.info("Creating bill: {}", bill);
        Bill createdBill = billClient.createBill(bill);
        logger.info("Bill created successfully with ID: {}", createdBill.getId());
        return createdBill;
    }

    @PostMapping("/pay")
    public Map<String, String> payBill(@RequestBody BillPaymentRequest request) {
        logger.info("Paying bill with ID: {} using account ID: {} and amount: {}", request.getBillId(), request.getAccountId(), request.getAmount());
        Map<String, String> response = new HashMap<>();
        try {
            response = billClient.payBill(request);
            logger.info("Bill with ID: {} paid successfully.", request.getBillId());
        } catch (RuntimeException e) {
            response.put("message", "Failed to pay the bill.");
            logger.error("Failed to pay bill with ID: {}. Error: {}", request.getBillId(), e.getMessage());
        }
        return response;
    }

    @GetMapping("/{billId}")
    public Bill getBillById(@PathVariable Long billId) {
        logger.info("Fetching bill with ID: {}", billId);
        Bill bill = billClient.getBillById(billId);
        logger.info("Fetched bill with ID: {}", billId);
        return bill;
    }

    @GetMapping
    public List<Bill> getAllBills() {
        logger.info("Fetching all bills");
        List<Bill> bills = billClient.getAllBills();
        logger.info("Fetched {} bills", bills.size());
        return bills;
    }

    @PostMapping("/markOverdue")
    public void markOverdueBills() {
        logger.info("Marking overdue bills");
        billClient.markOverdueBills();
        logger.info("Overdue bills marked successfully.");
    }
}
