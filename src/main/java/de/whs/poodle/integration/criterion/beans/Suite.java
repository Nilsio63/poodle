package de.whs.poodle.integration.criterion.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class Suite {
    @JsonProperty("id")
    public int id;

    @JsonProperty("creationTime")
    public String creationTime;

    @JsonProperty("tests")
    public Test[] tests;

    @Override
    public String toString() {
        return "Suite{" +
                "id=" + id +
                ", creationTime='" + creationTime + '\'' +
                ", tests=" + Arrays.toString(tests) +
                '}';
    }
}
