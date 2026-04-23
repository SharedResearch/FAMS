package com.fams.view.gmv;

import com.fams.view.ComponentManager;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class GradingSideBar extends VBox {
    private ToggleButton currentSelection;
    private final VBox content = new VBox();
    public GradingSideBar() {
        Map<String, String> buttonInfo = new LinkedHashMap<>() {{
            put("Students", "students");
            put("Aspects", "idea");
        }};

        ToggleGroup toggleGroup = new ToggleGroup();
        HBox options = new HBox();
        options.setAlignment(Pos.CENTER);

        for (Map.Entry<String, String> entry : buttonInfo.entrySet()) {
            ToggleButton tBtn = ComponentManager.createToggleGroupButton(toggleGroup, entry, 20);
            options.getChildren().add(tBtn);

            if ("Students".equals(entry.getKey())) {
                currentSelection = tBtn;
            } else { // disable aspect button, as we don't have implementation for it
                tBtn.setDisable(true);
                tBtn.setStyle("-fx-border-width: 0;"); // so that we don't underline the btn
            }
        }
        this.getChildren().add(options);

        toggleGroup.selectedToggleProperty().addListener((ob, o, n) -> {
            ToggleButton rb = (ToggleButton) toggleGroup.getSelectedToggle();
            if (rb != null && rb.getText() != null) {
                //renderCallback.call(rb.getText());
                updateContent(rb.getText());
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

        /*-----------------*/

        this.setId("gradingSideBar");

        this.getChildren().add(content);
        content.setPadding(new Insets(10,0,0,0));
    }

    private void updateContent(String value) {
        content.getChildren().clear();

        ToggleGroup toggleGroup = new ToggleGroup();
        String val = (value.equals("Students")) ? "Student" : "Aspect";
        IntStream.range(1, 10).mapToObj(i -> {
            ToggleButton stud = new ToggleButton(val + " " + i);
            stud.setMnemonicParsing(false); // to retain underscores in text value
            VBox.setMargin(stud, new Insets(0, 0, 0, 12));
            stud.setId("customLink");
            stud.setToggleGroup(toggleGroup);
            return stud;
        }).forEach(content.getChildren()::add);
    }
}
