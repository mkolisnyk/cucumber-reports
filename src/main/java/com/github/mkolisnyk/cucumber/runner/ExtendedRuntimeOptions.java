package com.github.mkolisnyk.cucumber.runner;

public class ExtendedRuntimeOptions {

	private boolean isOverviewReport = false;
	private boolean isUsageReport = false;
	private boolean isDetailedReport = false;
	private boolean isDetailedAggregatedReport = false;
	private String jsonReportPath;
	private String outputFolder;
	private String reportPrefix;
	private int retryCount = 0;
	private String screenShotSize = "";
	private boolean toPDF = false;
	private String jsonUsageReportPath;
	private String screenShotLocation = "";
	
	public ExtendedRuntimeOptions(Class<?> clazz) {
		ExtendedCucumberOptions options = clazz.getAnnotation(ExtendedCucumberOptions.class);
		if (options != null) {
			this.isOverviewReport = options.overviewReport();
			this.isUsageReport = options.usageReport();
			this.isDetailedReport = options.detailedReport();
			this.isDetailedAggregatedReport = options.detailedAggregatedReport();
			this.jsonReportPath = options.jsonReport();
			this.outputFolder = options.outputFolder();
			this.reportPrefix = options.reportPrefix();
			this.retryCount = options.retryCount();
			this.screenShotSize = options.screenShotSize();
			this.toPDF = options.toPDF();
			this.jsonUsageReportPath = options.jsonUsageReport();
			this.screenShotLocation = options.screenShotLocation();
		}
	}

	public final boolean isOverviewReport() {
		return isOverviewReport;
	}

	public final boolean isUsageReport() {
		return isUsageReport;
	}

	public final boolean isDetailedReport() {
		return isDetailedReport;
	}

	public final boolean isDetailedAggregatedReport() {
		return isDetailedAggregatedReport;
	}

	public final String getJsonReportPath() {
		return jsonReportPath;
	}

	public final String getOutputFolder() {
		return outputFolder;
	}

	public final String getReportPrefix() {
		return reportPrefix;
	}

	public final int getRetryCount() {
		return retryCount;
	}

	public final String getScreenShotSize() {
		return screenShotSize;
	}

	public final boolean isToPDF() {
		return toPDF;
	}

	public final String getJsonUsageReportPath() {
		return jsonUsageReportPath;
	}

	public final String getScreenShotLocation() {
		return screenShotLocation;
	}
}
