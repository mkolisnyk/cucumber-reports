package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;

import com.cedarsoftware.util.io.JsonReader;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownReportInfo;
import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownReportModel;
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
    public void executeReport(BreakdownReportInfo info, BreakdownTable table) throws Exception {
        CucumberFeatureResult[] features = readFileContent(true);
        File outFile = new File(
                this.getOutputDirectory() + File.separator + this.getOutputName()
                + "-" + info.getReportSuffix() + ".html");
        FileUtils.writeStringToFile(outFile, generateBreakdownReport(features, info, table));
    }
    public void executeReport(BreakdownTable table) throws Exception {
        executeReport(new BreakdownReportInfo(table), table);
    }
    public void executeReport(BreakdownReportModel model) throws Exception {
        model.initRedirectSequence("./" + this.getOutputName() + "-");
        for (BreakdownReportInfo info : model.getReportsInfo()) {
            this.executeReport(info, info.getTable());
        }
    }
    public void executeReport(File config) throws Exception {
        BreakdownReportModel model = (BreakdownReportModel) JsonReader.jsonToJava(
                FileUtils.readFileToString(config));
        this.executeReport(model);
    }
    private String generateBreakdownReport(CucumberFeatureResult[] features,
            BreakdownReportInfo info, BreakdownTable table) throws IOException {
        String content = getReportBase();
        content = content.replaceAll("__TITLE__", info.getTitle());
        if (info.getRefreshTimeout() > 0 && StringUtils.isNotBlank(info.getNextFile())) {
            String refreshHeader
                = String.format("<meta http-equiv=\"Refresh\" content=\"%d; url=%s\">",
                        info.getRefreshTimeout(), info.getNextFile());
            content = content.replaceAll("__REFRESH__", refreshHeader);
        } else {
            content = content.replaceAll("__REFRESH__", "");
        }
        content = content.replaceAll("__REPORT__", generateBreakdownTable(features, table));
        return content;
    }
    private String generateBreakdownTable(CucumberFeatureResult[] features,
            BreakdownTable table) {
        String content = String.format("<table class=\"hoverTable\"><thead>%s</thead><tbody>%s</tbody></table>",
                generateHeader(table), generateBody(table, features));
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
    private String generateRowHeading(DataDimension data, int maxDepth, int level) {
        int cellDepth = 1;
        String aliasText = data.getAlias();
        if (data.depth() == 1) {
            cellDepth = maxDepth - level + 1;
        }
        String content = String.format("<th colspan=\"%d\" rowspan=\"%d\">%s</th>",
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
    private String generateRowHeading(BreakdownTable table) {
        DataDimension rows = table.getRows();
        String content = "<tr>" + generateRowHeading(rows, rows.depth(), 1) + "</tr>";
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
            //output = output.concat(String.format("<font color=green>%d</font>&nbsp;", passed));
            output = output.concat(String.format("Passed: %d ", passed));
        }
        if (failed > 0) {
            //output = output.concat(String.format("<font color=red>%d</font>&nbsp;", failed));
            output = output.concat(String.format("Failed: %d ", failed));
        }
        if (skipped > 0) {
            //output = output.concat(String.format("<font color=silver>%d</font>&nbsp;", skipped));
            output = output.concat(String.format("Skipped: %d ", skipped));
        }
        return output;
    }
    private String drawCell(BreakdownStats stats) {
        final int cellSize = 50;
        double total = stats.getFailed() + stats.getPassed() + stats.getSkipped();
        if (total > 0) {
            int passedRatio = (int) (cellSize * ((double) stats.getPassed() / total));
            int failedRatio = (int) (cellSize * ((double) stats.getFailed() / total));
            int skippedRatio = (int) (cellSize * ((double) stats.getSkipped() / total));
            if (stats.getFailed() > 0) {
                failedRatio++;
            }
            return String.format("<td>"
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
        return String.format("<td bgcolor=silver><center><b>N/A</b></center></td>");
    }
}
