package com.fams.utils;

import com.fams.model.Fragment;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public enum ViewRenderer {
    INSTANCE;

    private final WebView outputView = new WebView();
    private final WebView tinyOutputView = new WebView();
    private final WebEngine viewEngine = outputView.getEngine();
    private final WebEngine toolTipEngine = tinyOutputView.getEngine();

    private final Parser parser;
    private final HtmlRenderer renderer;

    private ITextRenderer iTextRenderer = new ITextRenderer();  // only used for creating PDF

    ViewRenderer() {
        outputView.autosize();
        outputView.setId("outputView");

        tinyOutputView.autosize();
        tinyOutputView.setId("tinyOutputView");

        parser = Parser.builder().build();
        renderer = HtmlRenderer.builder().build();

        String userStyleSheet = Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm();
        toolTipEngine.setUserStyleSheetLocation(userStyleSheet);
        viewEngine.setUserStyleSheetLocation(userStyleSheet);

        /* NOT WORKING
        toolTipEngine.documentProperty().addListener(new ChangeListener<Document>() {
            @Override
            public void changed(ObservableValue<? extends Document> observable, Document oldValue, Document newValue) {
                if (newValue != null) {
                    // Adjust the WebView's size based on content
                    // JavaScript to get the scroll height, which represents the content's height
                    String script = "document.documentElement.scrollHeight";

                    // Execute the script and retrieve the result
                    Object result = toolTipEngine.executeScript(script);

                    // If the result is a number, update the WebView's height
                    if (result instanceof Number) {
                        double contentHeight = ((Number) result).doubleValue();
                        tinyOutputView.setPrefHeight(contentHeight);
                    }
                }
            }
        });*/
    }

    private String processContent(String content) {
        return content.startsWith(AppConfig.FRAGMENT_SYMBOL_MAIN) ?
                Optional.ofNullable(FragmentsManager.INSTANCE.getFragmentById(content))
                        .map(Fragment::getCompositeContent).orElse(null) : content;
    }

    public void renderOutput(List<String> content) {
        List<String> out = content.stream()
                .map(this::processContent)
                .collect(Collectors.toList());

        String output = renderer.render(parser.parse(String.join("\n\n", out)));
        viewEngine.loadContent(output, "text/html");
    }

    /**
     * Used for creating PDF documents.
     * @param content information to be rendered.
     * @return string of rendered content.
     */
    public String getRendererOutput(List<String> content) {
        List<String> out = content.stream()
                .map(this::processContent)
                .collect(Collectors.toList());

        return renderer.render(parser.parse(String.join("\n\n", out)));
    }

    public void renderTooltipOutput(String content) {
        toolTipEngine.loadContent(""); // Clear previous content
        String processedContent = processContent(content);
        String output = renderer.render(parser.parse(processedContent));
        toolTipEngine.loadContent(output, "text/html");
    }

    public String getRenderTooltipContent(String content) {
        String processedContent = processContent(content);
        return renderer.render(parser.parse(processedContent));
    }

    public void convertHtmlToPdf(String htmlContent, String outputPath) {
        try (FileOutputStream outputStream = new FileOutputStream(outputPath)) {
            iTextRenderer.setDocumentFromString(htmlContent);
            iTextRenderer.layout();
            iTextRenderer.createPDF(outputStream);
            iTextRenderer.finishPDF();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WebView getOutputView() { return outputView; }
    public WebView getTinyOutputView() { return tinyOutputView; }
}

