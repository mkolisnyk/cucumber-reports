package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.testng.Assert;

import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownStats;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownTable;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.DataDimension;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberScenarioResult;

public class CucumberBreakdownReport extends CucumberResultsCommon {
    private static final int RED = 0xFF0000;
    private static final int GREEN = 0x00FF00;
    private static final int GRAY = 0xBBBBBB;

    @Override
    public int[][] getStatuses(CucumberFeatureResult[] results) {
        return null;
    }
    protected String getReportBase() throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/breakdown-report-tmpl.html");
        String result = IOUtils.toString(is);
        return result;
    }
    public void executeReport(String reportSuffix, BreakdownTable table) throws Exception {
        CucumberFeatureResult[] features = readFileContent(true);
        File outFile = new File(
                this.getOutputDirectory() + File.separator + this.getOutputName()
                + "-" + reportSuffix + ".html");
        FileUtils.writeStringToFile(outFile, generateBreakdownReport(features, table));
    }
    public void executeReport(BreakdownTable table) throws Exception {
        executeReport("breakdown", table);
    }
    private String generateBreakdownReport(CucumberFeatureResult[] features,
            BreakdownTable table) throws IOException {
        String content = getReportBase();
        content = content.replaceAll("__TITLE__", "Breakdown Report");
        content = content.replaceAll("__REPORT__", generateBreakdownTable(features, table));
        return content;
    }
    private String generateBreakdownTable(CucumberFeatureResult[] features,
            BreakdownTable table) {
        String content = String.format("<table>%s%s</table>", generateHeader(table), generateBody(table, features));
        return content;
    }
    private String generateHeader(BreakdownTable table) {
        int colOffset = table.getRows().depth();
        int rowOffset = table.getCols().depth();
        String content = String.format("<tr><th colspan=\"%d\" rowspan=\"%d\">&nbsp;</th>", colOffset, rowOffset);
        for (int i = 0; i < rowOffset; i++) {
            DataDimension[] line = table.getCols().getRow(i);
            for (DataDimension item : line) {
                if (item.depth() == 1) {
                    content = content.concat(
                            String.format("<th colspan=\"%d\" rowspan=\"%d\">%s</th>",
                                    item.width(), rowOffset - item.depth() - i + 1, item.getAlias()));
                } else {
                    content = content.concat(
                            String.format("<th colspan=\"%d\" rowspan=\"%d\">%s</th>",
                                    item.width(), 1, item.getAlias()));
                }
            }
            content = content.concat("</tr><tr>");
        }
        content = content.concat("</tr>");
        return content;
    }
    private String generateRowHeading(DataDimension data, int maxDepth) {
        String content = String.format("<th colspan=\"%d\" rowspan=\"%d\">%s</th>",
                maxDepth - data.depth() + 1,
                data.width(),
                data.getAlias());
        if (data.hasSubElements()) {
            for (DataDimension item : data.getSubElements()) {
                content = content.concat(generateRowHeading(item, maxDepth - 1));
            }
        } else {
            content = content.concat("</tr><tr>");
        }
        return content;
    }
    private String generateRowHeading(BreakdownTable table) {
        DataDimension rows = table.getRows();
        String content = "<tr>" + generateRowHeading(rows, rows.depth()) + "</tr>";
        /*DataDimension[][] data = table.getRows().expand();
        for (int i = 0; i < data.length; i++) {
            content = content.concat("<tr>");
            for (int j = 0; j < data[i].length; j++) {
                content = content.concat(String.format("<th>%s</th>", data[i][j].getAlias()));
            }
            content = content.concat("</tr>");
        }*/
        return content;
    }
    private String generateBody(BreakdownTable table, CucumberFeatureResult[] features) {
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
                row = row.concat(drawCell(results[i][j]));
            }
            row = row.concat("</tr>");
            content = content.concat(row);
        }
        return content;
    }
    private String drawCellValues(int passed, int failed, int skipped) {
        String output = "";
        if (passed > 0) {
            output = output.concat(String.format("<font color=green>%d</font>&nbsp;", passed));
        }
        if (failed > 0) {
            output = output.concat(String.format("<font color=red>%d</font>&nbsp;", failed));
        }
        if (skipped > 0) {
            output = output.concat(String.format("<font color=silver>%d</font>&nbsp;", skipped));
        }
        return "<b>" + output + "</b>";
    }
    private String drawCell(BreakdownStats stats) {
        double total = stats.getFailed() + stats.getPassed() + stats.getSkipped();
        if (total > 0) {
            int passedRatio = (int) (50 * ((double) stats.getPassed() / total));
            int failedRatio = (int) (50 * ((double) stats.getFailed() / total));
            int skippedRatio = (int) (50 * ((double) stats.getSkipped() / total));
            if (stats.getFailed() > 0) {
                failedRatio++;
            }
            return String.format("<td>"
                    + "<table width=\"100%%\"><tr>"
                        + "<td>"
                        + "<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" width=\"100%%\" height=\"50\">"
                        + "<rect y=\"%d\" width=\"100%%\" height=\"%d\""
                            + " stroke=\"black\" stroke-width=\"1\" fill=\"green\"></rect>"
                        + "<rect y=\"%d\" width=\"100%%\" height=\"%d\""
                            + " stroke=\"red\" stroke-width=\"1\" fill=\"red\"></rect>"
                        + "<rect y=\"%d\" width=\"100%%\" height=\"%d\""
                            + " stroke=\"silver\" stroke-width=\"1\" fill=\"silver\"></rect>"
                        + "</svg>"
                        + "</td></tr>"
                        + "<tr><td colspan=3><center>%s</center></td></tr></table></td>",
                    0,passedRatio,
                    passedRatio,failedRatio,
                    failedRatio + passedRatio,skippedRatio,
                    drawCellValues(stats.getPassed(), stats.getFailed(), stats.getSkipped())
             );
        }
        return String.format("<td bgcolor=silver><center><b>N/A</b></center></td>");
    }
}
