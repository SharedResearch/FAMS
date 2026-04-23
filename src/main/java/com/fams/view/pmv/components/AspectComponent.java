package com.fams.view.pmv.components;

import com.fams.utils.AppConfig;
import com.fams.utils.FragmentsManager;
import com.fams.utils.ImageManager;
import com.fams.utils.callbacks.ComponentCallback;

import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AspectComponent extends BaseComponent {
    private final TextField aspectInfo = new TextField();
    private final List<String> selectedFragments;
    private final List<String> fragmentIDs = new ArrayList<>();

    public AspectComponent(ComponentCallback callback, String info, List<String> fragments, List<String> fragmentCategories) {
        super(callback, AppConfig.COMPONENT_ASPECT);

        aspectInfo.setId("aspectInfoField");
        aspectInfo.setTooltip(new Tooltip("Enter Aspect Description"));
        aspectInfo.setText(info);

        selectedFragments = fragments;

        FragmentsManager man = FragmentsManager.INSTANCE;
        for (String category : fragmentCategories) {
            fragmentIDs.addAll(man.getFilterFragmentIDs(AppConfig.COURSE_NAME, category));
        }
        Collections.sort(fragmentIDs);

        ImageView img1 = ImageManager.INSTANCE.getImage("fragments");
        img1.setFitHeight(25);
        img1.setFitWidth(25);
        MenuButton fragmentsMenu = new MenuButton();
        fragmentsMenu.setGraphic(img1);
        fragmentsMenu.setId("fragmentsMenuButton");
        fragmentsMenu.setTooltip(new Tooltip("Select Fragments"));
        fragmentsMenu.getItems().addAll(createFragmentItems());

        this.getChildren().setAll(aspectInfo, super.componentTypeLabel, fragmentsMenu, super.region, super.dragBtn, super.deleteBtn);
    }

    private List<CheckMenuItem> createFragmentItems() {
        List<CheckMenuItem> items = new ArrayList<>();

        for (String id : fragmentIDs) {
            CheckMenuItem item = new CheckMenuItem(id);
            item.setMnemonicParsing(false);
            item.setSelected(selectedFragments.contains(id));

            item.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
                if (newValue) {
                    selectedFragments.add(item.getText());
                } else {
                    selectedFragments.remove(item.getText());
                }
            });
            items.add(item);
        }

        return items;
    }
}
