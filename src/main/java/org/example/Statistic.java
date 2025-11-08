package org.example;

import org.example.util.AverageHolder;

public class Statistic {

    private final AverageHolder fieldAverageHolder = new AverageHolder();

    public void updateFieldStatistic(Integer fieldCount) {
        fieldAverageHolder.updateAverage(fieldCount);
    }

    public void printStatistic() {
        System.out.print("Average: " + fieldAverageHolder.getAverage());
    }
}
