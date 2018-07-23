package com.github.mkolisnyk.cucumber.runner.runtime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ExtendedRuntimeOptionsListConstructorReportFlagsTest {

    private String reportString = "";
    private boolean isOverviewReport = false;
    private boolean isOverviewChartsReport = false;
    private boolean isUsageReport = false;
    private boolean isDetailedReport = false;
    private boolean isDetailedAggregatedReport = false;
    private boolean isCoverageReport = false;
    private boolean breakdownReport = false;
    private boolean featureMapReport = false;
    private boolean featureOverviewChart = false;
    private boolean knownErrorsReport = false;
    private boolean consolidatedReport = false;

    public ExtendedRuntimeOptionsListConstructorReportFlagsTest(
            String reportString,
            boolean isOverviewReport, boolean isOverviewChartsReport,
            boolean isUsageReport, boolean isDetailedReport,
            boolean isDetailedAggregatedReport, boolean isCoverageReport,
            boolean breakdownReport, boolean featureMapReport,
            boolean featureOverviewChart, boolean knownErrorsReport,
            boolean consolidatedReport) {
        super();
        this.reportString = reportString;
        this.isOverviewReport = isOverviewReport;
        this.isOverviewChartsReport = isOverviewChartsReport;
        this.isUsageReport = isUsageReport;
        this.isDetailedReport = isDetailedReport;
        this.isDetailedAggregatedReport = isDetailedAggregatedReport;
        this.isCoverageReport = isCoverageReport;
        this.breakdownReport = breakdownReport;
        this.featureMapReport = featureMapReport;
        this.featureOverviewChart = featureOverviewChart;
        this.knownErrorsReport = knownErrorsReport;
        this.consolidatedReport = consolidatedReport;
    }

    @Parameters(name = "{index} - \"{0}\"")
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
                {"o",  true, false,  false, false, false, false, false, false, false, false, false},
                {"O", false,  true,  false, false, false, false, false, false, false, false, false},
                {"u", false, false,   true, false, false, false, false, false, false, false, false},
                {"d", false, false,  false,  true, false, false, false, false, false, false, false},
                {"a", false, false,  false, false,  true, false, false, false, false, false, false},
                {"c", false, false,  false, false, false,  true, false, false, false, false, false},
                {"B", false, false,  false, false, false, false,  true, false, false, false, false},
                {"F", false, false,  false, false, false, false, false,  true, false, false, false},
                {"f", false, false,  false, false, false, false, false, false,  true, false, false},
                {"K", false, false,  false, false, false, false, false, false, false,  true, false},
                {"C", false, false,  false, false, false, false, false, false, false, false,  true},
                
                {"odBK"       ,  true, false,  false,  true, false, false,  true, false, false,  true, false},
                {"OaFC"       , false,  true,  false, false,  true, false, false,  true, false, false,  true},
                {"oucf"       ,  true, false,   true, false, false,  true, false, false,  true, false, false},
                {"oOudacBFfKC",  true,  true,   true,  true,  true,  true,  true,  true,  true,  true,  true},
                {""           , false, false,  false, false, false, false, false, false, false, false, false},
        });
    }

    @Test
    public void testInitializeReportFlags() throws Exception {
        ArrayList<String> input = new ArrayList<String>(Arrays.asList(new String[] {"-r", reportString}));
        ExtendedRuntimeOptions extendedOptions = new ExtendedRuntimeOptions(input);
        Assert.assertEquals(0, input.size());
        Assert.assertEquals(this.isOverviewReport, extendedOptions.isOverviewReport());
        Assert.assertEquals(this.isOverviewChartsReport, extendedOptions.isOverviewChartsReport());
        Assert.assertEquals(this.isUsageReport, extendedOptions.isUsageReport());
        Assert.assertEquals(this.isDetailedReport, extendedOptions.isDetailedReport());
        Assert.assertEquals(this.isDetailedAggregatedReport, extendedOptions.isDetailedAggregatedReport());
        Assert.assertEquals(this.isCoverageReport, extendedOptions.isCoverageReport());
        Assert.assertEquals(this.breakdownReport, extendedOptions.isBreakdownReport());
        Assert.assertEquals(this.featureMapReport, extendedOptions.isFeatureMapReport());
        Assert.assertEquals(this.featureOverviewChart, extendedOptions.isFeatureOverviewChart());
        Assert.assertEquals(this.knownErrorsReport, extendedOptions.isKnownErrorsReport());
        Assert.assertEquals(this.consolidatedReport, extendedOptions.isConsolidatedReport());
    }
}
