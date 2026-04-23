package com.fams.view;
import com.fams.utils.ViewRenderer;

import javafx.scene.control.Tooltip;
import javafx.scene.web.WebView;

public class MyTooltip extends Tooltip {
    public MyTooltip(String txt) {
        ViewRenderer.INSTANCE.renderTooltipOutput(txt);
        WebView view = ViewRenderer.INSTANCE.getTinyOutputView();
        //tinyOutputView.setPrefSize(400, 300);

        setGraphic(view);
    }
}
