package org.example.outfit.model;

import java.util.Map;

public class ImageAnalysisResponse {
    private String description;
    private Map<String, String> attributes;

    // Getters et Setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}
