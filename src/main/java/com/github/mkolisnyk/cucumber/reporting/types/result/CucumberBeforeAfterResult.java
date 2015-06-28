package com.github.mkolisnyk.cucumber.reporting.types.result;

import com.cedarsoftware.util.io.JsonObject;

public class CucumberBeforeAfterResult {

	private CucumberMatch match;
	private CucumberResult result;
	
	@SuppressWarnings("unchecked")
	public CucumberBeforeAfterResult(JsonObject<String, Object> json) {
		this.result = new CucumberResult(
                (JsonObject<String, Object>) json.get("result"));
		this.match = new CucumberMatch(
                (JsonObject<String, Object>) json.get("match"));
	}

	public final CucumberMatch getMatch() {
		return match;
	}

	public final CucumberResult getResult() {
		return result;
	}
	
}
