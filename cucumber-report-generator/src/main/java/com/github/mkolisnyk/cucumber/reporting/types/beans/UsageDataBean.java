package com.github.mkolisnyk.cucumber.reporting.types.beans;

import java.util.Map;
import java.util.SortedMap;

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
        public void setSource(CucumberStepSource source) {
            this.source = source;
        }
        public int getTotalUsed() {
            return totalUsed;
        }
        public void setTotalUsed(int totalUsed) {
            this.totalUsed = totalUsed;
        }
        public double getMinDuration() {
            return minDuration;
        }
        public void setMinDuration(double minDuration) {
            this.minDuration = minDuration;
        }
        public double getMaxDuration() {
            return maxDuration;
        }
        public void setMaxDuration(double maxDuration) {
            this.maxDuration = maxDuration;
        }
        public double getAverageDuration() {
            return averageDuration;
        }
        public void setAverageDuration(double averageDuration) {
            this.averageDuration = averageDuration;
        }
        public double getMedianDuration() {
            return medianDuration;
        }
        public void setMedianDuration(double medianDuration) {
            this.medianDuration = medianDuration;
        }
        public int[] getFrequencies() {
            return frequencies;
        }
        public void setFrequencies(int[] frequencies) {
            this.frequencies = frequencies;
        }
        public double getVariance() {
            return variance;
        }
        public void setVariance(double variance) {
            this.variance = variance;
        }
        public double getSkewness() {
            return skewness;
        }
        public void setSkewness(double skewness) {
            this.skewness = skewness;
        }
        public double getTotalDuration() {
            return totalDuration;
        }
        public void setTotalDuration(double totalDuration) {
            this.totalDuration = totalDuration;
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
    public void setUsageCounts(Map<Integer, Integer> usageCounts) {
        this.usageCounts = usageCounts;
    }
    public double getStepsUseAverage() {
        return stepsUseAverage;
    }
    public void setStepsUseAverage(double stepsUseAverage) {
        this.stepsUseAverage = stepsUseAverage;
    }
    public double getStepsUseMedian() {
        return stepsUseMedian;
    }
    public void setStepsUseMedian(double stepsUseMedian) {
        this.stepsUseMedian = stepsUseMedian;
    }
    public int getStepsUseMax() {
        return stepsUseMax;
    }
    public void setStepsUseMax(int stepsUseMax) {
        this.stepsUseMax = stepsUseMax;
    }
    public StepSourceData[] getStepsData() {
        return stepsData;
    }
    public void setStepsData(StepSourceData[] stepsData) {
        this.stepsData = stepsData;
    }
}
