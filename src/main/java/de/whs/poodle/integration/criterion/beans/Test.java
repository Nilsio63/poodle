package de.whs.poodle.integration.criterion.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Test {
    @JsonProperty("id")
    int id;

    @JsonProperty("suiteId")
    int suiteId;

    @JsonProperty("input")
    String input;

    @JsonProperty("output")
    String output;

    @JsonProperty("isHidden")
    boolean isHidden;

    @JsonProperty("creationTime")
    String creationTime;

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", suiteId=" + suiteId +
                ", input='" + input + '\'' +
                ", output='" + output + '\'' +
                ", isHidden=" + isHidden +
                ", creationTime='" + creationTime + '\'' +
                '}';
    }
}
