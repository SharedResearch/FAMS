package com.fams.view.gmv.configtools;

import com.fams.utils.AppConfig;
import com.fams.utils.ImageManager;
import com.fams.utils.ProtocolManager;
import com.fams.utils.callbacks.Callback;
import com.fams.utils.callbacks.StringCallback;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ProtocolConfiguration extends HBox {
    private final ChoiceBox<String> courseSelection = new ChoiceBox<>();
    private final ChoiceBox<String> protocolSelection = new ChoiceBox<>();
    public ProtocolConfiguration(StringCallback protocolChangeCallback, Callback protocolResetCallback) {
        ImageView img = ImageManager.INSTANCE.getImage("update");
        img.setFitHeight(20);
        img.setFitWidth(20);
        Button updateBtn = new Button();
        updateBtn.setGraphic(img);
        updateBtn.setId("toolBtn");
        updateBtn.setOnAction(event -> protocolResetCallback.call());

        courseSelection.setId("feedbackSelection");
        courseSelection.setTooltip(new Tooltip("Select Course"));
        protocolSelection.setId("feedbackSelection");
        protocolSelection.setTooltip(new Tooltip("Select Assignment"));
        protocolSelection.setStyle("-fx-pref-width: 200;");

        courseSelection.getItems().setAll(AppConfig.COURSE_NAME);
        protocolSelection.getItems().setAll(ProtocolManager.INSTANCE.getProtocolNames());

        protocolSelection.setOnAction(event -> protocolChangeCallback.call(protocolSelection.getValue()));

        courseSelection.setValue(courseSelection.getItems().get(0));
        protocolSelection.setValue(protocolSelection.getItems().get(0));

        this.getChildren().addAll(updateBtn, courseSelection, protocolSelection);
        this.setId("gradingToolbar");
        /*this.setStyle("-fx-pref-width: 400;");*/
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER_RIGHT);
    }
}
