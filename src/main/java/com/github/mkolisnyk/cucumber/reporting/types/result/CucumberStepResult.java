package com.github.mkolisnyk.cucumber.reporting.types.result;

import com.cedarsoftware.util.io.JsonObject;

public class CucumberStepResult {

    private CucumberResult result;
    private String         name;
    private String         keyword;
    private Long           line;

    @SuppressWarnings("unchecked")
    public CucumberStepResult(JsonObject<String, Object> json) {
        this.name = (String) json.get("name");
        this.keyword = (String) json.get("keyword");
        this.line = (Long) json.get("line");
        this.result = new CucumberResult(
                (JsonObject<String, Object>) json.get("result"));
    }

    /**
     * @return the result
     */
    public final CucumberResult getResult() {
        return result;
    }

    /**
     * @param resultValue
     *            the result to set
     */
    public final void setResult(CucumberResult resultValue) {
        this.result = resultValue;
    }

    /**
     * @return the name
     */
    public final String getName() {
        return name;
    }

    /**
     * @param nameValue
     *            the name to set
     */
    public final void setName(String nameValue) {
        this.name = nameValue;
    }

    /**
     * @return the keyword
     */
    public final String getKeyword() {
        return keyword;
    }

    /**
     * @param keywordValue
     *            the keyword to set
     */
    public final void setKeyword(String keywordValue) {
        this.keyword = keywordValue;
    }

    /**
     * @return the line
     */
    public final Long getLine() {
        return line;
    }

    /**
     * @param lineValue
     *            the line to set
     */
    public final void setLine(Long lineValue) {
        this.line = lineValue;
    }
}
