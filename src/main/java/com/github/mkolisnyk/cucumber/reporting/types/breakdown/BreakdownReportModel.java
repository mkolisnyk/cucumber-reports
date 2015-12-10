package com.github.mkolisnyk.cucumber.reporting.types.breakdown;

public class BreakdownReportModel {
    private BreakdownReportInfo[] reportsInfo = {};
    public BreakdownReportModel() {
    }
    public BreakdownReportModel(
            BreakdownReportInfo[] reportsInfoValue) {
        super();
        this.reportsInfo = reportsInfoValue;
    }
    public BreakdownReportInfo[] getReportsInfo() {
        return reportsInfo;
    }
    public void initRedirectSequence(String fileBaseName) {
        int next = 0;
        int firstRedirected = -1;
        for (int i = 0; i < reportsInfo.length; i++) {
            next = i + 1;
            while (next <= reportsInfo.length) {
                if (next < reportsInfo.length) {
                    if (reportsInfo[next].getRefreshTimeout() > 0) {
                        if (firstRedirected < 0) {
                            firstRedirected = i;
                        }
                        reportsInfo[i].setNextFile(fileBaseName + reportsInfo[next].getReportSuffix() + ".html");
                        break;
                    }
                } else {
                    if (firstRedirected >= 0 && reportsInfo[firstRedirected].getRefreshTimeout() > 0) {
                        reportsInfo[i].setNextFile(fileBaseName
                                + reportsInfo[firstRedirected].getReportSuffix() + ".html");
                        break;
                    }
                }
                next++;
            }
        }
    }
}
