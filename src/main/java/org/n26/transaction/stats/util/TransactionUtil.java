package org.n26.transaction.stats.util;

import org.n26.transaction.stats.dto.Transaction;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TransactionUtil {

    public static Integer TIME_BOUND_IN_SECONDS = 60;

    public static boolean isInLastMinute(Transaction transaction) {
        if ((System.currentTimeMillis() - transaction.getTimestamp()) / 1000 < TIME_BOUND_IN_SECONDS)
            return true;
        return false;
    }

    public static int getSecondFromTimestamp(Long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()).getSecond();
    }


}
