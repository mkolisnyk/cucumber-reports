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
    private int nextAvailableRedirect(int currentIndex) {
        int result = -1;
        for (int i = currentIndex + 1; i < reportsInfo.length; i++) {
            if (reportsInfo[i].getRefreshTimeout() > 0) {
                result = i;
                break;
            }
        }
        return result;
    }
    public void initRedirectSequence(String fileBaseName) {
        int firstRedirect = nextAvailableRedirect(-1);
        if (firstRedirect < 0) {
            return;
        }
        int currentRedirect = firstRedirect;
        while (nextAvailableRedirect(currentRedirect) > 0) {
            int nextRedirect = nextAvailableRedirect(currentRedirect);
            reportsInfo[currentRedirect].setNextFile(fileBaseName
                + reportsInfo[nextRedirect].getReportSuffix() + ".html");
            currentRedirect = nextRedirect;
        }
        reportsInfo[currentRedirect].setNextFile(fileBaseName
                + reportsInfo[firstRedirect].getReportSuffix() + ".html");
    }
}
