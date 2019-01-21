package de.whs.poodle.integration.criterion.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TestResult {
    @JsonProperty("id")
    public int id;

    @JsonProperty("stdout")
    public String stdout;

    @JsonProperty("stderr")
    public String stderr;

    @JsonProperty("input")
    public String input;

    @JsonProperty("expected")
    public String expected;

    @JsonProperty("success")
    public boolean success;
}
