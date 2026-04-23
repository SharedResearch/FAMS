package com.fams.view.fmv;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

public class NewFragmentDialog extends Dialog<String> {
    private TextField fragmentIDField = new TextField();
    private final Label label;
    public NewFragmentDialog() {
        initModality(Modality.APPLICATION_MODAL);

        // Set button types
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        this.getDialogPane().getButtonTypes().addAll(cancelButtonType);

        // Hide the cancel button so it won't show on the dialog
        this.getDialogPane().lookupButton(cancelButtonType).setVisible(false);

        // Handle close request to simulate clicking the hidden cancel button
        this.setOnCloseRequest(event -> {
            Button cancelButton = (Button) this.getDialogPane().lookupButton(cancelButtonType);
            cancelButton.fire();
        });

        // If cancel button is clicked, the result is null
        this.setResultConverter(button -> {
            if (button == cancelButtonType) {
                return null;
            }
            //return fragmentIDField.getText();
            return null;
        });

        // Set up the content
        VBox vbox = new VBox(10); // 10px spacing
        vbox.setAlignment(Pos.CENTER);
        label = new Label("Enter Fragment Identifier");
        label.setStyle("-fx-font-family: 'Verdana', Arial, sans-serif;\n" +
                "    -fx-font-size: 12;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #494949;");

        fragmentIDField = new TextField();
        fragmentIDField.setStyle("-fx-background-color: transparent;\n" +
                "    -fx-border-color: #646363;\n" +
                "    -fx-border-radius: 10;\n" +
                "    -fx-font-family: 'Verdana', Arial, sans-serif;\n" +
                "    -fx-font-size: 12;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-padding: 10px;");

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> handleOk());
        submitButton.setStyle("-fx-background-color: transparent;\n" +
                "    -fx-border-color: #646363;\n" +
                "    -fx-border-radius: 10;\n" +
                "    -fx-font-family: 'Verdana', Arial, sans-serif;\n" +
                "    -fx-font-size: 11;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-pref-width: 200;\n" +
                "    -fx-padding: 10px;\n" +
                "    -fx-alignment: center;");

        vbox.getChildren().addAll(label, fragmentIDField, submitButton);

        getDialogPane().setContent(vbox);
        setOnCloseRequest(event -> close());
        //getDialogPane().setPrefWidth(300);
        getDialogPane().setStyle("-fx-background-color: linear-gradient(to bottom, white, #6477a6); -fx-pref-width: 300;");

    }

    private void handleOk() {
        String idValue = fragmentIDField.getText();

        /* Validation not working
        if (FragmentsManager.INSTANCE.checkIfFragmentExist(idValue)) {
            label.setText("Identifier Already Exist!");
            label.setStyle("-fx-text-fill: red;");
            return;
        }*/

        // Close the dialog and set the result
        setResult(idValue);
        close();
    }
}
