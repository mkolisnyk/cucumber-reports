package com.github.mkolisnyk.cucumber.reporting.types.beans;

import java.util.Map;

import com.github.mkolisnyk.cucumber.reporting.types.usage.CucumberStepSource;

/**
 * Data structure which is used for the <a href="http://mkolisnyk.github.io/cucumber-reports/usage-report">
 * Usage Report</a> genearation.
 * @author Mykola Kolisnyk
 */
public class UsageDataBean extends CommonDataBean {
    /**
     * Represents the data structure containing all usage information for
     * specific step.
     * @author Mykola Kolisnyk
     */
    public class StepSourceData {
        /**
         * The original step source data taken from original Cucumber-JVM usage report.
         */
        private CucumberStepSource source;
        /**
         * The number of times this specific step source was used during the run.
         */
        private int totalUsed;
        /**
         * Minimal duration of the step in seconds.
         */
        private double minDuration;
        /**
         * Maximal duration of the step in seconds.
         */
        private double maxDuration;
        /**
         * Average duration of the step in seconds.
         */
        private double averageDuration;
        /**
         * Median duration of the step in seconds.
         */
        private double medianDuration;
        /**
         * Total duration of the steps from current step source.
         */
        private double totalDuration;
        /**
         * The list of frequencies which is used for duration distribution
         * hystogram for each specific step source. All step durations are
         * split into several ranges (up to 10). The value of each element
         * contains the number of steps from current source which duration
         * fits this time range.
         */
        private int[] frequencies;
        /**
         * Statistical information showing step variance.
         */
        private double variance;
        /**
         * Statistical information showing step skewness.
         */
        private double skewness;
        public CucumberStepSource getSource() {
            return source;
        }
        public void setSource(CucumberStepSource sourceValue) {
            this.source = sourceValue;
        }
        public int getTotalUsed() {
            return totalUsed;
        }
        public void setTotalUsed(int totalUsedValue) {
            this.totalUsed = totalUsedValue;
        }
        public double getMinDuration() {
            return minDuration;
        }
        public void setMinDuration(double minDurationValue) {
            this.minDuration = minDurationValue;
        }
        public double getMaxDuration() {
            return maxDuration;
        }
        public void setMaxDuration(double maxDurationValue) {
            this.maxDuration = maxDurationValue;
        }
        public double getAverageDuration() {
            return averageDuration;
        }
        public void setAverageDuration(double averageDurationValue) {
            this.averageDuration = averageDurationValue;
        }
        public double getMedianDuration() {
            return medianDuration;
        }
        public void setMedianDuration(double medianDurationValue) {
            this.medianDuration = medianDurationValue;
        }
        public int[] getFrequencies() {
            return frequencies;
        }
        public void setFrequencies(int[] frequenciesValue) {
            this.frequencies = frequenciesValue;
        }
        public double getVariance() {
            return variance;
        }
        public void setVariance(double varianceValue) {
            this.variance = varianceValue;
        }
        public double getSkewness() {
            return skewness;
        }
        public void setSkewness(double skewnessValue) {
            this.skewness = skewnessValue;
        }
        public double getTotalDuration() {
            return totalDuration;
        }
        public void setTotalDuration(double totalDurationValue) {
            this.totalDuration = totalDurationValue;
        }
    }
    /**
     * The map containing step usage counts. In the basic usage report
     * this data is used for overview graph representation. Each value
     * contains the number of step source occurrences. The key represents
     * the number of the step sources with such frequency.
     */
    private Map<Integer, Integer> usageCounts;
    /**
     * The average number of steps use.
     */
    private double stepsUseAverage;
    /**
     * The median number of steps use.
     */
    private double stepsUseMedian;
    /**
     * The maximal number of use of single step source.
     */
    private int stepsUseMax;
    /**
     * Detailed steps data for all step sources. This array is already sorted
     * from the most frequently used source to the least used.
     */
    private StepSourceData[] stepsData;
    public Map<Integer, Integer> getUsageCounts() {
        return usageCounts;
    }
    public void setUsageCounts(Map<Integer, Integer> usageCountsValue) {
        this.usageCounts = usageCountsValue;
    }
    public double getStepsUseAverage() {
        return stepsUseAverage;
    }
    public void setStepsUseAverage(double stepsUseAverageValue) {
        this.stepsUseAverage = stepsUseAverageValue;
    }
    public double getStepsUseMedian() {
        return stepsUseMedian;
    }
    public void setStepsUseMedian(double stepsUseMedianValue) {
        this.stepsUseMedian = stepsUseMedianValue;
    }
    public int getStepsUseMax() {
        return stepsUseMax;
    }
    public void setStepsUseMax(int stepsUseMaxValue) {
        this.stepsUseMax = stepsUseMaxValue;
    }
    public StepSourceData[] getStepsData() {
        return stepsData;
    }
    public void setStepsData(StepSourceData[] stepsDataValue) {
        this.stepsData = stepsDataValue;
    }
}
