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
public class BreakdownComplexMatchersTest {

    private CucumberScenarioResult scenario;
    private DataDimension filter;
    private boolean expectedMatch;

    public BreakdownComplexMatchersTest(DataDimension filterValue,
            boolean expectedMatchValue) throws Exception {
        super();
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

    @Parameters
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
            {
                new DataDimension(DimensionValue.AND, "(.*)", new DataDimension[] {
                    new DataDimension(DimensionValue.SCENARIO, "Add(.*)"),
                    new DataDimension(DimensionValue.SCENARIO, "(.*)card"),
                }),
                true
            },
            {
                new DataDimension(DimensionValue.AND, "(.*)", new DataDimension[] {
                    new DataDimension(DimensionValue.SCENARIO, "Add(.*)"),
                    new DataDimension(DimensionValue.SCENARIO, "card"),
                }),
                false
            },
            {
                new DataDimension(DimensionValue.NOT, "(.*)", new DataDimension[] {
                    new DataDimension(DimensionValue.SCENARIO, "Non-existing")
                }),
                true
            },
            {
                new DataDimension(DimensionValue.NOT, "(.*)", new DataDimension[] {
                    new DataDimension(DimensionValue.SCENARIO, "Add(.*)")
                }),
                false
            },
            {
                new DataDimension(DimensionValue.NOT, "(.*)", new DataDimension[] {}),
                false
            },
            {
                new DataDimension(DimensionValue.NOT, "(.*)", null),
                false
            },
            {
                new DataDimension(DimensionValue.OR, "(.*)", new DataDimension[] {
                    new DataDimension(DimensionValue.SCENARIO, "Add(.*)"),
                    new DataDimension(DimensionValue.SCENARIO, "(.*)card"),
                }),
                true
            },
            {
                new DataDimension(DimensionValue.OR, "(.*)", new DataDimension[] {
                    new DataDimension(DimensionValue.SCENARIO, "Add(.*)"),
                    new DataDimension(DimensionValue.SCENARIO, "card"),
                }),
                true
            },
            {
                new DataDimension(DimensionValue.OR, "(.*)", new DataDimension[] {
                    new DataDimension(DimensionValue.SCENARIO, "Add"),
                    new DataDimension(DimensionValue.SCENARIO, "card"),
                }),
                false
            },
        });
    }

    @Test
    public void testMatchersProcessing() {
        Matcher matcher = BaseMatcher.create(filter.getDimensionValue());
        Assert.assertEquals(expectedMatch, matcher.matches(scenario, filter));
        Assert.assertFalse(matcher.matches(scenario, "(.*)"));
        Assert.assertTrue(matcher.isComplex());
    }
}
