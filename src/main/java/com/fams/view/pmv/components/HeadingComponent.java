package com.fams.view.pmv.components;

import com.fams.utils.AppConfig;
import com.fams.utils.ImageManager;
import com.fams.utils.callbacks.ComponentCallback;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class HeadingComponent extends BaseComponent {
    private final HBox headingBox = new HBox();
    private final TextField headingField = new TextField();
    private final Label headingLabel = new Label();
    private final ToggleButton lockBtn = new ToggleButton();

    public HeadingComponent(ComponentCallback callback, String info) {
        super(callback, AppConfig.COMPONENT_HEADING);

        headingField.setText(info);
        headingField.setTooltip(new Tooltip("Enter Header Description"));
        headingField.setId("aspectInfoField");

        headingLabel.setText(info);
        headingLabel.setId("headingLabel");

        headingBox.setAlignment(Pos.CENTER);

        lockBtn.setId("toolBtn");
        lockBtn.setTooltip(new Tooltip("Toggle Edit / Rendering Mode"));
        lockBtn.setOnAction(event -> {
            changeToggleImg();
            toggleFieldLabel();
        });
        lockBtn.setSelected(!info.isEmpty());
        lockBtn.fire();

        this.getChildren().setAll(headingBox, super.componentTypeLabel, lockBtn, super.region, super.dragBtn, super.deleteBtn);
    }

    private void changeToggleImg() {
        ImageView img = ImageManager.INSTANCE.getImage(lockBtn.isSelected() ? "unlock" : "lock");
        img.setFitHeight(35);
        img.setFitWidth(35);
        lockBtn.setGraphic(img);
    }

    private void toggleFieldLabel() {
        headingBox.getChildren().clear();
        headingLabel.setText(headingField.getText());
        headingBox.getChildren().add(lockBtn.isSelected() ? headingField : headingLabel);
    }
}
