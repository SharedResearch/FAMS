package com.fams.view.fmv;

import com.fams.utils.AppConfig;
import com.fams.utils.FragmentsManager;
import com.fams.utils.callbacks.StringCallback;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Collections;
import java.util.List;

public class FeedbackSideBar extends VBox {
    private final VBox sideContent = new VBox();
    private final StringCallback fragmentsCallback;

    public FeedbackSideBar(StringCallback fragmentsCallback, String selectedCourse, String selectedAssignment) {
        this.fragmentsCallback = fragmentsCallback;
        ScrollPane scroller = new ScrollPane(sideContent);
        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        sideContent.setStyle("-fx-padding: 5 0 0 0;");

        this.getChildren().add(scroller);
        VBox.setVgrow(scroller, Priority.ALWAYS);
        VBox.setVgrow(this, Priority.ALWAYS);
        updateSideContent(selectedCourse, selectedAssignment, "");
    }

    public void updateSideContent(String selectedCourse, String selectedAssignment, String fragmentToSelect) {
        sideContent.getChildren().clear();

        ToggleButton selectedBtn = null;  // will be used when creating new fragments

        //List<String> fragmentIds = FragmentsManager.INSTANCE.getAllFragmentIDs();
        List<String> fragmentIds = FragmentsManager.INSTANCE.getFilterFragmentIDs(selectedCourse, selectedAssignment);
        Collections.sort(fragmentIds);

        ToggleGroup toggleGroup = new ToggleGroup();
        for (String id : fragmentIds) {
            // UGLY SOLUTION
            String newID = id.replace(AppConfig.FRAGMENT_SYMBOL_MAIN, "").replace("_", " ");
            ToggleButton btn = new ToggleButton(newID);
            btn.setStyle("-fx-max-width: 190;");
            btn.setWrapText(true);
            btn.setAlignment(Pos.CENTER_LEFT);
            if (id.equals(fragmentToSelect)) {
                selectedBtn = btn;
            }
            // -------------

            btn.setMnemonicParsing(false); // to retain underscores in text value

            btn.setOnAction(event -> fragmentsCallback.call(id));
            btn.setId("customLink");
            btn.setToggleGroup(toggleGroup);
            sideContent.getChildren().add(btn);
        }

        if (sideContent.getChildren().isEmpty()) { // in case there are no fragments
            return;
        }

        if (selectedBtn == null) {
            selectedBtn = (ToggleButton) sideContent.getChildren().get(0);
        }
        selectedBtn.fire();
    }


}
