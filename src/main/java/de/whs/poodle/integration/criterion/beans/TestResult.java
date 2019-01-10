package de.whs.poodle.integration.criterion.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TestResult {
    @JsonProperty("success")
    public boolean success;
}
