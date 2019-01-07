package de.whs.poodle.integration.criterion.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class SuiteResult {
    @JsonProperty("id")
    public int id;

    @JsonProperty("compileError")
    public String compileError;

    @JsonProperty("Status")
    public int status;

    @JsonProperty("creationTime")
    public String creationTime;

    @JsonProperty("tests")
    public TestResult[] tests;

    @Override
    public String toString() {
        return "Suite{" +
                "id=" + id +
                ", creationTime='" + creationTime + '\'' +
                ", tests=" + Arrays.toString(tests) +
                '}';
    }

    public void SetInfo(int id, String compErr, int status) {
        this.id = id;
        this.compileError = compErr;
        this.status = status;
    }
}
