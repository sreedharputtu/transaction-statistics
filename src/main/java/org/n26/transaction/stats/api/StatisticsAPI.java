package org.n26.transaction.stats.api;

import org.n26.transaction.stats.dto.StatisticsForMinute;
import org.n26.transaction.stats.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API to get transaction statistics for last one minute
 *
 * @Author Sreedhar
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsAPI {

    @Autowired
    TransactionService transactionService;

    @GetMapping
    public ResponseEntity getStatisticsForLastOneMin(){
        StatisticsForMinute statisticsForMinute = null;
        try {
            statisticsForMinute = transactionService.getStatisticsForLastMin();
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<StatisticsForMinute>(statisticsForMinute, HttpStatus.OK);
    }


}
