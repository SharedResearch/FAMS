package com.fams.view.gmv;

import com.fams.model.Fragment;
import com.fams.utils.FragmentsManager;
import com.fams.utils.ViewRenderer;

import javafx.scene.control.TextArea;
import javafx.scene.web.WebView;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum RenderContent {
    INSTANCE;
    private final TextArea sourceView = new TextArea();

    RenderContent() {
        //sourceView.setId("gradingTextArea");
        sourceView.setWrapText(true);
        sourceView.setEditable(false);
        //sourceView.setStyle("-fx-background-color: blue;");
    }

    public void renderSource(List<String> content, boolean showCompositeContent) {
        List<String> out = content.stream()
                .map(s -> {
                    if (s.startsWith("@@@")) {
                        Optional<Fragment> fragment = Optional.ofNullable(FragmentsManager.INSTANCE.getFragmentById(s));
                        return showCompositeContent ? fragment.map(Fragment::getCompositeContent).orElse(null)
                                : fragment.map(Fragment::getContent).orElse(null);
                    }
                    return s;
                })
                .collect(Collectors.toList());

        sourceView.setText(String.join("\n\n", out));
    }

    /**
     * Acts as Facade to collect needed information from ViewRenderer.
     * @param content the information to be rendered.
     */
    public void renderOutput(List<String> content) {
        ViewRenderer.INSTANCE.renderOutput(content);
    }

    public WebView getOutputView() { return ViewRenderer.INSTANCE.getOutputView(); }
    public TextArea getSourceView() { return sourceView; }

    //public VBox getProtocolView() { return gradingContent; }
}
