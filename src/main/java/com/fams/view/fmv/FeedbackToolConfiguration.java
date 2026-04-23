package com.fams.view.fmv;

import com.fams.utils.callbacks.Callback;
import com.fams.view.BaseToolConfiguration;
import javafx.scene.control.Tooltip;

public class FeedbackToolConfiguration extends BaseToolConfiguration {
    public FeedbackToolConfiguration(Callback saveCallback, Callback addCallback, Callback removeCallback) {
        super(saveCallback, addCallback, removeCallback);

        super.saveBtn.setTooltip(new Tooltip("Save Changes To Selected Fragment"));
        super.addBtn.setTooltip(new Tooltip("Add New Fragment"));
        super.removeBtn.setTooltip(new Tooltip("Delete Selected Fragment"));
    }
}
