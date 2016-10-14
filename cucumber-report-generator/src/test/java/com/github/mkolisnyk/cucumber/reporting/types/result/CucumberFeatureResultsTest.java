package com.github.mkolisnyk.cucumber.reporting.types.result;

import org.junit.Assert;
import org.junit.Test;

import com.github.mkolisnyk.cucumber.reporting.CucumberFeatureOverview;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.DataDimension;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.DimensionValue;
import com.github.mkolisnyk.cucumber.reporting.types.knownerrors.KnownErrorOrderBy;
import com.github.mkolisnyk.cucumber.reporting.types.knownerrors.KnownErrorPriority;
import com.github.mkolisnyk.cucumber.reporting.types.knownerrors.KnownErrorsInfo;
import com.github.mkolisnyk.cucumber.reporting.types.knownerrors.KnownErrorsModel;

public class CucumberFeatureResultsTest {

    @Test
    public void testSimpleFeatureResultsValuation() throws Exception {
        CucumberFeatureOverview report = new CucumberFeatureOverview();
        CucumberFeatureResult[] features = report.readFileContent("src/test/resources/86/cucumber.json", true);
        for (CucumberFeatureResult feature : features) {
            feature.valuate();
        }
        Assert.assertEquals("The size of feature list is unexpected", features.length, 2);
        Assert.assertEquals("The number of scenarios is unexpected", features[0].getElements().length, 1);
        Assert.assertEquals("The failed number is unexpected", 0, features[0].getFailed());
        Assert.assertEquals("The passed number is unexpected", 1, features[0].getPassed());
        Assert.assertEquals("The failed number is unexpected", 1, features[1].getFailed());
        Assert.assertEquals("The passed number is unexpected", 0, features[1].getPassed());
    }

    @Test
    public void testKEFeatureResultsValuation() throws Exception {
        KnownErrorsModel model = new KnownErrorsModel(
            new KnownErrorsInfo[] {
               new KnownErrorsInfo(
                   "UI Mismatch. Fix it!!!",
                   "UI locator was changed so now element cannot be found",
                   new DataDimension(DimensionValue.FAILED_STEP,
                       "(.*)search for journey using the following criteria(.*)"),
                   KnownErrorPriority.HIGHEST)
            },
            KnownErrorOrderBy.FREQUENCY
        );
        CucumberFeatureOverview report = new CucumberFeatureOverview();
        CucumberFeatureResult[] features = report.readFileContent("src/test/resources/86/cucumber.json", true);
        for (CucumberFeatureResult feature : features) {
            feature.valuateKnownErrors(model);
            feature.valuate();
        }
        Assert.assertEquals("The size of feature list is unexpected", features.length, 2);
        Assert.assertEquals("The number of scenarios is unexpected", features[0].getElements().length, 1);
        Assert.assertEquals("The failed number is unexpected", 0, features[0].getFailed());
        Assert.assertEquals("The passed number is unexpected", 1, features[0].getPassed());
        Assert.assertEquals("The failed number is unexpected", 0, features[1].getFailed());
        Assert.assertEquals("The passed number is unexpected", 0, features[1].getPassed());
        Assert.assertEquals("The known number is unexpected", 1, features[1].getKnown());
    }
}
