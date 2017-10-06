package com.github.mkolisnyk.cucumber.reporting.types.result;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.Assert;

@RunWith(Parameterized.class)
public class CucumberScenarioResultTest {
    private int passed = 0;
    private int failed = 0;
    private int known = 0;
    private int undefined = 0;
    private int skipped = 0;
    private String expectedStatus;

    public CucumberScenarioResultTest(int passed, int failed, int known,
            int undefined, int skipped, String expectedStatus) {
        super();
        this.passed = passed;
        this.failed = failed;
        this.known = known;
        this.undefined = undefined;
        this.skipped = skipped;
        this.expectedStatus = expectedStatus;
    }

    @Parameters
    public static Collection<Object[]> getParameters() {

        return Arrays.asList(new Object[][] {
            {0, 0, 0, 0, 0, "undefined"},
            {0, 0, 0, 0, 1, "skipped"},
            {0, 0, 0, 1, 0, "undefined"},
            {0, 0, 1, 0, 0, "known"},
            {0, 1, 0, 0, 0, "failed"},
            {1, 0, 0, 0, 0, "passed"},
            {1, 1, 1, 1, 1, "failed"},
            {1, 0, 1, 1, 1, "known"},
            {1, 0, 0, 1, 1, "undefined"},
            {1, 0, 0, 0, 1, "skipped"},
        });
    }

    @Test
    public void testGetStatus() throws Exception {
        CucumberScenarioResult result = new CucumberScenarioResult();
        result.setPassed(passed);
        result.setFailed(failed);
        result.setKnown(known);
        result.setSkipped(skipped);
        result.setUndefined(undefined);
        Assert.assertEquals(result.getStatus(false), this.expectedStatus);
    }
}
