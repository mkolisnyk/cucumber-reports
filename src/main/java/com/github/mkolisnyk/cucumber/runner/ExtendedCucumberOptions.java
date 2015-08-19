package com.github.mkolisnyk.cucumber.runner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface ExtendedCucumberOptions {
    int retryCount() default 0;

    String jsonReport();

    String jsonUsageReport() default "cucumber-usage.json";

    String outputFolder() default ".";

    String reportPrefix() default "cucumber-results";

    boolean usageReport() default false;

    boolean overviewReport() default false;

    boolean coverageReport() default false;

    boolean detailedReport() default false;

    boolean detailedAggregatedReport() default false;

    String screenShotSize() default "";

    boolean toPDF() default false;

    String screenShotLocation() default "";

    String[] includeCoverageTags() default { };
    String[] excludeCoverageTags() default { };
}
