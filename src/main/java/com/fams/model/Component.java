package com.fams.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

public class Component {
    private String info;
    private final String type;
    private List<String> fragments;

    @JsonCreator
    public Component(@JsonProperty("Info") String info,
                     @JsonProperty("Type") String type,
                     @JsonProperty("Fragments") List<String> fragments) {
        this.info = info;
        this.type = type;
        this.fragments = fragments;
    }

    public String getInfo() { return info; }
    public void setInfo(String info) { this.info = info; }
    public String getType() { return type; }
    public List<String> getFragments() { return fragments; }
    public void setFragments(List<String> fragments) {
        this.fragments = fragments;
    }
}
