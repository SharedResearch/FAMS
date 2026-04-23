package com.fams.view.pmv;

import com.fams.utils.AppConfig;
import com.fams.utils.ProtocolManager;
import com.fams.utils.ImageManager;
import com.fams.utils.callbacks.StringCallback;

import javafx.geometry.Pos;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class ProtocolMiddleMenu extends HBox {
    private final ChoiceBox<String> courseSelection = new ChoiceBox<>();
    private final ChoiceBox<String> protocolSelection = new ChoiceBox<>();
    private final List<String> selectedCategories = new ArrayList<>();
    private final MenuButton settingsBtn = new MenuButton();

    public ProtocolMiddleMenu(StringCallback protocolSelectionCallback) {
        courseSelection.setId("feedbackSelection");
        courseSelection.setTooltip(new Tooltip("Select Course"));
        protocolSelection.setId("feedbackSelection");
        protocolSelection.setTooltip(new Tooltip("Select Assignment"));
        protocolSelection.setStyle("-fx-pref-width: 200;");

        courseSelection.getItems().setAll(AppConfig.COURSE_NAME);
        protocolSelection.getItems().setAll(ProtocolManager.INSTANCE.getProtocolNames());

        protocolSelection.setOnAction(event -> {
            selectedCategories.clear();
            selectedCategories.addAll(ProtocolManager.INSTANCE.getProtocol(protocolSelection.getValue()).getCategories());
            settingsBtn.getItems().clear();
            settingsBtn.getItems().addAll(createItems());
            protocolSelectionCallback.call(protocolSelection.getValue());
        });

        courseSelection.setValue(courseSelection.getItems().get(0));
        protocolSelection.setValue(protocolSelection.getItems().get(0));

        ImageView img = ImageManager.INSTANCE.getImage("fragments");
        img.setFitHeight(25);
        img.setFitWidth(25);

        settingsBtn.setGraphic(img);
        settingsBtn.setTooltip(new Tooltip("Fragment Categories"));
        settingsBtn.setId("toolBtn");

        this.setAlignment(Pos.CENTER);
        this.getChildren().setAll(courseSelection, settingsBtn, protocolSelection);
    }

    private List<CheckMenuItem> createItems() {
        List<CheckMenuItem> items = new ArrayList<>();
        for (String category : AppConfig.getCourseDetails().get(AppConfig.COURSE_NAME)) {
            CheckMenuItem item = new CheckMenuItem(category);
            item.setMnemonicParsing(false);
            item.setSelected(selectedCategories.contains(category));

            item.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
                if (newValue) {
                    selectedCategories.add(item.getText());
                } else {
                    selectedCategories.remove(item.getText());
                }
            });
            items.add(item);
        }

        return items;
    }

    public List<String> getSelectedCategories() { return selectedCategories; }
}
