package com.fams.view.gmv;

import com.fams.utils.ImageManager;
import com.fams.view.MyTooltip;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AspectGrading extends HBox {
    private final Label aspectLabel;
    private final List<AspectTool> aspectTools = new ArrayList<>();
    private final VBox rightContainer = new VBox();

    public AspectGrading(String aspectInfo, List<String> fragmentSymbols) {
        aspectLabel = new Label(aspectInfo);
        aspectLabel.setId("aspectLabel");

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        ImageView img = ImageManager.INSTANCE.getImage("plus");
        img.setFitWidth(10);
        img.setFitHeight(10);
        Button addBtn = new Button();
        addBtn.setGraphic(img);
        addBtn.setId("toolBtn");

        rightContainer.getChildren().add(addBtn);
        rightContainer.setSpacing(5);
        rightContainer.setAlignment(Pos.TOP_RIGHT);

        addBtn.setOnAction(event -> {
            AspectTool aspectTool = new AspectTool(fragmentSymbols);
            aspectTools.add(aspectTool);
            rightContainer.getChildren().add(rightContainer.getChildren().size() - 1, aspectTool);
        });
        addBtn.fire();

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(aspectLabel, region, rightContainer);
    }

    public String getAspectInfo() { return aspectLabel.getText(); }

    public List<String> getAspectValues() {
        return aspectTools.stream()
                .map(AspectTool::getAspectValue)
                .filter(value -> !value.isEmpty())
                .collect(Collectors.toList());
        /*
        List<String> out = new ArrayList<>();
        for (AspectTool aspectTool : aspectTools) {
            out.add(aspectTool.getAspectValue());
        }
        return out;*/
    }

    public void resetAspect() {
        List<AspectTool> aspectsToRemove = new ArrayList<>();
        List<Node> nodesToRemove = new ArrayList<>();

        for (int i = 1; i < rightContainer.getChildren().size() -1; i++) {
            aspectsToRemove.add((AspectTool) rightContainer.getChildren().get(i));
            nodesToRemove.add(rightContainer.getChildren().get(i));
        }
        aspectTools.removeAll(aspectsToRemove);
        rightContainer.getChildren().removeAll(nodesToRemove);
        aspectTools.get(0).resetAspect();
    }


    class AspectTool extends HBox {
        private final CheckBox freeTextSelect = new CheckBox();
        private final ChoiceBox<String> fragmentSelection = new ChoiceBox<>();
        private final TextArea freeTextArea = new TextArea();

        public AspectTool(List<String> fragmentSymbols) {
            fragmentSelection.getItems().add("-");
            fragmentSelection.getItems().addAll(fragmentSymbols);
            fragmentSelection.setValue("-");
            fragmentSelection.setId("fragmentSelection");

            fragmentSelection.setOnMouseEntered(event -> { // show preview on hover
                if (fragmentSelection.getValue().equals("-")) {
                    fragmentSelection.setTooltip(new Tooltip("Select Fragment"));
                    return;
                }
                fragmentSelection.setTooltip(new MyTooltip(fragmentSelection.getValue()));
            });

            HBox contentContainer = new HBox(fragmentSelection);

            ImageView img = ImageManager.INSTANCE.getImage("text");
            img.setFitWidth(10);
            img.setFitHeight(10);
            Label lbl = new Label();
            lbl.setGraphic(img);

            freeTextArea.setWrapText(true);
            freeTextArea.setId("aspectTextArea");

            freeTextSelect.setTooltip(new Tooltip("Toggle Free Text"));
            freeTextSelect.setPadding(new Insets(0, 0, 0, 5));
            freeTextSelect.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (freeTextSelect.isSelected()) {
                        contentContainer.getChildren().clear();
                        contentContainer.getChildren().add(freeTextArea);
                    } else {
                        contentContainer.getChildren().clear();
                        contentContainer.getChildren().add(fragmentSelection);
                    }
                }
            });

            HBox freeTextBox = new HBox(lbl, freeTextSelect);
            freeTextBox.setAlignment(Pos.CENTER);
            this.getChildren().setAll(contentContainer, freeTextBox);
            this.setAlignment(Pos.CENTER);
            this.setSpacing(10);
        }

        public String getAspectValue() {
            String out = (freeTextSelect.isSelected()) ? freeTextArea.getText() : fragmentSelection.getValue();
            return (out.equals("-")) ? "" : out;
        }

        public void resetAspect() {
            freeTextArea.setText("");
            fragmentSelection.setValue("-");
            freeTextSelect.setSelected(false);
        }
    }
}
