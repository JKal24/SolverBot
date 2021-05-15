package com.astro.smitesolver.smite.entity.characters.auxiliary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Description {

    private String description;
    private String value;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "[" +
                "Description='" + description + '\'' +
                ", Value='" + value + '\'' +
                ']';
    }
}
