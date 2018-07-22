package org.n26.transaction.stats.service;

import org.n26.transaction.stats.dto.StatisticsForMinute;
import org.n26.transaction.stats.dto.StatisticsForSecond;
import org.n26.transaction.stats.dto.Transaction;
import org.n26.transaction.stats.util.TransactionUtil;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Sreedhar
 */
@Service
public class TransactionService {

    //statisticsForSecondMap contains transactional statistics for each second 1,2,3,4...60
    private static final Map<Integer, StatisticsForSecond> statisticsForSecondMap = new ConcurrentHashMap<>(TransactionUtil.TIME_BOUND_IN_SECONDS);

    //memory complexity here is constant memory storage (at max 60 entries in statisticsForSecondMap)
    //statisticsForSecondMap contains statisticsForSecond(sum,avg.min,max,timestamp) for each second,
    //while adding transaction(timestamp,amount) to statisticsForSecond(sum,avg.min,max,timestamp) will check whether timestamp is expired
    //If yes statisticsForSecond values will be overwritten with transaction values else transaction values will be added
    //to statisticsForSecond
    public void addTransactionAndComputeStatistics(Transaction transaction) {
        int second = TransactionUtil.getSecondFromTimestamp(transaction.getTimestamp());

        statisticsForSecondMap.compute(second, (key, value) -> {
            if (value == null || (System.currentTimeMillis() - value.getTimestamp()) / 1000 >= TransactionUtil.TIME_BOUND_IN_SECONDS) {
                value = new StatisticsForSecond();
                value.setTimestamp(transaction.getTimestamp());
                value.setSum(transaction.getAmount());
                value.setMax(transaction.getAmount());
                value.setMin(transaction.getAmount());
                value.setCount(1l);
                return value;
            }

            value.setCount(value.getCount() + 1);
            value.setSum(value.getSum() + transaction.getAmount());
            if (Double.compare(transaction.getAmount(), value.getMax()) > 0) value.setMax(transaction.getAmount());
            if (Double.compare(transaction.getAmount(), value.getMin()) < 0) value.setMin(transaction.getAmount());
            return value;
        });
    }

    //Time complexity for computing statistics is O(1) because calculations made by adding/removing already computed statistics
    //computes statistics summary for none expired entries in statisticsForSecondMap based on timestamp
    public StatisticsForMinute getStatisticsForLastMin() {
        StatisticsForMinute statisticsForMinute = statisticsForSecondMap.values().stream()
                .filter(s -> (System.currentTimeMillis() - s.getTimestamp()) / 1000 < TransactionUtil.TIME_BOUND_IN_SECONDS)
                .map(StatisticsForMinute::new)
                .reduce(new StatisticsForMinute(), (s1, s2) -> {
                    s1.setSum(s1.getSum() + s2.getSum());
                    s1.setCount(s1.getCount() + s2.getCount());
                    s1.setMax(Double.compare(s1.getMax(), s2.getMax()) > 0 ? s1.getMax() : s2.getMax());
                    s1.setMin(Double.compare(s1.getMin(), s2.getMin()) < 0 ? s1.getMin() : s2.getMin());
                    return s1;
                });

        statisticsForMinute.setMin(Double.compare(statisticsForMinute.getMin(), Double.MAX_VALUE) == 0 ? 0.0 : statisticsForMinute.getMin());
        statisticsForMinute.setMax(Double.compare(statisticsForMinute.getMax(), Double.MIN_VALUE) == 0 ? 0.0 : statisticsForMinute.getMax());
        statisticsForMinute.setAvg(statisticsForMinute.getCount() > 0l ? statisticsForMinute.getSum() / statisticsForMinute.getCount() : 0.0);

        return statisticsForMinute;
    }


}
