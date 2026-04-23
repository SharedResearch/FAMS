package com.fams;

import com.fams.controller.MainController;
import com.fams.utils.FeedbackDBUtil;
import com.fams.utils.FragmentsManager;
import com.fams.utils.ImageManager;
import com.fams.utils.ProtocolManager;
import com.fams.utils.ViewRenderer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class App extends Application {
    private Stage stage;

    private void setStageTitle(String title) {
        this.stage.setTitle("FAMS - " + title);
    }

    @Override
    public void start(Stage stage) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");

        this.stage = stage;

        //DataModel model = new DataModel("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        //GradingView view = new GradingView();
        //new MainController(view);

        Scene scene = new Scene(new MainController(this::setStageTitle), 1200, 800);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());

        stage.setScene(scene);
        //stage.setTitle("FAMS - Grading Management View");
        //stage.setOpacity(.9);
        stage.getIcons().add(Objects.requireNonNull(ImageManager.INSTANCE.getImage("miun_icon_small")).getImage());
        stage.show();
    }

    public void launchApp() {
        FeedbackDBUtil db = FeedbackDBUtil.INSTANCE;
        FragmentsManager fm = FragmentsManager.INSTANCE;
        ProtocolManager pm = ProtocolManager.INSTANCE;

        launch();

        db.closeDB();
    }
}
