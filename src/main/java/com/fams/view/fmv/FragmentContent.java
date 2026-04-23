package com.fams.view.fmv;

import com.fams.model.Fragment;
import com.fams.utils.AppConfig;
import com.fams.view.gmv.RenderContent;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FragmentContent extends BorderPane {
    private final TextField fragmentID = new TextField();
    private final ChoiceBox<String> courseRelationSelection = new ChoiceBox<>();
    private final ChoiceBox<String> assignmentRelationSelection = new ChoiceBox<>();
    private StackPane mainView = new StackPane();

    private TextArea sourceContent;
    private WebView renderedContent;

    public FragmentContent() {
        VBox fragmentIDBox = new VBox();
        fragmentIDBox.setSpacing(10);
        Label fragmentIDHeading = new Label("Fragment ID");
        fragmentIDHeading.setId("fragmentLabel");
        fragmentIDBox.getChildren().setAll(fragmentIDHeading, fragmentID);

        Map<String, List<String>> courseDetails = AppConfig.getCourseDetails();

        Label courseRelationLabel = new Label("Course");
        courseRelationLabel.setId("fragmentLabel");
        courseRelationSelection.getItems().setAll(courseDetails.keySet());
        courseRelationSelection.setId("feedbackSelection");
        courseRelationSelection.setTooltip(new Tooltip("Select Course Relation"));
        HBox fragmentCourseRelation = new HBox(courseRelationLabel, courseRelationSelection);
        fragmentCourseRelation.setSpacing(10);

        Label assignmentRelationLabel = new Label("Assignment");
        assignmentRelationLabel.setId("fragmentLabel");
        assignmentRelationSelection.setId("feedbackSelection");
        assignmentRelationSelection.getItems().setAll(courseDetails.get("DT180G"));
        assignmentRelationSelection.setTooltip(new Tooltip("Select Assignment Relation"));
        HBox fragmentAssignmentRelation = new HBox(assignmentRelationLabel, assignmentRelationSelection);
        fragmentAssignmentRelation.setSpacing(10);

        courseRelationSelection.setStyle("-fx-border-width: 2 0 0 0;");
        assignmentRelationSelection.setStyle("-fx-border-width: 0 0 2 0;");

        VBox fragmentRelationBox = new VBox();
        fragmentRelationBox.getChildren().setAll(fragmentCourseRelation, fragmentAssignmentRelation);
        fragmentRelationBox.setAlignment(Pos.CENTER_RIGHT);

        Region middleFiller = new Region();
        HBox.setHgrow(middleFiller, Priority.ALWAYS);

        HBox fragmentInfoBox = new HBox();
        fragmentInfoBox.getChildren().setAll(fragmentIDBox, middleFiller, fragmentRelationBox);
        fragmentInfoBox.setId("fragmentInfoBox");

        fragmentID.setId("fragmentIDField");
        fragmentID.setEditable(false);
        mainView.setAlignment(Pos.TOP_LEFT);

        this.setTop(fragmentInfoBox);
        this.setCenter(mainView);
        this.setStyle("-fx-background-color: transparent;");
    }
    /*
    public void setFragment(Fragment fragment, boolean useRenderedContent) {
        this.currentlySelectedFragment = fragment;
        fragmentID.setText(fragment.getFragmentIdentifier().replace(AppConfig.FRAGMENT_SYMBOL_MAIN, ""));
        updateView(useRenderedContent);
    }*/

    public String getCourseRelation() { return courseRelationSelection.getValue(); }
    public String getAssignmentRelation() { return assignmentRelationSelection.getValue(); }
    public String getSourceContent() { return sourceContent.getText(); }

    public void updateView(Fragment fragment, boolean useRenderedContent) {
        fragmentID.setText(fragment.getFragmentIdentifier().replace(AppConfig.FRAGMENT_SYMBOL_MAIN, ""));
        courseRelationSelection.setValue(fragment.getCourse());
        assignmentRelationSelection.setValue(fragment.getAssignment());

        RenderContent.INSTANCE.renderOutput(Collections.singletonList(fragment.getFragmentIdentifier()));
        renderedContent = RenderContent.INSTANCE.getOutputView();

        RenderContent.INSTANCE.renderSource(Collections.singletonList(fragment.getFragmentIdentifier()), false);
        sourceContent = RenderContent.INSTANCE.getSourceView();

        if (useRenderedContent) {
            mainView.getChildren().setAll(renderedContent);
        } else {
            sourceContent.setEditable(true);
            mainView.getChildren().setAll(sourceContent);
        }
    }
}
