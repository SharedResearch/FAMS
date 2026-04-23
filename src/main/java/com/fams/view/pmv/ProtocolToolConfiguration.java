package com.fams.view.pmv;

import com.fams.utils.callbacks.Callback;
import com.fams.view.BaseToolConfiguration;
import javafx.scene.control.Tooltip;

public class ProtocolToolConfiguration extends BaseToolConfiguration {

    public ProtocolToolConfiguration(Callback saveCallback, Callback addCallback, Callback removeCallback) {
        super(saveCallback, addCallback, removeCallback);

        super.saveBtn.setTooltip(new Tooltip("Save Changes To Selected Protocol"));
        super.addBtn.setTooltip(new Tooltip("Add New Protocol"));
        super.removeBtn.setTooltip(new Tooltip("Delete Selected Protocol"));

        super.saveBtn.setDisable(true);
        super.addBtn.setDisable(true);
        super.removeBtn.setDisable(true);
        super.saveBtn.setStyle("-fx-border-width: 0;");
        super.addBtn.setStyle("-fx-border-width: 0;");
        super.removeBtn.setStyle("-fx-border-width: 0;");
    }
}
