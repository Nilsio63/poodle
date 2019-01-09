package de.whs.poodle.integration.criterion.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class SuiteResult {
    @JsonProperty("id")
    public int id;

    @JsonProperty("suiteId")
    public int suiteId;

    @JsonProperty("userId")
    public int userId;

    @JsonProperty("compileError")
    public String compileError;

    @JsonProperty("status")
    public int status;

    @JsonProperty("creationTime")
    public String creationTime;

    @JsonProperty("successCount")
    public int successCount;

    @JsonProperty("testCount")
    public int testCount;

   // @JsonProperty("tests")
   // public TestResult[] tests;

    @Override
    public String toString() {
        return "Suite{" +
                "id=" + id +
                ", creationTime='" + creationTime + '\'' +
                ", successCount='" + successCount + '\'' +
                ", testCount='" + testCount + '\'' +
             //   ", tests=" + Arrays.toString(tests) +
                '}';
    }

    public void SetInfo(int id, int suiteId, String compErr, int status, int successCount, int testCount) {
        this.id = id;
        this.suiteId = suiteId;
        this.compileError = compErr;
        this.status = status;
        this.successCount = successCount;
        this.testCount = testCount;
    }
}
