package com.github.mkolisnyk.cucumber.runner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ExtendedCucumberOptionList.class)
@Target({ ElementType.TYPE })
public @interface ExtendedCucumberOptions {
    int retryCount() default 0;

    String jsonReport() default "";
    String[] jsonReports() default { };

    String jsonUsageReport() default "";
    String[] jsonUsageReports() default { };

    String outputFolder() default ".";

    String reportPrefix() default "cucumber-results";

    boolean usageReport() default false;

    boolean overviewReport() default false;

    boolean overviewChartsReport() default false;

    boolean coverageReport() default false;

    boolean detailedReport() default false;

    boolean detailedAggregatedReport() default false;

    String screenShotSize() default "";

    boolean toPDF() default false;
    String pdfPageSize() default "auto";

    String screenShotLocation() default "";

    String[] includeCoverageTags() default { };
    String[] excludeCoverageTags() default { };

    boolean breakdownReport() default false;
    String breakdownConfig() default "";

    boolean featureMapReport() default false;
    String featureMapConfig() default "";

    boolean featureOverviewChart() default false;

    boolean knownErrorsReport() default false;
    String knownErrorsConfig() default "";

    boolean consolidatedReport() default false;
    String consolidatedReportConfig() default "";

    int threadsCount() default 1;
    String threadsCountValue() default "";

    boolean systemInfoReport() default false;
    boolean benchmarkReport() default false;
    String benchmarkReportConfig() default "";

    boolean customReport() default false;
    String[] customReportTemplateNames() default {};

    String customTemplatesPath() default "";

    String[] formats() default {};
}
