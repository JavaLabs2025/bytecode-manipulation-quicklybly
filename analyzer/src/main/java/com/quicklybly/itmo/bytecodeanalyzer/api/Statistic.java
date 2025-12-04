package com.quicklybly.itmo.bytecodeanalyzer.api;

import com.quicklybly.itmo.bytecodeanalyzer.model.AbcMetric;
import com.quicklybly.itmo.bytecodeanalyzer.model.AverageHolder;

public class Statistic {

    private final AverageHolder fieldAverageHolder = new AverageHolder();
    private final AverageHolder averageInheritanceDepthHolder = new AverageHolder();
    private final AverageHolder averageOverriddenCountHolder = new AverageHolder();
    private int maxInheritanceDepth = 0;
    private final AbcMetric abcMetric = new AbcMetric(0, 0, 0);

    public void updateFieldStatistic(Integer fieldCount) {
        fieldAverageHolder.updateAverage(fieldCount);
    }

    public void writeDepth(int depth) {
        averageInheritanceDepthHolder.updateAverage(depth);
        maxInheritanceDepth = Math.max(maxInheritanceDepth, depth);
    }

    public void writeOverriddenCount(int overriddenCount) {
        averageOverriddenCountHolder.updateAverage(overriddenCount);
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "fieldAverageHolder=" + fieldAverageHolder +
                ", averageInheritanceDepthHolder=" + averageInheritanceDepthHolder +
                ", averageOverriddenCountHolder=" + averageOverriddenCountHolder +
                ", maxInheritanceDepth=" + maxInheritanceDepth +
                ", abcMetric=" + abcMetric +
                '}';
    }

    public AbcMetric getAbcMetric() {
        return abcMetric;
    }

    public double getAverageFieldCount() {
        return fieldAverageHolder.getAverage();
    }

    public double getAverageInheritanceDepth() {
        return averageInheritanceDepthHolder.getAverage();
    }

    public double getAverageOverriddenCount() {
        return averageOverriddenCountHolder.getAverage();
    }

    public int getMaxInheritanceDepth() {
        return maxInheritanceDepth;
    }
}
