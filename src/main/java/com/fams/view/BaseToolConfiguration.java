package com.fams.view;

import com.fams.utils.callbacks.Callback;
import com.fams.utils.ImageManager;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class BaseToolConfiguration extends HBox {
    protected final Button saveBtn = createButton("save");
    protected final Button addBtn = createButton("plus");
    protected final Button removeBtn = createButton("remove");

    protected BaseToolConfiguration(Callback saveCallback, Callback addCallback, Callback removeCallback) {
        saveBtn.setOnAction(actionEvent -> saveCallback.call());
        addBtn.setOnAction(actionEvent -> addCallback.call());
        removeBtn.setOnAction(actionEvent -> removeCallback.call());

        this.getChildren().setAll(saveBtn, addBtn, removeBtn);

        this.setId("gradingToolbar");
        this.setAlignment(Pos.CENTER_RIGHT);
    }

    private Button createButton(String imgName) {
        ImageView img = ImageManager.INSTANCE.getImage(imgName);
        img.setFitHeight(25);
        img.setFitWidth(25);

        Button button = new Button();
        button.setGraphic(img);
        button.setId("toolBtn");
        return button;
    }
}
