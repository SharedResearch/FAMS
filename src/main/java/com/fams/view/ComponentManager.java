package com.fams.view;

import com.fams.utils.ImageManager;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

import java.util.Map;

public class ComponentManager {
    public static ToggleButton createToggleGroupButton(ToggleGroup toggleGroup, Map.Entry<String, String> entry, int size) {
        String key = entry.getKey();
        ToggleButton tBtn = new ToggleButton(key);

        ImageView img = ImageManager.INSTANCE.getImage(entry.getValue());
        img.setFitHeight(size);
        img.setFitWidth(size);
        tBtn.setGraphic(img);

        tBtn.setId("toolBtn");
        tBtn.setTooltip(new Tooltip(key));
        tBtn.setToggleGroup(toggleGroup);

        return tBtn;
    }
}
