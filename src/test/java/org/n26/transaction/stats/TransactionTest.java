package org.n26.transaction.stats;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.n26.transaction.stats.dto.StatisticsForMinute;
import org.n26.transaction.stats.dto.Transaction;
import org.n26.transaction.stats.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionTest {

    @Autowired
    TransactionService transactionService;

    @Test
    public void addTransactionSuccessTest(){
        StatisticsForMinute statisticsForMinute = transactionService.getStatisticsForLastMin();
        transactionService.addTransactionAndComputeStatistics(new Transaction( 10.5,System.currentTimeMillis() - 60000));
        assertEquals(statisticsForMinute, transactionService.getStatisticsForLastMin());
    }

    @Test
    public void getStatisticsForLastMinTest(){

        transactionService.addTransactionAndComputeStatistics(new Transaction(3.5, System.currentTimeMillis() - 4000));
        transactionService.addTransactionAndComputeStatistics(new Transaction(2.5, System.currentTimeMillis() - 3000));
        transactionService.addTransactionAndComputeStatistics(new Transaction(4.0, System.currentTimeMillis() - 2000));
        transactionService.addTransactionAndComputeStatistics(new Transaction(10.1, System.currentTimeMillis() - 1000));

        StatisticsForMinute statisticsForMinute = transactionService.getStatisticsForLastMin();
        assertEquals(statisticsForMinute.getCount(), 4l);
        assertEquals(statisticsForMinute.getSum(), 20.1, 0.0);
        assertEquals(statisticsForMinute.getMax(), 10.1, 0.0);
        assertEquals(statisticsForMinute.getMin(), 2.5, 0.0);
        assertEquals(statisticsForMinute.getAvg(), 20.1 / 4, 0.0);

    }

}
