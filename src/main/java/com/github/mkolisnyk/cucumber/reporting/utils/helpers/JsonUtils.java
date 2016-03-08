package com.github.mkolisnyk.cucumber.reporting.utils.helpers;

import com.cedarsoftware.util.io.JsonObject;
import com.github.mkolisnyk.cucumber.reporting.types.result.CucumberTagResults;

public final class JsonUtils {
    private JsonUtils() {
    }
    private CucumberTagResults[] getCucumberTags(JsonObject<String, Object> json) {
        JsonObject<String, Object> tagEntry = (JsonObject<String, Object>) json
                .get("tags");
        Object[] objs = {};
        if (tagEntry != null) {
            objs = (Object[]) ((JsonObject<String, Object>) json.get("tags"))
                    .get("@items");
        }
        CucumberTagResults[] tags = new CucumberTagResults[objs.length];
        for (int i = 0; i < objs.length; i++) {
            tags[i] = new CucumberTagResults(
                    (JsonObject<String, Object>) objs[i]);
        }
        return tags;
    }
    public static CucumberTagResults[] toTagArray(JsonObject<String, Object> json) {
        JsonUtils utils = new JsonUtils();
        return utils.getCucumberTags(json);
    }
}
