/**
 * .
 */
package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Assert;

import com.cedarsoftware.util.io.JsonObject;
import com.cedarsoftware.util.io.JsonReader;
import com.github.mkolisnyk.cucumber.reporting.interfaces.CucumberResultsCommon;
import com.github.mkolisnyk.cucumber.reporting.interfaces.SimpleReport;
import com.github.mkolisnyk.cucumber.reporting.types.beans.UsageDataBean;
import com.github.mkolisnyk.cucumber.reporting.types.beans.UsageDataBean.StepSourceData;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportError;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportLink;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportTypes;
import com.github.mkolisnyk.cucumber.reporting.types.usage.CucumberStep;
import com.github.mkolisnyk.cucumber.reporting.types.usage.CucumberStepSource;
import com.github.mkolisnyk.cucumber.reporting.utils.helpers.MapUtils;

public class CucumberUsageReporting extends SimpleReport {

    private String[]       jsonUsageFiles;

    public String getDescription(Locale arg0) {
        return "HTML formatted Cucumber keywords usage report";
    }

    public String getName(Locale arg0) {
        return "Cucumber usage report";
    }

    public String getJsonUsageFile() {
        return jsonUsageFiles[0];
    }

    public void setJsonUsageFile(String jsonUsageFileValue) {
        this.jsonUsageFiles = new String[] {jsonUsageFileValue};
    }

    public String[] getJsonUsageFiles() {
        return jsonUsageFiles;
    }

    public void setJsonUsageFiles(String[] jsonUsageFilesValue) {
        this.jsonUsageFiles = jsonUsageFilesValue;
    }

    public LinkedHashMap<String, Integer> calculateStepsUsageScore(CucumberStepSource[] sources) {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();

        for (CucumberStepSource source:sources) {
            int totalSteps = 0;
            for (CucumberStep step:source.getSteps()) {
                totalSteps += step.getDurations().length;
            }
            map.put(source.getSource(), totalSteps);
        }

        map = (LinkedHashMap<String, Integer>) MapUtils.sortByValue(map);

        return map;
    }

    public SortedMap<Integer, Integer> calculateStepsUsageCounts(CucumberStepSource[] sources) {
        SortedMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
        for (CucumberStepSource source:sources) {
            int stepsCount = 0;

            for (CucumberStep step:source.getSteps()) {
                stepsCount += step.getDurations().length;
            }

            if (!map.containsKey(stepsCount)) {
                map.put(stepsCount, 1);
            } else {
                int prevNum = map.get(stepsCount);
                prevNum++;
                map.remove(stepsCount);
                map.put(stepsCount, prevNum);
            }
        }
        return map;
    }

    public double calculateStepsUsageAverage(SortedMap<Integer, Integer> statistics) {
        int totalSteps = 0;
        int totalUniqueSteps = 0;

        for (int i:statistics.keySet()) {
            totalSteps += i * statistics.get(i);
            totalUniqueSteps += statistics.get(i);
        }
        if (totalUniqueSteps == 0) {
            totalUniqueSteps = 1;
        }
        return (double) totalSteps / (double) totalUniqueSteps;
    }

    public int calculateStepsUsageMedian(SortedMap<Integer, Integer> statistics) {
        int totalSteps = 0;
        int usedSteps = 0;
        int median = 0;
        for (int i:statistics.keySet()) {
            totalSteps += statistics.get(i);
        }
        for (int i:statistics.keySet()) {
            usedSteps += statistics.get(i);
            if (usedSteps * 2 >= totalSteps) {
                median = i;
                break;
            }
        }
        return median;
    }

    public int calculateTotalSteps(SortedMap<Integer, Integer> statistics) {
        int totalSteps = 0;

        for (int i:statistics.keySet()) {
            totalSteps += i * statistics.get(i);
        }
        return totalSteps;
    }

    public int calculateUsedSteps(SortedMap<Integer, Integer> statistics) {
        int usedSteps = 0;

        for (int i:statistics.keySet()) {
            usedSteps += statistics.get(i);
        }
        return usedSteps;
    }

