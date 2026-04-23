package com.fams.controller;

import com.fams.utils.ProtocolManager;
import com.fams.view.SectionTop;
import com.fams.view.pmv.ProtocolContent;
import com.fams.view.pmv.ProtocolMiddleMenu;
import com.fams.view.pmv.ProtocolToolConfiguration;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ProtocolController {
    private final ProtocolContent protocolContent = new ProtocolContent();

    private final SectionTop sectionTop;
    private final StackPane sectionCenter;
    private final ProtocolMiddleMenu protocolMiddleMenu;
    private final ProtocolToolConfiguration protocolToolConfiguration;
    private final ScrollPane scroller;

    public ProtocolController(SectionTop sectionTop, StackPane sectionCenter) {
        this.sectionTop = sectionTop;
        this.sectionCenter = sectionCenter;

        protocolMiddleMenu = new ProtocolMiddleMenu(this::updateProtocolSelection);
        protocolToolConfiguration = new ProtocolToolConfiguration(
                this::saveChangesToProtocol, this::addNewProtocol, this::removeSelectedProtocol);

        scroller = new ScrollPane(protocolContent);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroller.setFitToWidth(true);
    }

    public void activateController() {
        sectionTop.setCenterMenu(protocolMiddleMenu);
        sectionTop.setRightMenu(protocolToolConfiguration);
        sectionCenter.getChildren().add(scroller);
    }

    private void updateProtocolSelection(String protocolName) {
        protocolContent.setupProtocol(ProtocolManager.INSTANCE.getProtocol(protocolName));
    }

    private void saveChangesToProtocol() {
        System.out.println("Saving protocol");
    }

    private void addNewProtocol() {
        System.out.println("Adding protocol");
    }

    private void removeSelectedProtocol() {
        System.out.println("Removing protocol");
    }
}
