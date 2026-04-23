package com.fams.controller;

import com.fams.utils.AppConfig;
import com.fams.utils.FragmentsManager;
import com.fams.view.SectionTop;
import com.fams.view.fmv.FeedbackMiddleMenu;
import com.fams.view.fmv.NewFragmentDialog;
import com.fams.view.fmv.FeedbackSideBar;
import com.fams.model.Fragment;
import com.fams.view.fmv.FragmentContent;
import com.fams.view.fmv.FeedbackToolConfiguration;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class FeedbackController {
    private final VBox sectionLeft;
    private final StackPane sectionCenter;
    private final SectionTop sectionTop;
    private final FeedbackSideBar feedbackSideBar;
    private final FragmentContent fragmentContent;
    private final FeedbackMiddleMenu feedbackMiddleMenu;
    private final FeedbackToolConfiguration feedbackToolConfiguration;
    private Fragment currentlySelectedFragment;

    public FeedbackController(SectionTop sectionTop, StackPane sectionCenter, VBox sectionLeft) {
        this.sectionLeft = sectionLeft;
        this.sectionCenter = sectionCenter;
        this.sectionTop = sectionTop;
        this.fragmentContent = new FragmentContent();

        feedbackMiddleMenu = new FeedbackMiddleMenu(this::toggleView, this::updateFragmentsSelection);

        feedbackToolConfiguration = new FeedbackToolConfiguration(
                this::saveChangesToFragment, this::addNewFragment, this::removeSelectedFragment);

        feedbackSideBar = new FeedbackSideBar(this::setCurrentSelectedFragment, feedbackMiddleMenu.getSelectedCourse(),
                feedbackMiddleMenu.getSelectedAssignment());
    }

    public void activateController() {
        sectionCenter.getChildren().add(fragmentContent);
        sectionTop.setCenterMenu(feedbackMiddleMenu);
        sectionTop.setRightMenu(feedbackToolConfiguration);
        sectionLeft.getChildren().setAll(feedbackSideBar);
    }

    private void toggleView() {
        fragmentContent.updateView(currentlySelectedFragment, feedbackMiddleMenu.renderBtnState());
    }

    private void updateFragmentsSelection() {
        feedbackSideBar.updateSideContent(
                feedbackMiddleMenu.getSelectedCourse(), feedbackMiddleMenu.getSelectedAssignment(), "");
    }

    private void setCurrentSelectedFragment(String id) {
        currentlySelectedFragment = FragmentsManager.INSTANCE.getFragmentById(id);
        fragmentContent.updateView(currentlySelectedFragment, feedbackMiddleMenu.renderBtnState());
    }

    private void saveChangesToFragment() {
        currentlySelectedFragment.setCourse(fragmentContent.getCourseRelation());
        currentlySelectedFragment.setAssignment(fragmentContent.getAssignmentRelation());
        currentlySelectedFragment.setContent(fragmentContent.getSourceContent());
        FragmentsManager.INSTANCE.updateFragment(currentlySelectedFragment);

        feedbackSideBar.updateSideContent(
                feedbackMiddleMenu.getSelectedCourse(), feedbackMiddleMenu.getSelectedAssignment(), currentlySelectedFragment.getFragmentIdentifier());
    }

    private void addNewFragment() {
        NewFragmentDialog dialog = new NewFragmentDialog();
        String id = dialog.showAndWait().orElse(null);
        if (id == null || id.isEmpty()) {
            return;
        }

        // format id to ensure there are no empty spaces
        String fragmentID = id.trim().toUpperCase().replaceAll("\\s+","_");

        // This part is for storing the new fragment to be used as selection. NOT IMPLEMENTED
        Fragment fragment = FragmentsManager.INSTANCE.createNewFragment(fragmentID,
                feedbackMiddleMenu.getSelectedCourse(), feedbackMiddleMenu.getSelectedAssignment());

        if (fragment == null) {
            return;
        }
        // -----------------------

        feedbackSideBar.updateSideContent(
                feedbackMiddleMenu.getSelectedCourse(), feedbackMiddleMenu.getSelectedAssignment(), AppConfig.FRAGMENT_SYMBOL_MAIN + fragmentID);
    }

    private void removeSelectedFragment() {
        if (FragmentsManager.INSTANCE.deleteFragment(currentlySelectedFragment.getFragmentIdentifier())) {
            feedbackSideBar.updateSideContent(
                    feedbackMiddleMenu.getSelectedCourse(), feedbackMiddleMenu.getSelectedAssignment(), "");
        }
    }
}
