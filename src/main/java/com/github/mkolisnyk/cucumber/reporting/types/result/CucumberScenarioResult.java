package com.github.mkolisnyk.cucumber.reporting.types.result;

import com.cedarsoftware.util.io.JsonObject;

public class CucumberScenarioResult {

    private String               id;
    private CucumberTagResults[]     tags;
    private String               description;
    private String               name;
    private String               keyword;
    private Long                 line;
    private CucumberStepResult[] steps;
    private String               type;

    private int                  passed    = 0;
    private int                  failed    = 0;
    private int                  undefined = 0;

    @SuppressWarnings("unchecked")
    public CucumberScenarioResult(JsonObject<String, Object> json) {
        this.id = (String) json.get("id");
        this.description = (String) json.get("description");
        this.name = (String) json.get("name");
        this.keyword = (String) json.get("keyword");
        this.line = (Long) json.get("line");
        this.type = (String) json.get("type");
        if (json.containsKey("steps")) {
            Object[] objs = (Object[]) ((JsonObject<String, Object>) json
                    .get("steps")).get("@items");
            this.steps = new CucumberStepResult[objs.length];
            for (int i = 0; i < objs.length; i++) {
                this.steps[i] = new CucumberStepResult(
                        (JsonObject<String, Object>) objs[i]);
            }
        }
        JsonObject<String, Object> tagEntry = (JsonObject<String, Object>) json
                .get("tags");
        Object[] objs = {};
        if (tagEntry != null) {
            objs = (Object[]) ((JsonObject<String, Object>) json
                    .get("tags")).get("@items");
        }
        this.tags = new CucumberTagResults[objs.length];
        for (int i = 0; i < objs.length; i++) {
            this.tags[i] = new CucumberTagResults(
                    (JsonObject<String, Object>) objs[i]);
        }
    }

    public void valuate() {
        passed = 0;
        failed = 0;
        undefined = 0;
        if (steps == null) {
            return;
        }
        for (CucumberStepResult step : steps) {
            String status = step.getResult().getStatus();
            if (status.equalsIgnoreCase("passed")) {
                this.passed++;
            } else if (status.equalsIgnoreCase("failed")) {
                this.failed++;
            } else {
                this.undefined++;
            }
        }
    }

    /**
     * @return the passed
     */
    public final int getPassed() {
        return passed;
    }

    /**
     * @return the failed
     */
    public final int getFailed() {
        return failed;
    }

    /**
     * @return the undefined
     */
    public final int getUndefined() {
        return undefined;
    }

    public String getStatus() {
        valuate();
        if (this.getFailed() > 0) {
            return "failed";
        } else if (this.getUndefined() > 0) {
            return "undefined";
        } else if (this.getPassed() > 0) {
            return "passed";
        } else {
            return "undefined";
        }
    }

    /**
     * @return the id
     */
    public final String getId() {
        return id;
    }

    /**
     * @return the description
     */
    public final String getDescription() {
        return description;
    }

    /**
     * @return the name
     */
    public final String getName() {
        return name;
    }

    /**
     * @return the keyword
     */
    public final String getKeyword() {
        return keyword;
    }

    /**
     * @return the line
     */
    public final Long getLine() {
        return line;
    }

    /**
     * @return the steps
     */
    public final CucumberStepResult[] getSteps() {
        return steps;
    }

    /**
     * @return the type
     */
    public final String getType() {
        return type;
    }

    /**
     * @param idValue the id to set
     */
    public final void setId(String idValue) {
        this.id = idValue;
    }

    /**
     * @param descriptionValue the description to set
     */
    public final void setDescription(String descriptionValue) {
        this.description = descriptionValue;
    }

    /**
     * @param nameValue the name to set
     */
    public final void setName(String nameValue) {
        this.name = nameValue;
    }

    /**
     * @param keywordValue the keyword to set
     */
    public final void setKeyword(String keywordValue) {
        this.keyword = keywordValue;
    }

    /**
     * @param lineValue the line to set
     */
    public final void setLine(Long lineValue) {
        this.line = lineValue;
    }

    /**
     * @param stepsValue the steps to set
     */
    public final void setSteps(CucumberStepResult[] stepsValue) {
        this.steps = stepsValue;
    }

    /**
     * @param typeValue the type to set
     */
    public final void setType(String typeValue) {
        this.type = typeValue;
    }

    /**
     * @return the tags
     */
    public final CucumberTagResults[] getTags() {
        return tags;
    }

    /**
     * @param tagsValue the tags to set
     */
    public final void setTags(CucumberTagResults[] tagsValue) {
        this.tags = tagsValue;
    }
}
