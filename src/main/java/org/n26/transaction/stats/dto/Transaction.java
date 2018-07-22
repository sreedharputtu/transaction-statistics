package org.n26.transaction.stats.dto;

public class Transaction {

    private Long timestamp;

    private Double amount;

    public Transaction(Double amount,Long timestamp){
        this.timestamp = timestamp;
        this.amount = amount;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
