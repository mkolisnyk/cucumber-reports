package com.github.mkolisnyk.cucumber.reporting.types.result;

import org.apache.commons.lang.ArrayUtils;

import com.cedarsoftware.util.io.JsonObject;

public class CucumberScenarioResult {

    private String id;
    private CucumberTagResults[] tags;
    private String description;
    private String name;
    private String keyword;
    private Long line;
    private CucumberStepResult[] steps;
    private String type;

    private CucumberBeforeAfterResult before;
    private CucumberBeforeAfterResult after;

    private int passed = 0;
    private int failed = 0;
    private int skipped = 0;
    private int undefined = 0;
    private double duration = 0.f;
    private int rerunAttempts = 0;

    private String[] includeCoverageTags = {};
    private String[] excludeCoverageTags = {};

    @SuppressWarnings("unchecked")
    public CucumberScenarioResult(JsonObject<String, Object> json) {
        this.id = (String) json.get("id");
        this.description = (String) json.get("description");
        this.name = (String) json.get("name");
        this.keyword = (String) json.get("keyword");
        this.line = (Long) json.get("line");
        this.type = (String) json.get("type");
        if (json.containsKey("before")) {
            Object[] objs = (Object[]) ((JsonObject<String, Object>) json
                    .get("before")).get("@items");
            for (int i = 0; i < objs.length; i++) {
                this.before = new CucumberBeforeAfterResult(
                        (JsonObject<String, Object>) objs[i]);
            }
        }
        if (json.containsKey("after")) {
            Object[] objs = (Object[]) ((JsonObject<String, Object>) json
                    .get("after")).get("@items");
            for (int i = 0; i < objs.length; i++) {
                this.after = new CucumberBeforeAfterResult(
                        (JsonObject<String, Object>) objs[i]);
            }
        }
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
            objs = (Object[]) ((JsonObject<String, Object>) json.get("tags"))
                    .get("@items");
        }
        this.tags = new CucumberTagResults[objs.length];
        for (int i = 0; i < objs.length; i++) {
            this.tags[i] = new CucumberTagResults(
                    (JsonObject<String, Object>) objs[i]);
        }
    }

    public void valuate() {
        final int nanosecondsInMillisecond = 1000000;
        final float millesecondsInSecond = 1000.f;
        passed = 0;
        failed = 0;
        skipped = 0;
        undefined = 0;
        this.duration = 0.f;
        if (steps == null) {
            return;
        }
        for (CucumberStepResult step : steps) {
            String status = step.getResult().getStatus();
            if (status.equalsIgnoreCase("passed")) {
                this.passed++;
            } else if (status.equalsIgnoreCase("failed")) {
                this.failed++;
            } else if (status.equalsIgnoreCase("skipped")) {
                this.skipped++;
            } else {
                this.undefined++;
            }
            this.duration += (float) (step.getResult().getDuration() / nanosecondsInMillisecond)
                    / millesecondsInSecond;
        }
        if (!this.isInTagSet(this.includeCoverageTags, this.excludeCoverageTags)) {
            undefined++;
        }
        if (this.getBefore() != null) {
            this.duration += (float) (this.getBefore().getResult()
                    .getDuration() / nanosecondsInMillisecond)
                    / millesecondsInSecond;
        }
        if (this.getAfter() != null) {
            this.duration += (float) (this.getAfter().getResult().getDuration() / nanosecondsInMillisecond)
                    / millesecondsInSecond;
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
    public final int getSkipped() {
        return skipped;
    }

    public String getStatus() {
        valuate();
        if (this.getFailed() > 0) {
            return "failed";
        } else if (this.getUndefined() > 0) {
            return "undefined";
        } else if (this.getPassed() > 0) {
            return "passed";
        } else if (this.getSkipped() > 0) {
            return "skipped";
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
     * @param idValue
     *            the id to set
     */
    public final void setId(String idValue) {
        this.id = idValue;
    }

    /**
     * @param descriptionValue
     *            the description to set
     */
    public final void setDescription(String descriptionValue) {
        this.description = descriptionValue;
    }

    /**
     * @param nameValue
     *            the name to set
     */
    public final void setName(String nameValue) {
        this.name = nameValue;
    }

    /**
     * @param keywordValue
     *            the keyword to set
     */
    public final void setKeyword(String keywordValue) {
        this.keyword = keywordValue;
    }

    /**
     * @param lineValue
     *            the line to set
     */
    public final void setLine(Long lineValue) {
        this.line = lineValue;
    }

    /**
     * @param stepsValue
     *            the steps to set
     */
    public final void setSteps(CucumberStepResult[] stepsValue) {
        this.steps = stepsValue;
    }

    /**
     * @param typeValue
     *            the type to set
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
     * @param tagsValue
     *            the tags to set
     */
    public final void setTags(CucumberTagResults[] tagsValue) {
        this.tags = tagsValue;
    }

    /**
     * @return the duration
     */
    public final double getDuration() {
        return duration;
    }

    public final CucumberBeforeAfterResult getBefore() {
        return before;
    }

    public final CucumberBeforeAfterResult getAfter() {
        return after;
    }

    public final int getRerunAttempts() {
        return rerunAttempts;
    }

    public void addRerunAttempts(int count) {
        this.rerunAttempts += count;
    }
    public String[] getAllTags() {
        String[] result = {};
        for (CucumberTagResults tag : this.getTags()) {
            result = (String[]) ArrayUtils.add(result, tag.getName());
        }
        return result;
    }
    public boolean isInTagSet(String[] include, String[] exclude) {
        String[] tagValues = this.getAllTags();
        for (String tag : include) {
            if (ArrayUtils.contains(tagValues, tag)) {
                return true;
            }
        }
        for (String tag : exclude) {
            if (ArrayUtils.contains(tagValues, tag)) {
                System.out.println("Exclude tag found: " + tag);
                return false;
            }
        }
        return true;
    }

    public final String[] getIncludeCoverageTags() {
        return includeCoverageTags;
    }

    public final void setIncludeCoverageTags(String[] includeCoverageTagsValue) {
        this.includeCoverageTags = includeCoverageTagsValue;
    }

    public final String[] getExcludeCoverageTags() {
        return excludeCoverageTags;
    }

    public final void setExcludeCoverageTags(String[] excludeCoverageTagsValue) {
        this.excludeCoverageTags = excludeCoverageTagsValue;
    }
}
