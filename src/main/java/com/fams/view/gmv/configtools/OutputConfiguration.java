package com.fams.view.gmv.configtools;

import com.fams.utils.callbacks.Callback;
import com.fams.utils.ImageManager;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.Arrays;

public class OutputConfiguration extends HBox {
    public OutputConfiguration(Callback createPDFCallback) {
        for (String imgName : Arrays.asList("pdf", "html5")) {
            ImageView img = ImageManager.INSTANCE.getImage(imgName);
            img.setFitHeight(25);
            img.setFitWidth(25);

            Button button = new Button();
            button.setGraphic(img);
            button.setId("toolBtn");
            button.setTooltip(new Tooltip(String.format("Export as %s document", imgName.toUpperCase())));

            if (imgName.equals("pdf")) {
                button.setOnAction(actionEvent -> createPDFCallback.call());
            } else {
                button.setOnAction(actionEvent -> System.out.println(imgName.toUpperCase()));
            }

            this.getChildren().add(button);
        }

        this.setId("gradingToolbar");
        this.setAlignment(Pos.CENTER_RIGHT);
    }
}
