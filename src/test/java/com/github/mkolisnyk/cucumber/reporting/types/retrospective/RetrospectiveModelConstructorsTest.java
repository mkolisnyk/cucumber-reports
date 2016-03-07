package com.github.mkolisnyk.cucumber.reporting.types.retrospective;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import org.junit.Assert;

@RunWith(Parameterized.class)
public class RetrospectiveModelConstructorsTest {

    private RetrospectiveModel model;
    private String reportSuffixValue;
    private String titleValue;
    private String maskValue;
    private RetrospectiveOrderBy orderByValue;
    private String redirectToValue;
    private int refreshTimeoutValue;
    private int widthValue;
    private int heightValue;
    
    public RetrospectiveModelConstructorsTest(RetrospectiveModel model,
            String reportSuffixValue, String titleValue, String maskValue,
            RetrospectiveOrderBy orderByValue, String redirectToValue,
            int refreshTimeoutValue, int widthValue, int heightValue) {
        super();
        this.model = model;
        this.reportSuffixValue = reportSuffixValue;
        this.titleValue = titleValue;
        this.maskValue = maskValue;
        this.orderByValue = orderByValue;
        this.redirectToValue = redirectToValue;
        this.refreshTimeoutValue = refreshTimeoutValue;
        this.widthValue = widthValue;
        this.heightValue = heightValue;
    }

    @Parameters
    public static Collection<Object[]> getParameters() {

        return Arrays.asList(new Object[][] {
            {
                new RetrospectiveModel("Suffix", "Title", "Mask",
                    RetrospectiveOrderBy.NAME, "Redirect To", 30, 200, 350),
                "Suffix", "Title", "Mask", RetrospectiveOrderBy.NAME, "Redirect To", 30, 200, 350
            },
            {
                new RetrospectiveModel("Suffix", "Title", "Mask",
                    RetrospectiveOrderBy.NAME, "Redirect To", 30),
                "Suffix", "Title", "Mask", RetrospectiveOrderBy.NAME, "Redirect To", 30, 450, 300
            },
            {
                new RetrospectiveModel("Suffix", "Title", "Mask",
                    RetrospectiveOrderBy.NAME, 200, 350),
                "Suffix", "Title", "Mask", RetrospectiveOrderBy.NAME, "", 0, 200, 350
            },
            
            {
                new RetrospectiveModel("Suffix", "Title", "Mask", 200, 350),
                "Suffix", "Title", "Mask", RetrospectiveOrderBy.DATE_CREATED, "", 0, 200, 350
            },
            {
                new RetrospectiveModel("Suffix", "Title", "Mask", RetrospectiveOrderBy.NAME),
                "Suffix", "Title", "Mask", RetrospectiveOrderBy.NAME, "", 0, 450, 300
            },
            {
                new RetrospectiveModel("Suffix", "Title", "Mask"),
                "Suffix", "Title", "Mask", RetrospectiveOrderBy.DATE_CREATED, "", 0, 450, 300
            },
            {
                new RetrospectiveModel("Suffix", "Title"),
                "Suffix", "Title", "(.*)", RetrospectiveOrderBy.DATE_CREATED, "", 0, 450, 300
            },
            {
                new RetrospectiveModel("Suffix"),
                "Suffix", "Retrospective Results", "(.*)", RetrospectiveOrderBy.DATE_CREATED, "", 0, 450, 300
            },
        });
    }
    
    @Test
    public void testVerifyModelInitialization() {
        Assert.assertEquals(this.reportSuffixValue, model.getReportSuffix());
        Assert.assertEquals(this.titleValue, model.getTitle());
        Assert.assertEquals(this.maskValue, model.getMask());
        Assert.assertEquals(this.orderByValue, model.getOrderBy());
        Assert.assertEquals(this.redirectToValue, model.getRedirectTo());
        Assert.assertEquals(this.refreshTimeoutValue, model.getRefreshTimeout());
        Assert.assertEquals(this.widthValue, model.getWidth());
        Assert.assertEquals(this.heightValue, model.getHeight());
    }
}
