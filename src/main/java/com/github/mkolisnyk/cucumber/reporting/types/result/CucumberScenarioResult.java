package com.github.mkolisnyk.cucumber.reporting.types.result;

import org.apache.commons.lang.ArrayUtils;

import com.cedarsoftware.util.io.JsonObject;
import com.github.mkolisnyk.cucumber.reporting.utils.helpers.JsonUtils;

public class CucumberScenarioResult {

    private CucumberFeatureResult feature;
    private String id;
    private CucumberTagResults[] tags;
    private String description;
    private String name;
    private String keyword;
    private Long line;
    private CucumberStepResult[] steps = {};
    private String type;

    private CucumberBeforeAfterResult before;
    private CucumberBeforeAfterResult after;

    private int passed = 0;
    private int failed = 0;
    private int skipped = 0;
    private int undefined = 0;
    private int known = 0;
    private double duration = 0.f;
    private int rerunAttempts = 0;

    private String[] includeCoverageTags = {};
    private String[] excludeCoverageTags = {};

    public CucumberScenarioResult() {
    }

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
        this.tags = JsonUtils.toTagArray((JsonObject<String, Object>) json);
    }

    public void updateFailedToKnown() {
        for (CucumberStepResult step : steps) {
            String status = step.getResult().getStatus();
            if (status.equalsIgnoreCase("failed")) {
                step.getResult().setStatus("known");
            }
        }
    }
    private void valuateStepStatus(String status) {
        if (status.equalsIgnoreCase("passed")) {
            this.passed++;
        } else if (status.equalsIgnoreCase("known")) {
            this.known++;
        } else if (status.equalsIgnoreCase("failed")) {
            this.failed++;
        } else if (status.equalsIgnoreCase("skipped")) {
            this.skipped++;
        } else {
            this.undefined++;
        }
    }
    public void valuate() {
        final int nanosecondsInMillisecond = 1000000;
        final float millesecondsInSecond = 1000.f;
        passed = 0;
        failed = 0;
        skipped = 0;
        undefined = 0;
        known = 0;
        this.duration = 0.f;
        if (steps == null) {
            return;
        }
        for (CucumberStepResult step : steps) {
            String status = step.getResult().getStatus();
            valuateStepStatus(status);
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

    public int getKnown() {
        return known;
    }

    public String getStatus(boolean valuate) {
        if (valuate) {
            valuate();
        }
        String[] statuses = {
            "failed",
            "known",
            "undefined",
            "skipped",
            "passed",
        };
        for (String status : statuses) {
            int value = 0;
            try {
                value = this.getClass().getDeclaredField(status).getInt(this);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            if (value > 0) {
                return status;
            }
        }
        return "undefined";
    }
    public String getStatus() {
        return getStatus(true);
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
     * @param nameValue
     *            the name to set
     */
    public final void setName(String nameValue) {
        this.name = nameValue;
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

    public CucumberFeatureResult getFeature() {
        return feature;
    }

    public void setFeature(CucumberFeatureResult featureValue) {
        this.feature = featureValue;
    }
    public boolean isSameAs(CucumberScenarioResult another) {
        return another != null && this.getId().equals(another.getId());
    }

    public void setPassed(int passedValue) {
        this.passed = passedValue;
    }

    public void setFailed(int failedValue) {
        this.failed = failedValue;
    }

    public void setSkipped(int skippedValue) {
        this.skipped = skippedValue;
    }

    public void setUndefined(int undefinedValue) {
        this.undefined = undefinedValue;
    }

    public void setKnown(int knownValue) {
        this.known = knownValue;
    }
}
