package com.github.mkolisnyk.cucumber.reporting;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.github.mkolisnyk.cucumber.reporting.interfaces.ConfigurableReport;
import com.github.mkolisnyk.cucumber.reporting.types.CommonDataBean;
import com.github.mkolisnyk.cucumber.reporting.types.consolidated.ConsolidatedDataBean;
import com.github.mkolisnyk.cucumber.reporting.types.consolidated.ConsolidatedItemInfo;
import com.github.mkolisnyk.cucumber.reporting.types.consolidated.ConsolidatedReportBatch;
import com.github.mkolisnyk.cucumber.reporting.types.consolidated.ConsolidatedReportModel;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportLink;
import com.github.mkolisnyk.cucumber.reporting.types.enums.CucumberReportTypes;
import com.github.mkolisnyk.cucumber.reporting.utils.helpers.StringConversionUtils;
import com.github.mkolisnyk.cucumber.runner.runtime.ExtendedRuntimeOptions;

public class CucumberConsolidatedReport extends ConfigurableReport<ConsolidatedReportBatch> {
    public CucumberConsolidatedReport() {
        super();
    }
    public CucumberConsolidatedReport(ExtendedRuntimeOptions extendedOptions) {
        super(extendedOptions);
    }
    private String retrieveBody(String content) {
        return content.split("<body>")[1].split("</body>")[0];
    }
    private String amendHtmlHeaders(String content) {
        final int totalHeadingTypes = 6;
        for (int i = totalHeadingTypes; i > 0; i--) {
            content = content.replaceAll("<h" + i + ">", "<h" + (i + 1) + ">");
            content = content.replaceAll("</h" + i + ">", "</h" + (i + 1) + ">");
        }
        return content;
    }
    public void executeConsolidatedReport(ConsolidatedReportModel model, String[] formats) throws Exception {
        File outFile = new File(
                this.getOutputDirectory() + File.separator + this.getOutputName()
                + "-" + model.getReportSuffix() + ".html");
        ConsolidatedDataBean data = new ConsolidatedDataBean();
        data.setTitle(model.getTitle());
        data.setRefreshData("");
        data.setColumns(model.getCols());
        data.setUseTableOfContents(model.isUseTableOfContents());
        for (ConsolidatedItemInfo item : model.getItems()) {
            String content = FileUtils.readFileToString(new File(item.getPath()));
            content = this.amendHtmlHeaders(content);
            content = this.retrieveBody(content);
            content = StringConversionUtils.replaceHtmlEntitiesWithCodes(content);
            data.getContents().put(item.getTitle(), content);
        }
        generateReportFromTemplate(outFile, "consolidated", data );
        this.export(outFile, model.getReportSuffix(), formats, this.isImageExportable());
    }
    public void executeConsolidatedReport(ConsolidatedReportModel model) throws Exception {
        executeConsolidatedReport(model, new String[] {});
    }
    @Override
    public CucumberReportTypes getReportType() {
        return CucumberReportTypes.CONSOLIDATED_REPORT;
    }
    @Override
    public void validateParameters() {
    }
    @Override
    public CucumberReportLink getReportDocLink() {
        return CucumberReportLink.CONSOLIDATED_URL;
    }
    @Override
    public void execute(ConsolidatedReportBatch batch, String[] formats)
            throws Exception {
        for (ConsolidatedReportModel model : batch.getModels()) {
            executeConsolidatedReport(model, formats);
        }
    }

    @Override
    public void execute(boolean aggregate, String[] formats) throws Exception {
    }
    @Override
    public void execute(ConsolidatedReportBatch batch, boolean aggregate,
            String[] formats) throws Exception {
        execute(batch, formats);
    }
    @Override
    public void execute(File config, boolean aggregate, String[] formats)
            throws Exception {
        execute(config, formats);
    }
}
