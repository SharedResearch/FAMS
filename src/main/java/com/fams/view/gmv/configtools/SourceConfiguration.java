package com.fams.view.gmv.configtools;

import com.fams.utils.callbacks.Callback;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SourceConfiguration extends HBox {
    private final CheckBox checkBox = new CheckBox("Render Nested Symbols");
    public SourceConfiguration(Callback showCompostiteContentCallback) {
        this.getChildren().add(checkBox);
        checkBox.setFont(Font.font("Helvetica", FontWeight.BOLD, 12));

        this.setId("gradingToolbar");
        this.setAlignment(Pos.CENTER_RIGHT);
        checkBox.setOnAction(event -> showCompostiteContentCallback.call());
    }

    public boolean getCheckboxValue() {
        return checkBox.isSelected();
    }
}
