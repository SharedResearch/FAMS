package com.fams.view.fmv;

import com.fams.utils.AppConfig;
import com.fams.utils.callbacks.Callback;
import com.fams.utils.ImageManager;

import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.List;
import java.util.Map;

public class FeedbackMiddleMenu extends HBox {
    private final ChoiceBox<String> courseSelection = new ChoiceBox<>();
    private final ChoiceBox<String> assignmentSelection = new ChoiceBox<>();
    private final ToggleButton renderButton = new ToggleButton();

    public FeedbackMiddleMenu(Callback toggleViewCallback, Callback assignmentSelectionCallback) {
        courseSelection.setId("feedbackSelection");
        courseSelection.setTooltip(new Tooltip("Select Course"));
        assignmentSelection.setId("feedbackSelection");
        assignmentSelection.setTooltip(new Tooltip("Select Assignment"));
        Map<String, List<String>> courseDetails = AppConfig.getCourseDetails();
        String selectedCourse = "DT180G";
        courseSelection.getItems().setAll(courseDetails.keySet());
        assignmentSelection.getItems().setAll(courseDetails.get(selectedCourse));

        courseSelection.setValue(selectedCourse);
        assignmentSelection.setValue(courseDetails.get(selectedCourse).get(0));

        assignmentSelection.setOnAction(event -> assignmentSelectionCallback.call());

        renderButton.setId("toolBtn");
        renderButton.setTooltip(new Tooltip("Toggle Source / Output view"));
        renderButton.setOnAction(event -> {
            changeToggleImg();
            toggleViewCallback.call();
        });
        renderButton.setSelected(true);
        changeToggleImg();

        this.setAlignment(Pos.CENTER);
        this.getChildren().setAll(courseSelection, renderButton, assignmentSelection);
    }

    private void changeToggleImg() {
        ImageView img = ImageManager.INSTANCE.getImage(renderButton.isSelected() ? "inspect_output" : "inspect_code");
        img.setFitHeight(35);
        img.setFitWidth(35);
        renderButton.setGraphic(img);
    }

    public boolean renderBtnState() { return renderButton.isSelected(); }

    public String getSelectedCourse() { return courseSelection.getValue(); }
    public String getSelectedAssignment() { return assignmentSelection.getValue(); }
}
