package com.fams.view.pmv.components;

import com.fams.utils.AppConfig;
import com.fams.utils.callbacks.ComponentCallback;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

import java.util.List;

public class StaticContentComponent extends BaseComponent {
    private final TextField contentField = new TextField();

    public StaticContentComponent(ComponentCallback callback, List<String> fragments) {
        super(callback, AppConfig.COMPONENT_STATIC_CONTENT);

        if (!fragments.isEmpty()) {
            contentField.setText(fragments.get(0));
        }
        contentField.setTooltip(new Tooltip("Enter Fragment ID"));
        contentField.setId("aspectInfoField");

        this.getChildren().setAll(contentField, super.componentTypeLabel, super.region, super.dragBtn, super.deleteBtn);
    }

    public String getContent() { return contentField.getText(); }
}
