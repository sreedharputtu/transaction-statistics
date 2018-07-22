package org.n26.transaction.stats.dto;


public class StatisticsForSecond {

    private Long timestamp;

    private Long count;

    private Double sum;

    private Double avg;

    private Double min;

    private Double max;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Double getAvg() {
        return avg;
    }

    public void setAvg(Double avg) {
        this.avg = avg;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "StatisticsForSecond{" +
                "sum=" + sum +
                ", count=" + count +
                ", max=" + max +
                ", min=" + min +
                ", avg=" + avg +
                '}';
    }
}