    public int calculateStepsUsageMax(SortedMap<Integer, Integer> statistics) {
        int max = 0;
        for (int i:statistics.keySet()) {
            max = Math.max(max, statistics.get(i));
        }
        return max;
    }

    private CucumberStepSource getSourceByString(CucumberStepSource[] sources, String text) {
        for (CucumberStepSource source : sources) {
            if (source.getSource().equals(text)) {
                return source;
            }
        }
        return null;
    }
    public int getDurationGroupsCount(CucumberStepSource source) {
        final int minimalDurations = 5;
        final int minimalDurationSize = 3;
        final int maxDurationGroups = 10;
        List<Double> durations = source.getDurations();
        if (durations.size() <= minimalDurations) {
            return 0;
        }
        if (durations.size() < minimalDurationSize * maxDurationGroups) {
            return durations.size() / minimalDurationSize;
        }
        return maxDurationGroups;
    }

    public int[] getFrequencyData(CucumberStepSource source) {
        final int minimalDurations = 5;
        int[] result = new int[]{};
        List<Double> durations = source.getDurations();
        if (durations.size() <= minimalDurations) {
            return result;
        }
        double minDuration = this.getMinDuration(source);
        double maxDuration = this.getMaxDuration(source);
        int count = getDurationGroupsCount(source);
        result = new int[count];
        for (int i = 0; i < count; i++) {
            result[i] = 0;
        }
        double step = (maxDuration - minDuration) / (double) count;
        for (Double duration : durations) {
            int index = (int) ((duration - minDuration) / step);
            if (index >= count) {
                index = count - 1;
            }
            result[index] = result[index] + 1;
        }
        return result;
    }

    private Double getMaxDuration(CucumberStepSource source) {
        List<Double> durations = source.getDurations();
        double maxDuration = durations.get(0);
        for (Double duration : durations) {
            maxDuration = Math.max(maxDuration, duration);
        }
        return maxDuration;
    }

    private Double getMinDuration(CucumberStepSource source) {
        List<Double> durations = source.getDurations();
        double minDuration = durations.get(0);
        for (Double duration : durations) {
            minDuration = Math.min(minDuration, duration);
        }
        return minDuration;
    }

    private double variance(List<Double> durations, double average) {
        double result = 0.f;
        for (double duration : durations) {
            result += (duration - average) * (duration - average);
        }
        if (durations.size() > 0) {
            result = result / durations.size();
        }
        return result;
    }
    private double skewness(List<Double> durations, double average) {
        double result = 0.f;
        final double precision = 0.000000001;
        final double extand = 1.5;
        for (double duration : durations) {
            result += (duration - average) * (duration - average) * (duration - average);
        }
        if (durations.size() > 0) {
            result = result / durations.size();
        }
        double variance = variance(durations, average);
        if (Math.abs(variance) < precision) {
            variance = 1.;
        }
        result = result / Math.pow(variance, extand);
        return result;
    }

    @SuppressWarnings("unchecked")
    public CucumberStepSource[] getStepSources(String filePath) throws Exception {
        FileInputStream fis = null;
        JsonReader jr = null;
        File file = new File(filePath);

        if (!(file.exists() && file.isFile())) {
            throw new FileNotFoundException();
        }

        fis = new FileInputStream(file);
        jr = new JsonReader(fis, true);
        //JsonObject<String, Object> source = (JsonObject<String, Object>) jr.readObject();
        Object[] objs = (Object[]) jr.readObject();

        CucumberStepSource[] sources = new CucumberStepSource[objs.length];
        for (int i = 0; i < objs.length; i++) {
            sources[i] = new CucumberStepSource((JsonObject<String, Object>) objs[i]);
        }
        jr.close();
        fis.close();
        return sources;
    }

