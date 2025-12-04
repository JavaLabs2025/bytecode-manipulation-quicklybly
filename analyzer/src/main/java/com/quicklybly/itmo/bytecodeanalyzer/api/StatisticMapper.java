package com.quicklybly.itmo.bytecodeanalyzer.api;

public class StatisticMapper {

    private StatisticMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static String toJson(Statistic statistic) {
        return String.format(
                "{"
                        + "\"fieldAverage\":%s,"
                        + "\"averageInheritanceDepth\":%s,"
                        + "\"averageOverriddenCountHolder\":%s,"
                        + "\"maxInheritanceDepth\":%d,"
                        + "\"abc\":%s"
                        + "}",
                statistic.getAverageFieldCount(),
                statistic.getAverageInheritanceDepth(),
                statistic.getAverageOverriddenCount(),
                statistic.getMaxInheritanceDepth(),
                statistic.getAbcMetric().getAbc()
        );
    }
}
