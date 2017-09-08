package com.github.mkolisnyk.cucumber.reporting.types.beans;

import java.util.Map;

import com.github.mkolisnyk.cucumber.reporting.types.usage.CucumberStepSource;

public class UsageDataBean extends CommonDataBean {
    public class StepSourceData {
        private CucumberStepSource source;
        private int totalUsed;
        private double minDuration;
        private double maxDuration;
        private double averageDuration;
        private double medianDuration;
        private double totalDuration;
        private int[] frequencies;
        private double variance;
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
    private Map<Integer, Integer> usageCounts;
    private double stepsUseAverage;
    private double stepsUseMedian;
    private int stepsUseMax;
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
