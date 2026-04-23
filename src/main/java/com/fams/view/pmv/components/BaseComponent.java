package com.fams.view.pmv.components;

import com.fams.utils.ImageManager;
import com.fams.utils.callbacks.ComponentCallback;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class BaseComponent extends HBox {
    protected final Label componentTypeLabel;
    protected Button dragBtn = new Button();
    protected final Button deleteBtn = new Button();
    protected final Region region = new Region();

    public BaseComponent(ComponentCallback callback, String componentType) {
        componentTypeLabel = new Label((componentType + " Component").toUpperCase());
        componentTypeLabel.setId("componentTypeLabel");

        ImageView img1 = ImageManager.INSTANCE.getImage("menu");
        img1.setFitHeight(20);
        img1.setFitWidth(20);

        dragBtn.setGraphic(img1);
        dragBtn.setTooltip(new Tooltip("Reposition Component"));
        dragBtn.setId("toolBtn");

        ImageView img2 = ImageManager.INSTANCE.getImage("cross");
        img2.setFitHeight(20);
        img2.setFitWidth(20);

        deleteBtn.setGraphic(img2);
        deleteBtn.setTooltip(new Tooltip("Remove Component"));
        deleteBtn.setId("toolBtn");
        deleteBtn.setOnAction(event -> callback.call(this));

        HBox.setHgrow(region, Priority.ALWAYS);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
    }
}
