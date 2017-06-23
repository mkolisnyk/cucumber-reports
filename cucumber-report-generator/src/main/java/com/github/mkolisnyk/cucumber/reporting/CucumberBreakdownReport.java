package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;

import com.github.mkolisnyk.cucumber.reporting.interfaces.ConfigurableReport;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownCellDisplayType;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownReportInfo;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownReportModel;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownStats;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownTable;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.DataDimension;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportError;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportLink;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportTypes;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;
import com.github.mkolisnyk.cucumber.reporting.utils.drawers.PieChartDrawer;
import com.github.mkolisnyk.cucumber.reporting.utils.helpers.StringConversionUtils;
import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

public class CucumberBreakdownReport extends ConfigurableReport<BreakdownReportModel> {
    public CucumberBreakdownReport() {
        super();
    }
    public CucumberBreakdownReport(ExtendedRuntimeOptions extendedOptions) {
        super(extendedOptions);
    }
    private static final int TIMEOUT_MULTIPLIER = 3;

    @Override
    public int[][] getStatuses(CucumberFeatureResult[] results) {
        return null;
    }
    protected String getReportBase() throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/breakdown-report-tmpl.html");
        String result = IOUtils.toString(is);
        return result;
    }
    public void executeReport(BreakdownReportInfo info, BreakdownTable table, String[] formats) throws Exception {
        CucumberFeatureResult[] features = readFileContent(true);
        File outFile = new File(
                this.getOutputDirectory() + File.separator + this.getOutputName()
                + "-" + info.getReportSuffix() + ".html");
        FileUtils.writeStringToFile(outFile, generateBreakdownReport(features, info, table));
        this.export(outFile, info.getReportSuffix(), formats, this.isImageExportable());
    }
    public void executeReport(BreakdownTable table, String[] formats) throws Exception {
        executeReport(new BreakdownReportInfo(table), table, formats);
    }
    protected void generateFrameFile(BreakdownReportModel model) throws Exception {
        InputStream is = this.getClass().getResourceAsStream("/breakdown-frame.html");
        String content = IOUtils.toString(is);
        File outFile = new File(
                this.getOutputDirectory() + File.separator + this.getOutputName()
                + "-frame.html");
        content = content.replaceAll("__THIS__", outFile.getName());
        for (BreakdownReportInfo item : model.getReportsInfo()) {
            if (item.getRefreshTimeout() > 0) {
                content = content.replaceAll("__FIRST__",
                        "./" + this.getOutputName() + "-" + item.getReportSuffix() + ".html");
                break;
            }
        }
        int totalTimeout = 0;
        for (BreakdownReportInfo item : model.getReportsInfo()) {
            if (item.getRefreshTimeout() > 0) {
                totalTimeout += item.getRefreshTimeout();
            }
        }
        totalTimeout *= TIMEOUT_MULTIPLIER;
        content = content.replaceAll("__TIMEOUT__", "" + totalTimeout);
        FileUtils.writeStringToFile(outFile, content);
    }

    @Override
    public void execute(BreakdownReportModel batch, String[] formats) throws Exception {
        boolean frameGenerated = false;
        validateParameters();
        batch.initRedirectSequence("./" + this.getOutputName() + "-");
        for (BreakdownReportInfo info : batch.getReportsInfo()) {
            if (info.getRefreshTimeout() > 0 && !frameGenerated) {
                frameGenerated = true;
                generateFrameFile(batch);
            }
            this.executeReport(info, info.getTable(), formats);
        }
    }

    @Override
    public void execute(boolean aggregate, String[] formats) throws Exception {
    }

    protected String generateBreakdownReport(CucumberFeatureResult[] features,
            BreakdownReportInfo info, BreakdownTable table) throws Exception {
        String content = getReportBase();
        content = content.replaceAll("__TITLE__", info.getTitle());
        if (info.getRefreshTimeout() > 0 && StringUtils.isNotBlank(info.getNextFile())) {
            String refreshHeader
                = String.format(Locale.US, "<meta http-equiv=\"Refresh\" content=\"%d; url=%s\" />",
                        info.getRefreshTimeout(), info.getNextFile());
            content = content.replaceAll("__REFRESH__", refreshHeader);
        } else {
            content = content.replaceAll("__REFRESH__", "");
        }
        String tableContent = generateBreakdownTable(features, table);
        tableContent = StringConversionUtils.replaceHtmlEntitiesWithCodes(tableContent);
        tableContent = tableContent.replaceAll("[$]", "&#36;");
        content = content.replaceAll("__REPORT__", tableContent);
        content = StringConversionUtils.replaceHtmlEntitiesWithCodes(content);
        content = content.replaceAll("[$]", "&#36;");
        return content;
    }
    protected String generateBreakdownTable(CucumberFeatureResult[] features,
            BreakdownTable table) throws Exception {
        String content = String.format(Locale.US,
                "<table class=\"hoverTable\"><thead>%s</thead><tbody>%s</tbody></table>",
                generateHeader(table), generateBody(table, features));
        content = StringConversionUtils.replaceHtmlEntitiesWithCodes(content);
        return content;
    }
    protected String generateHeader(BreakdownTable table) {
        int colOffset = table.getRows().depth();
        int rowOffset = table.getCols().depth();
        String content = String.format(Locale.US,
                "<tr><th colspan=\"%d\" rowspan=\"%d\">&nbsp;</th>", colOffset, rowOffset);
        for (int i = 0; i < rowOffset; i++) {
            DataDimension[] line = table.getCols().getRow(i);
            for (DataDimension item : line) {
                if (item.depth() == 1) {
                    content = content.concat(
                            String.format(Locale.US,
                                    "<th colspan=\"%d\" rowspan=\"%d\">%s</th>",
                                    item.width(), rowOffset - item.depth() - i + 1, item.getAlias()));
                } else {
                    content = content.concat(
                            String.format(Locale.US,
                                    "<th colspan=\"%d\" rowspan=\"%d\">%s</th>",
                                    item.width(), 1, item.getAlias()));
                }
            }
            content = content.concat("</tr><tr>");
        }
        content = content.concat("</tr>");
        return content;
    }
    protected String generateRowHeading(DataDimension data, int maxDepth, int level) {
        int cellDepth = 1;
        String aliasText = data.getAlias();
        if (data.depth() == 1) {
            cellDepth = maxDepth - level + 1;
        }
        String content = String.format(Locale.US,
                "<th colspan=\"%d\" rowspan=\"%d\">%s</th>",
                cellDepth,
                data.width(),
                aliasText);
        if (data.hasSubElements()) {
            for (DataDimension item : data.getSubElements()) {
                content = content.concat(generateRowHeading(item, maxDepth, level + 1));
            }
        } else {
            content = content.concat("</tr><tr>");
        }
        return content;
    }
    protected String generateRowHeading(BreakdownTable table) {
        DataDimension rows = table.getRows();
        String content = "<tr>" + generateRowHeading(rows, rows.depth(), 1) + "</tr>";
        return content;
    }
    protected String generateBody(BreakdownTable table, CucumberFeatureResult[] features) throws Exception {
        CucumberScenarioResult[] scenarios = new CucumberScenarioResult[] {};
        for (CucumberFeatureResult feature : features) {
            scenarios = ArrayUtils.addAll(scenarios, feature.getElements());
        }
        BreakdownStats[][] results = table.valuate(scenarios);
        String rowHeadings = generateRowHeading(table);
        String[] headingRows = rowHeadings.split("</tr>");
        Assert.assertEquals(headingRows.length - 1, results.length);
        String content = "";
        for (int i = 0; i < results.length; i++) {
            String row = headingRows[i];
            for (int j = 0; j < results[i].length; j++) {
                row = row.concat(drawCell(results[i][j], table.getDisplayType()));
            }
            row = row.concat("</tr>");
            content = content.concat(row);
        }
        return content;
    }
    private String drawCell(BreakdownStats stats, BreakdownCellDisplayType type) throws Exception {
        Map<BreakdownCellDisplayType, Class<?>> drawCellMap = new HashMap<BreakdownCellDisplayType, Class<?>>() {
            {
                put(BreakdownCellDisplayType.BARS_ONLY, BarCellDrawer.class);
                put(BreakdownCellDisplayType.BARS_WITH_NUMBERS, BarNumberCellDrawer.class);
                put(BreakdownCellDisplayType.NUMBERS_ONLY, NumberOnlyCellDrawer.class);
                put(BreakdownCellDisplayType.PIE_CHART, PieChartCellDrawer.class);
            }
        };
        double total = stats.getFailed() + stats.getPassed() + stats.getSkipped();
        if (total <= 0) {
            return String.format(Locale.US,
                    "<td bgcolor=\"silver\"><center><b>N/A</b></center></td>");
        }
        CellDrawer drawer = (CellDrawer) (drawCellMap.get(type)
                .getConstructor(this.getClass()).newInstance(this));
        return drawer.drawCell(stats);
    }
    private interface CellDrawer {
        String drawCell(BreakdownStats stats) throws Exception;
    }
    private class BarCellDrawer implements CellDrawer {
        public BarCellDrawer() {
            super();
        }
        private String drawCellValues(int passed, int failed, int skipped) {
            String output = "";
            if (passed > 0) {
                output = output.concat(String.format(Locale.US, "Passed: %d ", passed));
            }
            if (failed > 0) {
                output = output.concat(String.format(Locale.US, "Failed: %d ", failed));
            }
            if (skipped > 0) {
                output = output.concat(String.format(Locale.US, "Skipped: %d ", skipped));
            }
            return output;
        }
        @Override
        public String drawCell(BreakdownStats stats) {
            final int cellSize = 30;
            double total = stats.getFailed() + stats.getPassed() + stats.getSkipped();
            if (total > 0) {
                int passedRatio = (int) (cellSize * ((double) stats.getPassed() / total));
                int failedRatio = (int) (cellSize * ((double) stats.getFailed() / total));
                int skippedRatio = (int) (cellSize * ((double) stats.getSkipped() / total));
                if (stats.getFailed() > 0) {
                    failedRatio++;
                }
                return String.format(Locale.US, "<td>"
                        + "<table width=\"100%%\"><tr>"
                            + "<td><a title=\"%s\">"
                            + "<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" width=\"100%%\" height=\"30\">"
                            + "<rect y=\"%d\" width=\"100%%\" height=\"%d\""
                                + " stroke=\"black\" stroke-width=\"1\" fill=\"green\"></rect>"
                            + "<rect y=\"%d\" width=\"100%%\" height=\"%d\""
                                + " stroke=\"red\" stroke-width=\"1\" fill=\"red\"></rect>"
                            + "<rect y=\"%d\" width=\"100%%\" height=\"%d\""
                                + " stroke=\"silver\" stroke-width=\"1\" fill=\"silver\"></rect>"
                            + "</svg></a>"
                            + "</td></tr>"
                            //+ "<tr><td colspan=3><center>%s</center></td></tr>"
                            + "</table></td>",
                        drawCellValues(stats.getPassed(), stats.getFailed(), stats.getSkipped()),
                        0, passedRatio,
                        passedRatio, failedRatio,
                        failedRatio + passedRatio, skippedRatio//,
                        //drawCellValues(stats.getPassed(), stats.getFailed(), stats.getSkipped())
                 );
            }
            return String.format(Locale.US, "<td bgcolor=\"silver\"><center><b>N/A</b></center></td>");
        }
    }
    private class BarNumberCellDrawer implements CellDrawer {
        public BarNumberCellDrawer() {
            super();
        }

        @Override
        public String drawCell(BreakdownStats stats) {
            BarCellDrawer barDrawer = new BarCellDrawer();
            String barHtml = barDrawer.drawCell(stats);
            NumberOnlyCellDrawer numberDrawer = new NumberOnlyCellDrawer();
            String numberHtml = numberDrawer.drawCell(stats);
            barHtml = barHtml.replaceAll("</tr></table>", "</tr>" + numberHtml + "</table>");
            return barHtml;
        }
    }
    private class NumberOnlyCellDrawer implements CellDrawer {
        public NumberOnlyCellDrawer() {
            super();
        }

        @Override
        public String drawCell(BreakdownStats stats) {
            String output = "<td><center><b>";
            if (stats.getPassed() > 0) {
                output = output.concat(String.format(Locale.US,
                        "<span class=\"passed\">%d</span> ", stats.getPassed()));
            }
            if (stats.getFailed() > 0) {
                output = output.concat(String.format(Locale.US,
                        "<span class=\"failed\">%d</span> ", stats.getFailed()));
            }
            if (stats.getSkipped() > 0) {
                output = output.concat(String.format(Locale.US,
                        "<span class=\"skipped\">%d</span> ", stats.getSkipped()));
            }
            return output + "</b></center></td>";
        }
    }
    private class PieChartCellDrawer implements CellDrawer {
        public PieChartCellDrawer() {
            super();
        }

        @Override
        public String drawCell(BreakdownStats stats) throws Exception {
            String chartHtml = "";
            double total = stats.getFailed() + stats.getPassed() + stats.getSkipped();
            if (total > 0) {
                PieChartDrawer pieChart = new PieChartDrawer();
                chartHtml = "<td>" + pieChart.generatePieChart(CHART_WIDTH, CHART_HEIGHT,
                    new int[] {stats.getPassed(), stats.getFailed(), stats.getSkipped()},
                    new String[] {"Passed", "Failed", "Skipped"},
                    new String[] {"green", "red", "silver"},
                    new String[] {"darkgreen", "darkred", "darkgray"},
                    CHART_THICKNESS, 2) + "</td>";
                return chartHtml;
            }
            return String.format(Locale.US,
                    "<td bgcolor=\"silver\"><center><b>N/A</b></center></td>");
        }
    }
    @Override
    public CucumberReportTypes getReportType() {
        return CucumberReportTypes.BREAKDOWN_REPORT;
    }
    @Override
    public void validateParameters() {
        Assert.assertNotNull(
            this.constructErrorMessage(CucumberReportError.NO_SOURCE_FILE, ""),
            this.getSourceFiles());
        Assert.assertNotNull(
                this.constructErrorMessage(CucumberReportError.NO_OUTPUT_DIRECTORY, ""),
                this.getOutputDirectory());
        Assert.assertNotNull(
                this.constructErrorMessage(CucumberReportError.NO_OUTPUT_NAME, ""),
                this.getOutputName());
        for (String sourceFile : this.getSourceFiles()) {
            File path = new File(sourceFile);
            Assert.assertTrue(
                    this.constructErrorMessage(CucumberReportError.NON_EXISTING_SOURCE_FILE, "")
                    + ". Was looking for path: \"" + path.getAbsolutePath() + "\"",
                    path.exists());
        }
    }
    @Override
    public CucumberReportLink getReportDocLink() {
        return CucumberReportLink.BREAKDOWN_URL;
    }
    @Override
    public void execute(BreakdownReportModel batch, boolean aggregate,
            String[] formats) throws Exception {
        execute(batch, formats);
    }
    @Override
    public void execute(File config, boolean aggregate, String[] formats)
            throws Exception {
        execute(config, formats);
    }
    @Override
    public void execute(String[] formats) throws Exception {
    }
}
