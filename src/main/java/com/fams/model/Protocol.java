package com.fams.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

public class Protocol {
    private String protocolName;
    private List<String> categories;
    private List<Component> components;

    @JsonCreator
    public Protocol(@JsonProperty("ProtocolName") String protocolName,
                    @JsonProperty("Categories") List<String> categories,
                    @JsonProperty("Components") List<Component> components) {
        this.protocolName = protocolName;
        this.categories = categories;
        this.components = components;
    }

    public String getProtocolName() { return protocolName; }
    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    public List<String> getCategories() { return categories; }
    public List<Component> getComponents() { return components; }

    public void addComponent(Component component) { components.add(component); }
    public void removeComponent(Component component) { components.remove(component); }
}
