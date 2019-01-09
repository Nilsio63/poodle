package de.whs.poodle.integration.criterion.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class Suite {
    @JsonProperty("id")
    private int id;

    @JsonProperty("creationTime")
    private String creationTime;

    @JsonProperty("tests")
    private Test[] tests;

    public Suite() {
        tests = new Test[0];
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public Test[] getTests() {
        return tests;
    }

    public void setTests(Test[] tests) {
        this.tests = tests;
    }

    @Override
    public String toString() {
        return "Suite{" +
                "id=" + id +
                ", creationTime='" + creationTime + '\'' +
                ", tests=" + Arrays.toString(tests) +
                '}';
    }
}
