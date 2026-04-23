package com.fams.view.pmv;

import com.fams.model.Component;
import com.fams.model.Protocol;
import com.fams.utils.AppConfig;
import com.fams.utils.ImageManager;
import com.fams.view.pmv.components.AspectComponent;
import com.fams.view.pmv.components.BaseComponent;
import com.fams.view.pmv.components.HeadingComponent;
import com.fams.view.pmv.components.StaticContentComponent;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class ProtocolContent extends VBox {
    private final MenuButton choiceControl = createChoiceControl();
    private Protocol selectedProtocol;

    public ProtocolContent() {
        this.setSpacing(10);
        this.setPadding(new Insets(10, 10, 10, 10));
    }

    public void setupProtocol(Protocol protocol) {
        this.selectedProtocol = protocol;
        this.getChildren().clear();
        this.getChildren().add(choiceControl);

        for (Component component : protocol.getComponents()) {
            if (component.getType().equals(AppConfig.COMPONENT_ASPECT)) {
                addNewAspectComponent(component.getInfo(), component.getFragments());
            } else if (component.getType().equals(AppConfig.COMPONENT_HEADING)) {
                addNewHeadingComponent(component.getInfo());
            } else if (component.getType().equals(AppConfig.COMPONENT_STATIC_CONTENT)) {
                addNewStaticContentComponent(component.getFragments());
            }
        }
    }

    private void removeComponent(BaseComponent component) {
        this.getChildren().remove(component);
    }

    private void addNewAspectComponent(String info, List<String> fragments) {
        addNewComponent(new AspectComponent(this::removeComponent, info, fragments, selectedProtocol.getCategories()));
    }

    private void addNewHeadingComponent(String info) {
        addNewComponent(new HeadingComponent(this::removeComponent, info));
    }

    private void addNewStaticContentComponent(List<String> fragments) {
        addNewComponent(new StaticContentComponent(this::removeComponent, fragments));
    }

    private void addNewComponent(BaseComponent component) {
        this.getChildren().add(this.getChildren().size() - 1, component);
        component.setId(this.getChildren().size() % 2 == 0 ? "componentBoxEven" : "componentBoxOdd");
    }

    private MenuButton createChoiceControl() {
        ImageView img = ImageManager.INSTANCE.getImage("plus");
        img.setFitHeight(25);
        img.setFitWidth(25);
        MenuButton choiceControl = new MenuButton();
        choiceControl.setGraphic(img);

        choiceControl.setId("toolBtn");
        choiceControl.setAlignment(Pos.TOP_LEFT);

        MenuItem addAspectItem = new MenuItem("Add Aspect");
        addAspectItem.setOnAction(event -> addNewAspectComponent("", new ArrayList<>()));

        MenuItem addHeadingItem = new MenuItem("Add Heading");
        addHeadingItem.setOnAction(event -> addNewHeadingComponent(""));

        MenuItem addStaticContentItem = new MenuItem("Add Static Content");
        addStaticContentItem.setOnAction(event -> addNewStaticContentComponent(new ArrayList<>()));

        choiceControl.getItems().addAll(addAspectItem, addHeadingItem, addStaticContentItem);
        return choiceControl;
    }
}
