package com.fams.controller;

import com.fams.utils.AppConfig;
import com.fams.utils.callbacks.StringCallback;
import com.fams.utils.ViewRenderer;
import com.fams.view.SectionCenter;
import com.fams.view.SectionLeft;
import com.fams.view.SectionTop;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainController extends BorderPane {
    private final SectionTop sectionTop;
    private final VBox sectionLeft = new SectionLeft();
    private final StackPane sectionCenter = new SectionCenter();

    private final StringCallback stageTitleStringCallback;  // callback used for changing title of stage

    private final GradingController gradingController;
    private final FeedbackController feedbackController;
    private final ProtocolController protocolController;

    public MainController(StringCallback stageTitleStringCallback) {
        ViewRenderer vr = ViewRenderer.INSTANCE;  // just to initialize this heavy instance
        this.stageTitleStringCallback = stageTitleStringCallback;
        this.sectionTop = new SectionTop();

        this.setTop(sectionTop);
        this.setLeft(sectionLeft);
        this.setCenter(sectionCenter);

        gradingController = new GradingController(sectionTop, sectionCenter, sectionLeft);
        feedbackController = new FeedbackController(sectionTop, sectionCenter, sectionLeft);
        protocolController = new ProtocolController(sectionTop, sectionCenter);

        this.sectionTop.initializeNavigationMenu(this::navigationChange);
    }

    private void navigationChange(String value) {
        sectionLeft.getChildren().clear();
        sectionCenter.getChildren().clear();
        sectionTop.clearMenus();

        switch (value) {
            case AppConfig.NAVIGATION_GRADING_VIEW -> {
                sectionLeft.setPrefWidth(100);
                gradingController.activateController();
            }
            case AppConfig.NAVIGATION_FEEDBACK_VIEW -> {
                sectionLeft.setPrefWidth(200);
                feedbackController.activateController();
            }
            case AppConfig.NAVIGATION_PROTOCOL_VIEW -> {
                sectionLeft.setPrefWidth(0);
                protocolController.activateController();
            }
        }

        // change title of stage, using callback
        this.stageTitleStringCallback.call(value);
    }
}
