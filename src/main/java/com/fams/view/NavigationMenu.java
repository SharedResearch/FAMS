package com.fams.view;

import com.fams.utils.AppConfig;
import com.fams.utils.callbacks.StringCallback;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

import java.util.LinkedHashMap;
import java.util.Map;

public class NavigationMenu extends HBox {
    private ToggleButton currentSelection;

    /*
    private Map<String, String> buttonInfo = new LinkedHashMap<>() {{
        put("Grading Management", "grade");
        put("Feedback Management", "box");
        put("Protocol Management", "task");
    }};*/

    public NavigationMenu(StringCallback navigationStringCallback) {
        ToggleGroup toggleGroup = new ToggleGroup();

        Map<String, String> map = new LinkedHashMap<>();
        map.put(AppConfig.NAVIGATION_GRADING_VIEW, "exam");
        map.put(AppConfig.NAVIGATION_PROTOCOL_VIEW, "task");
        map.put(AppConfig.NAVIGATION_FEEDBACK_VIEW, "box");

        for (Map.Entry<String, String> entry : map.entrySet()) {
            ToggleButton tBtn = ComponentManager.createToggleGroupButton(toggleGroup, entry, 30);
            this.getChildren().add(tBtn);

            if (AppConfig.NAVIGATION_GRADING_VIEW.equals(entry.getKey()))
                currentSelection = tBtn;
        }

        this.setId("gradingToolbar");
        this.setAlignment(Pos.CENTER_LEFT);

        toggleGroup.selectedToggleProperty().addListener((ob, o, n) -> {
            ToggleButton rb = (ToggleButton) toggleGroup.getSelectedToggle();
            if (rb != null && rb.getText() != null) {
                //renderCallback.call(rb.getText());
                navigationStringCallback.call(rb.getText());
            }

            // logic to make toggle button behave as we need (unselectable)
            if (currentSelection != null) {
                currentSelection.setDisable(false);
            }

            if (rb == null) {
                throw new IllegalStateException("Selected toggle button cannot be null");
            }

            rb.setDisable(true);
            currentSelection = rb;
        });

        currentSelection.fire();
    }
}
