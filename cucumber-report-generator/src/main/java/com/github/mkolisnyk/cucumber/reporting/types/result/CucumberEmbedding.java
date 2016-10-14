package com.github.mkolisnyk.cucumber.reporting.types.result;

import org.apache.commons.codec.binary.Base64;

import com.cedarsoftware.util.io.JsonObject;

public class CucumberEmbedding {
    private String mimeType;
    private byte[] data;
    public CucumberEmbedding(JsonObject<String, Object> json) {
        this.mimeType = (String) json.get("mime_type");
        this.data = (byte[]) Base64.decodeBase64((String) json.get("data"));
    }
    public String getMimeType() {
        return mimeType;
    }
    public byte[] getData() {
        return data;
    }
}