    private void executeReport(String[] formats) throws Exception {
        try {

            CucumberStepSource[] sources = {};
            for (String jsonUsageFile : this.getJsonUsageFiles()) {
                sources = (CucumberStepSource[]) ArrayUtils.addAll(sources, getStepSources(jsonUsageFile));
            }

            File report = new File(this.getOutputDirectory() + File.separator + this.getOutputName() + "-usage.html");
            UsageDataBean data = new UsageDataBean();
            SortedMap<Integer, Integer> map = calculateStepsUsageCounts(sources);
            int max = calculateStepsUsageMax(map);
            int median = calculateStepsUsageMedian(map);
            double average = calculateStepsUsageAverage(map);
            data.setStepsUseMax(max);
            data.setStepsUseAverage(average);
            data.setStepsUseMedian(median);
            data.setUsageCounts(map);
            
            StepSourceData[] stepSourceData = new StepSourceData[sources.length];
            LinkedHashMap<String, Integer> stepsScoreMap = calculateStepsUsageScore(sources);
            int index = 0;
            for (String key:stepsScoreMap.keySet()) {
                CucumberStepSource source = getSourceByString(sources, key);
                stepSourceData[index] = data.new StepSourceData();
                stepSourceData[index].setSource(source);
                Double medianDuration = 0.;
                Double totalDuration = 0.;
                Double averageDuration = 0.;
                Double minDuration = 0.;
                Double maxDuration = 0.;
                
                if (source != null) {
                    List<Double> durations = source.getDurations();
                    if (durations.size() > 0) {
                        Collections.sort(durations);
                        medianDuration = durations.get(durations.size() / 2);
                        for (Double duration : durations) {
                            totalDuration += duration;
                        }
                        averageDuration = totalDuration / durations.size();
                        minDuration = Collections.min(durations);
                        maxDuration = Collections.max(durations);
                    }
                    stepSourceData[index].setTotalUsed(stepsScoreMap.get(key));
                    stepSourceData[index].setVariance(this.variance(durations, averageDuration));
                    stepSourceData[index].setSkewness(this.skewness(durations, averageDuration));
                    stepSourceData[index].setFrequencies(this.getFrequencyData(source));
                } else {
                    stepSourceData[index].setTotalUsed(0);
                    stepSourceData[index].setVariance(0);
                    stepSourceData[index].setSkewness(0);
                    stepSourceData[index].setFrequencies(new int[] {});
                }
                stepSourceData[index].setAverageDuration(averageDuration);
                stepSourceData[index].setMaxDuration(maxDuration);
                stepSourceData[index].setMedianDuration(medianDuration);
                stepSourceData[index].setMinDuration(minDuration);
                stepSourceData[index].setTotalDuration(totalDuration);
                index++;
            }
            data.setStepsData(stepSourceData);
            generateReportFromTemplate(report, "usage", data);
            this.export(report, "usage", formats, this.isImageExportable());
        } catch (Exception e) {
            throw new Exception(
                    "Error occured while generating Cucumber usage report", e);
        }
    }

    @Override
    public CucumberReportTypes getReportType() {
        return CucumberReportTypes.USAGE;
    }

    @Override
    public CucumberReportLink getReportDocLink() {
        return CucumberReportLink.USAGE_URL;
    }

    @Override
    public void validateParameters() {
        Assert.assertNotNull(this.constructErrorMessage(CucumberReportError.NO_SOURCE_FILE, ""),
                this.getJsonUsageFiles());
        Assert.assertNotNull(this.constructErrorMessage(CucumberReportError.NO_OUTPUT_DIRECTORY, ""),
                this.getOutputDirectory());
        Assert.assertNotNull(this.constructErrorMessage(CucumberReportError.NO_OUTPUT_NAME, ""),
                this.getOutputName());
        for (String sourceFile : this.getJsonUsageFiles()) {
            Assert.assertNotNull(
                    this.constructErrorMessage(CucumberReportError.NO_SOURCE_FILE, ""), sourceFile);
            File path = new File(sourceFile);
            Assert.assertTrue(this.constructErrorMessage(CucumberReportError.NON_EXISTING_SOURCE_FILE, "")
                    + ". Was looking for path: \"" + path.getAbsolutePath() + "\"", path.exists());
        }
    }

    @Override
    public void execute() throws Exception {
        this.execute(new String[] {});
    }

    @Override
    public void execute(String[] formats) throws Exception {
        this.validateParameters();
        this.executeReport(formats);
    }
    
}
