package com.fams.view.gmv;

import com.fams.model.Component;
import com.fams.model.Protocol;
import com.fams.utils.AppConfig;

import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GradingContent extends VBox {
    private final Map<String, AspectGrading> aspects = new LinkedHashMap<>();

    public GradingContent() {
        this.setSpacing(5);
        this.setId("protocolView");
    }

    public void setupProtocol(Protocol protocol) {
        this.getChildren().clear();
        this.aspects.clear();

        for (Component component : protocol.getComponents()) {
            if (component.getType().equals(AppConfig.COMPONENT_ASPECT)) {
                AspectGrading aspect = new AspectGrading(component.getInfo(), component.getFragments());
                this.getChildren().add(aspect);
                aspect.setId((this.getChildren().size() % 2 == 0) ? "componentBoxEven" : "componentBoxOdd");
                aspects.put(component.getInfo(), aspect);
            }
        }
    }

    public List<String> getAspectValues() {
        List<String> out = new ArrayList<>();
        for (AspectGrading aspect : aspects.values()) {
            out.addAll(aspect.getAspectValues());
        }
        return out;

        /*
        return aspects.values().stream()
                .map(AspectGrading::getAspectValue)
                .filter(value -> !value.isEmpty())
                .collect(Collectors.toList());*/
    }

    public List<String> getAspectValuesForKey(String key) {
        //return aspects.get(key).getAspectValue();
        return aspects.get(key).getAspectValues();
    }

    public void resetProtocol() {
        for (AspectGrading aspect : aspects.values()) {
            aspect.resetAspect();
        }
    }
}
