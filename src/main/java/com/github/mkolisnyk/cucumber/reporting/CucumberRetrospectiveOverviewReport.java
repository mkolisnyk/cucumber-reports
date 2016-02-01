package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;

import com.github.mkolisnyk.cucumber.reporting.types.breakdown.BreakdownStats;
import com.github.mkolisnyk.cucumber.reporting.types.consolidated.ConsolidatedItemInfo;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberFeatureResult;
import com.github.mkolisnyk.cucumber.reporting.types.retrospective.RetrospectiveBatch;
import com.github.mkolisnyk.cucumber.reporting.types.retrospective.RetrospectiveModel;

public class CucumberRetrospectiveOverviewReport extends CucumberResultsCommon {

    @Override
    public int[][] getStatuses(CucumberFeatureResult[] results) {
        // TODO Auto-generated method stub
        return null;
    }
    protected String getReportBase() throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/consolidated-tmpl.html");
        String result = IOUtils.toString(is);
        return result;
    }
    private String[] getFileNames(String rootFolder) throws Exception {
        String[] fileNames = {};
        for (File file : (new File(rootFolder)).listFiles()) {
            if (file.isDirectory()) {
                fileNames = (String[]) ArrayUtils.addAll(fileNames, getFileNames(file.getAbsolutePath()));
            } else {
                fileNames = (String[]) ArrayUtils.add(fileNames, file.getAbsolutePath());
            }
        }
        return fileNames;
    }
    private String[] getFilesByMask(String mask) throws Exception {
        String[] result = {};
        String[] input = getFileNames(".");
        for (String fileName : input) {
            if (fileName.matches(mask)) {
                result = (String[]) ArrayUtils.add(result, fileName);
            }
        }
        return result;
    }
    private BreakdownStats[] calculateStats(String[] files) throws Exception {
        BreakdownStats[] result = {};
        for (String file : files) {
            CucumberFeatureResult[] features = this.readFileContent(file, true);
            for (CucumberFeatureResult feature : features) {
                feature.valuate();
                BreakdownStats stat = new BreakdownStats();
                stat.addPassed(feature.getPassed());
                stat.addFailed(feature.getFailed());
                stat.addSkipped(feature.getSkipped() + feature.getUndefined());
                result = (BreakdownStats[]) ArrayUtils.add(result, stat);
            }
        }
        return result;
    }
    private String drawBarChart(RetrospectiveModel model, BreakdownStats stats, int offset, int barSize) {
        double total = stats.getFailed() + stats.getPassed() + stats.getSkipped();
        if (total > 0) {
            int passedRatio = (int) (model.getHeight() * ((double) stats.getPassed() / total));
            int failedRatio = (int) (model.getHeight() * ((double) stats.getFailed() / total));
            int skippedRatio = (int) (model.getHeight() * ((double) stats.getSkipped() / total));
            return String.format("<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\""
                            + " stroke=\"black\" stroke-width=\"1\" fill=\"green\"></rect>"
                        + "<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\""
                            + " stroke=\"red\" stroke-width=\"1\" fill=\"red\"></rect>"
                        + "<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\""
                            + " stroke=\"silver\" stroke-width=\"1\" fill=\"silver\"></rect>",
                    offset, 0, barSize, passedRatio,
                    offset, passedRatio, barSize , failedRatio,
                    offset, failedRatio + passedRatio, barSize, skippedRatio
             );
        }
        return "";
    }
    private String drawGraph(RetrospectiveModel model, BreakdownStats[] stats) {
        String content = String.format("<svg xmlns=\"http://www.w3.org/2000/svg\""
                + " version=\"1.1\" width=\"%d\" height=\"%d\">", model.getWidth(), model.getHeight());
        int offset = 0;
        final int barSize = model.getWidth() / stats.length;
        for (BreakdownStats stat : stats) {
            content = content.concat(this.drawBarChart(model, stat, offset * barSize, barSize));
        }
        content = content + "</svg>";
        return content;
    }
    private String generateRetrospectiveReport(RetrospectiveModel model, BreakdownStats[] stats) throws Exception {
        String result = getReportBase();
        result = result.replaceAll("__TITLE__", model.getTitle());
        String reportContent = "<h1>" + model.getTitle() + "</h1>" + drawGraph(model, stats);
        reportContent = this.replaceHtmlEntitiesWithCodes(reportContent);
        reportContent = reportContent.replaceAll("[$]", "&#36;");
        result = result.replaceAll("__REPORT__", reportContent);
        return result;
    }
    public void executeReport(RetrospectiveModel model, boolean aggregate, boolean toPDF) throws Exception {
        String[] files = getFilesByMask(model.getMask());
        BreakdownStats[] stats = calculateStats(files);

        File outFile = new File(
                this.getOutputDirectory() + File.separator + this.getOutputName()
                + "-" + model.getReportSuffix() + ".html");
        FileUtils.writeStringToFile(outFile, generateRetrospectiveReport(model, stats));
        if (toPDF) {
            this.exportToPDF(outFile, model.getReportSuffix());
        }
        return;
    }
    public void executeReport(RetrospectiveBatch batch, boolean aggregate, boolean toPDF) throws Exception {
        for (RetrospectiveModel model : batch.getModels()) {
            this.executeReport(model, aggregate, toPDF);
        }
        return;
    }
}
