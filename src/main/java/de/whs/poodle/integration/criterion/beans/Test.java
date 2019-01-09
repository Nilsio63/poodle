package de.whs.poodle.integration.criterion.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Test implements Serializable {
    @JsonProperty("id")
    private int id;

    @JsonProperty("suiteId")
    private int suiteId;

    @JsonProperty("input")
    private String input;

    @JsonProperty("output")
    private String output;

    @JsonProperty("isHidden")
    private boolean isHidden;

    @JsonProperty("creationTime")
    private String creationTime;

    public int getId() {
        return id;
    }

    public int getSuiteId() {
        return suiteId;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    public boolean getIsHidden() {
        return isHidden;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSuiteId(int suiteId) {
        this.suiteId = suiteId;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public void setIsHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

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
