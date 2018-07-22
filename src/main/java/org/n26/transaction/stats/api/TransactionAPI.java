package org.n26.transaction.stats.api;

import org.n26.transaction.stats.dto.Transaction;
import org.n26.transaction.stats.service.TransactionService;
import org.n26.transaction.stats.util.TransactionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API to post transactions
 *
 * @Author Sreedhar
 */
@RestController
@RequestMapping("/transactions")
public class TransactionAPI {

    @Autowired
    TransactionService transactionService;

    @PostMapping
    public ResponseEntity saveTransaction(@RequestBody Transaction transaction) {
        try {
            if (!TransactionUtil.isInLastMinute(transaction))
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            transactionService.addTransactionAndComputeStatistics(transaction);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }


}
