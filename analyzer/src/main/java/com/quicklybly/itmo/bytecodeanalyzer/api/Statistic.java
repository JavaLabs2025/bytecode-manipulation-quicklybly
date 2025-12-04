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

    public void printStatistic() {
        System.out.println("Average field count: " + fieldAverageHolder.getAverage());
        System.out.println("ABC: " + abcMetric.getAbc());
        System.out.println("Inheritance max depth: " + maxInheritanceDepth);
        System.out.println("Inheritance average depth: " + averageInheritanceDepthHolder.getAverage());
        System.out.println("Average override count: " + averageOverriddenCountHolder.getAverage());
    }

    public AbcMetric getAbcMetric() {
        return abcMetric;
    }
}
