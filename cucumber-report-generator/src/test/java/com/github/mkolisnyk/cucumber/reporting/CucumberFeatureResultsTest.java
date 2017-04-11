package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.cedarsoftware.util.io.JsonObject;
import com.cedarsoftware.util.io.JsonReader;
import com.github.mkolisnyk.cucumber.reporting.types.OverviewStats;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;

@RunWith(Parameterized.class)
public class CucumberFeatureResultsTest {

    private CucumberFeatureResult result;

    private String json;
    private int featuresPassed;
    private int featuresFailed;
    private int featuresUndefined;
    private int scenarioPassed;
    private int scenarioFailed;
    private int scenarioUndefined;
    private int stepsPassed;
    private int stepsFailed;
    private int stepsUndefined;

    public CucumberFeatureResultsTest(String json, int featuresPassed,
            int featuresFailed, int featuresUndefined, int scenarioPassed,
            int scenarioFailed, int scenarioUndefined, int stepsPassed,
            int stepsFailed, int stepsUndefined) {
        super();
        this.json = json;
        this.featuresPassed = featuresPassed;
        this.featuresFailed = featuresFailed;
        this.featuresUndefined = featuresUndefined;
        this.scenarioPassed = scenarioPassed;
        this.scenarioFailed = scenarioFailed;
        this.scenarioUndefined = scenarioUndefined;
        this.stepsPassed = stepsPassed;
        this.stepsFailed = stepsFailed;
        this.stepsUndefined = stepsUndefined;
    }



    @Parameters
    public static Collection<Object[]> getParameters() throws IOException {
        return Arrays.asList(new Object[][] {
            {FileUtils.readFileToString(new File("src/test/resources/background-source/failed-background.json")), 0, 1, 0, 0, 3, 0, 3, 3, 6},
            {FileUtils.readFileToString(new File("src/test/resources/background-source/failed-step.json")), 0, 1, 0, 2, 1, 0, 10, 1, 1}, 
        });
    }
    @Test
    public void testValuate() throws IOException {
        JsonReader jr = new JsonReader(IOUtils.toInputStream(json, "UTF-8"), true);
        Object[] objs = (Object[]) jr.readObject();

        CucumberFeatureResult[] features = new CucumberFeatureResult[objs.length];
        for (int i = 0; i < objs.length; i++) {
            features[i] = new CucumberFeatureResult((JsonObject<String, Object>) objs[i]);
        }
        jr.close();

        OverviewStats actualStats = new OverviewStats().valuate(features);
        Assert.assertEquals(this.featuresPassed, actualStats.getFeaturesPassed());
        Assert.assertEquals(this.featuresFailed, actualStats.getFeaturesFailed());
        Assert.assertEquals(this.featuresUndefined, actualStats.getFeaturesUndefined());
        Assert.assertEquals(this.scenarioPassed, actualStats.getScenariosPassed());
        Assert.assertEquals(this.scenarioFailed, actualStats.getScenariosFailed());
        Assert.assertEquals(this.scenarioUndefined, actualStats.getScenariosUndefined());
        Assert.assertEquals(this.stepsPassed, actualStats.getStepsPassed());
        Assert.assertEquals(this.stepsFailed, actualStats.getStepsFailed());
        Assert.assertEquals(this.stepsUndefined, actualStats.getStepsUndefined());
    }
}
