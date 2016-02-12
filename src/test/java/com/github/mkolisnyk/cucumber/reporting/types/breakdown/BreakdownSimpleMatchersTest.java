package com.github.mkolisnyk.cucumber.reporting.types.breakdown;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.cedarsoftware.util.io.JsonObject;
import com.cedarsoftware.util.io.JsonReader;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.matchers.BaseMatcher;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.matchers.Matcher;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;

@RunWith(Parameterized.class)
public class BreakdownSimpleMatchersTest {

    private CucumberScenarioResult scenario;
    private DimensionValue matchType;
    private String filter;
    private boolean expectedMatch;

    @Parameters
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
            {DimensionValue.SCENARIO, "(.*)", true},
            {DimensionValue.SCENARIO, "Non-existing scenario name", false},
            {DimensionValue.STEP, "(.*)", true},
            {DimensionValue.STEP, "Non-existing step name", false},
            {DimensionValue.STEP_PARAM, "(.*)", true},
            {DimensionValue.STEP_PARAM, "Non-existing param", false},
            {DimensionValue.TAG, "(.*)", true},
            {DimensionValue.TAG, "Non-existing tag", false},
            {DimensionValue.FAILED_STEP, "(.*)", true},
            {DimensionValue.FAILED_STEP, "Non-existing failed step", false},
            //{DimensionValue.ERROR_MESSAGE, "(.*)", true},
            {DimensionValue.ERROR_MESSAGE, "Non-existing error message", false},
        });
    }

    public BreakdownSimpleMatchersTest(DimensionValue matchTypeValue, String filterValue,
            boolean expectedMatchValue) throws Exception {
        super();
        this.matchType = matchTypeValue;
        this.filter = filterValue;
        this.expectedMatch = expectedMatchValue;

        FileInputStream fis = new FileInputStream(new File("src/test/resources/sample_scenario.json"));
        JsonReader jr = new JsonReader(fis, true);
        JsonObject<String, Object> source = (JsonObject<String, Object>) jr.readObject();
        this.scenario = new CucumberScenarioResult(source);
        jr.close();
        fis.close();
        this.scenario.valuate();
    }

    @Test
    public void testMatchersProcessing() {
        Matcher matcher = BaseMatcher.create(matchType);
        Assert.assertEquals(expectedMatch, matcher.matches(scenario, filter));
        Assert.assertEquals(expectedMatch, matcher.matches(scenario, new DataDimension(matchType, filter)));
        Assert.assertFalse(matcher.isComplex());
    }
}
