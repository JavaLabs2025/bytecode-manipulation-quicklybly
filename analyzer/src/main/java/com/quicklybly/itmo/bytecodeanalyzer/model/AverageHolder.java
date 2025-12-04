package com.quicklybly.itmo.bytecodeanalyzer.model;

public class AverageHolder {
    private final Object lock = new Object();
    private Integer sum = 0;
    private Integer count = 0;

    public Double getAverage() {
        return count.equals(0) ? 0.0 : sum.doubleValue() / count;
    }

    public void updateAverage(Integer value) {
        synchronized (lock) {
            sum += value;
            count++;
        }
    }
}
