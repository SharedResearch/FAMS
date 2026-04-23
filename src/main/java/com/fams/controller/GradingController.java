package com.fams.controller;

import com.fams.model.Component;
import com.fams.model.Protocol;
import com.fams.utils.AppConfig;
import com.fams.utils.ImageManager;
import com.fams.utils.ProtocolManager;
import com.fams.utils.ViewRenderer;
import com.fams.view.SectionTop;
import com.fams.view.gmv.GradingContent;
import com.fams.view.gmv.GradingSideBar;
import com.fams.view.gmv.GradingViewMenu;
import com.fams.view.gmv.RenderContent;
import com.fams.view.gmv.configtools.OutputConfiguration;
import com.fams.view.gmv.configtools.ProtocolConfiguration;
import com.fams.view.gmv.configtools.SourceConfiguration;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GradingController {
    private final SectionTop sectionTop;
    private Protocol selectedProtocol;
    private final VBox sectionLeft;
    private final StackPane sectionCenter;
    private final GradingContent gradingContent = new GradingContent();
    private final List<HBox> configTools = Arrays.asList(
            new ProtocolConfiguration(this::updateSelectedProtocol, this::resetProtocol),
            new OutputConfiguration(this::createPDF), new SourceConfiguration(this::toggleShowCompositeSourceContent));

    public GradingController(SectionTop sectionTop, StackPane sectionCenter, VBox sectionLeft) {
        this.sectionTop = sectionTop;
        this.sectionCenter = sectionCenter;
        this.sectionLeft = sectionLeft;
    }

    public void activateController() {
        this.sectionLeft.getChildren().setAll(new GradingSideBar());
        sectionTop.setCenterMenu(new GradingViewMenu(this::updateRenderView));
    }

    private void updateRenderView(String value) {
        List<String> out = gradingContent.getAspectValues();

        switch (value) {
            case AppConfig.GRADING_VIEW_PROTOCOL -> {
                ScrollPane scroller = new ScrollPane(gradingContent);
                scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                scroller.setFitToWidth(true);

                sectionCenter.getChildren().setAll(scroller);
                this.sectionTop.setRightMenu(configTools.get(0));
            }
            case AppConfig.GRADING_VIEW_OUTPUT -> {
                RenderContent.INSTANCE.renderOutput(out);
                sectionCenter.getChildren().setAll(RenderContent.INSTANCE.getOutputView());
                this.sectionTop.setRightMenu(configTools.get(1));
            }
            case AppConfig.GRADING_VIEW_SOURCE -> {
                RenderContent.INSTANCE.renderSource(out, ((SourceConfiguration) configTools.get(2)).getCheckboxValue());
                TextArea src = RenderContent.INSTANCE.getSourceView();
                src.setEditable(false);
                sectionCenter.getChildren().setAll(src);
                this.sectionTop.setRightMenu(configTools.get(2));
            }
        }
    }

    private void updateSelectedProtocol(String protocolName) {
        this.selectedProtocol = ProtocolManager.INSTANCE.getProtocol(protocolName);
        gradingContent.setupProtocol(selectedProtocol);
    }

    private void toggleShowCompositeSourceContent() {
        updateRenderView(AppConfig.GRADING_VIEW_SOURCE);
    }

    private void createPDF() {
        //String content = ViewRenderer.INSTANCE.getRendererOutput(gradingContent.getAspectValues());
        String pageBreak = "<div style=\"page-break-after:always;\"></div>";

        StringBuilder strBuilder = new StringBuilder("<html><body>");
        strBuilder.append("<br/>".repeat(5));
        strBuilder.append(ImageManager.INSTANCE.getMiunLogoHTML());
        strBuilder.append("<br/>".repeat(7)).append("<h1>DT180G Object-Oriented Programming I</h1>");
        String assignment = selectedProtocol.getProtocolName().replace(" Protocol", "");
        strBuilder.append("<em><h2>").append(assignment).append(" Feedback</h2></em>");
        strBuilder.append(pageBreak);
        strBuilder.append("<h1>Feedback</h1>");

        ViewRenderer vr = ViewRenderer.INSTANCE;
        for (Component component : selectedProtocol.getComponents()) {
            switch (component.getType()) {
                case AppConfig.COMPONENT_ASPECT -> {
                    List<String> vals = gradingContent.getAspectValuesForKey(component.getInfo());
                    strBuilder.append(vr.getRendererOutput(vals));
                }
                case AppConfig.COMPONENT_STATIC_CONTENT ->
                        strBuilder.append(vr.getRendererOutput(component.getFragments()));
                case AppConfig.COMPONENT_HEADING -> {
                    strBuilder.append(pageBreak);
                    strBuilder.append(vr.getRendererOutput(Collections.singletonList("<h2>" + component.getInfo() + "</h2>")));
                }
            }
        }

        //strBuilder.append(content);
        strBuilder.append("</body></html>");

        String outputPath = "output/studid.pdf";
        ensureOutputFolderExists(outputPath);

        ViewRenderer.INSTANCE.convertHtmlToPdf(strBuilder.toString(), outputPath);
    }

    private void ensureOutputFolderExists(String outputPath) {
        File outputFolder = new File(outputPath).getParentFile();
        if (!outputFolder.exists()) {
            if (outputFolder.mkdirs()) {
                System.out.println("Output folder created: " + outputFolder.getAbsolutePath());
            } else {
                System.err.println("Failed to create output folder: " + outputFolder.getAbsolutePath());
            }
        }
    }

    public void resetProtocol() {
        gradingContent.resetProtocol();
    }
}
