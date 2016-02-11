package com.github.mkolisnyk.cucumber.reporting.types.retrospective;

public class RetrospectiveModel {
    private static final int DEFAULT_WIDTH = 450;
    private static final int DEFAULT_HEIGHT = 300;
    private String reportSuffix;
    private String title;
    private String mask;
    private RetrospectiveOrderBy orderBy;
    private String redirectTo;
    private int refreshTimeout;
    private int width;
    private int height;
    public RetrospectiveModel(String reportSuffixValue, String titleValue, String maskValue,
            RetrospectiveOrderBy orderByValue, String redirectToValue, int refreshTimeoutValue,
            int widthValue, int heightValue) {
        super();
        this.reportSuffix = reportSuffixValue;
        this.title = titleValue;
        this.mask = maskValue;
        this.orderBy = orderByValue;
        this.redirectTo = redirectToValue;
        this.refreshTimeout = refreshTimeoutValue;
        this.height = heightValue;
        this.width = widthValue;
    }
    public RetrospectiveModel(String reportSuffixValue, String titleValue, String maskValue,
            RetrospectiveOrderBy orderByValue, String redirectToValue, int refreshTimeoutValue) {
        this(reportSuffixValue, titleValue, maskValue, orderByValue, redirectToValue, refreshTimeoutValue,
                DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
    public RetrospectiveModel(String reportSuffixValue, String titleValue, String maskValue,
            RetrospectiveOrderBy orderByValue, int widthValue, int heightValue) {
        this(reportSuffixValue, titleValue, maskValue, orderByValue, "", 0, widthValue, heightValue);
    }
    public RetrospectiveModel(String reportSuffixValue, String titleValue, String maskValue,
            int widthValue, int heightValue) {
        this(reportSuffixValue, titleValue, maskValue,
                RetrospectiveOrderBy.DATE_CREATED, "", 0, widthValue, heightValue);
    }
    public RetrospectiveModel(String reportSuffixValue, String titleValue, String maskValue,
            RetrospectiveOrderBy orderByValue) {
        this(reportSuffixValue, titleValue, maskValue, orderByValue, "", 0);
    }
    public RetrospectiveModel(String reportSuffixValue, String titleValue, String maskValue) {
        this(reportSuffixValue, titleValue, maskValue, RetrospectiveOrderBy.DATE_CREATED);
    }
    public RetrospectiveModel(String reportSuffixValue, String titleValue) {
        this(reportSuffixValue, titleValue, "(.*)");
    }
    public RetrospectiveModel(String reportSuffixValue) {
        this(reportSuffixValue, "Retrospective Results");
    }
    public String getTitle() {
        return title;
    }
    public String getMask() {
        return mask;
    }
    public RetrospectiveOrderBy getOrderBy() {
        return orderBy;
    }
    public String getReportSuffix() {
        return reportSuffix;
    }
    public String getRedirectTo() {
        return redirectTo;
    }
    public int getRefreshTimeout() {
        return refreshTimeout;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}
