package com.fams.view.gmv;

import com.fams.utils.callbacks.StringCallback;
import com.fams.view.ComponentManager;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

import java.util.LinkedHashMap;
import java.util.Map;

public class GradingViewMenu extends HBox {
    private ToggleButton currentViewSelection;
    private final Map<String, String> buttonInfo = new LinkedHashMap<>() {{
        put("Protocol", "report_card");
        put("Output", "inspect_output");
        put("Source", "inspect_code");
    }};

    public GradingViewMenu(StringCallback renderStringCallback) {
        ToggleGroup toggleRendererGroup = new ToggleGroup();

        for (Map.Entry<String, String> entry : buttonInfo.entrySet()) {
            ToggleButton tBtn = ComponentManager.createToggleGroupButton(toggleRendererGroup, entry, 35);
            this.getChildren().add(tBtn);

            if ("Protocol".equals(entry.getKey()))
                currentViewSelection = tBtn;
        }

        this.setId("gradingToolbar");
        this.setAlignment(Pos.CENTER);

        toggleRendererGroup.selectedToggleProperty().addListener((ob, o, n) -> {
            ToggleButton rb = (ToggleButton) toggleRendererGroup.getSelectedToggle();
            if (rb != null && rb.getText() != null) {
                renderStringCallback.call(rb.getText());
            }

            // logic to make toggle button behave as we need (unselectable)
            if (currentViewSelection != null) {
                currentViewSelection.setDisable(false);
            }

            if (rb == null) {
                throw new IllegalStateException("Selected toggle button cannot be null");
            }

            rb.setDisable(true);
            currentViewSelection = rb;
        });

        currentViewSelection.fire();
    }
}

