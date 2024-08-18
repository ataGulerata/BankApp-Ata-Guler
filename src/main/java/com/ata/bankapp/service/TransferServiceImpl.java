package com.ata.bankapp.service;

import com.ata.bankapp.model.Transfer;
import com.ata.bankapp.repo.TransferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferServiceImpl implements TransferService {

    private static final Logger logger = LoggerFactory.getLogger(TransferServiceImpl.class);

    @Autowired
    private TransferRepository transferRepository;

    @Override
    public void createTransfer(Transfer transfer) {
        logger.info("Creating transfer: {}", transfer);
        transferRepository.save(transfer);
    }

    @Override
    public Transfer getTransferById(Long transferId) {
        logger.info("Fetching transfer with ID: {}", transferId);
        return transferRepository.findById(transferId)
                .orElseThrow(() -> {
                    logger.error("Transfer not found with ID: {}", transferId);
                    return new RuntimeException("Transfer not found");
                });
    }

    @Override
    public List<Transfer> getAllTransfers() {
        logger.info("Fetching all transfers");
        return transferRepository.findAll();
    }
}
