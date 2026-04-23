package com.fams.view;

import com.fams.utils.callbacks.StringCallback;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class SectionTop extends HBox {
    private final int prefSize = 200;
    private final StackPane leftMenu, centerMenu, rightMenu;

    public SectionTop() {
        leftMenu = new StackPane();
        centerMenu = new StackPane();
        rightMenu = new StackPane();

        leftMenu.setPrefWidth(350);
        rightMenu.setPrefWidth(350);

        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);

        this.getChildren().setAll(leftMenu, region1, centerMenu, region2, rightMenu);

        this.setId("sectionTop");
    }

    /**
     * The class needs to be instantiated before setting up navigation menu.
     * @param navigationStringCallback callback for whenever navigation changes.
     */
    public void initializeNavigationMenu(StringCallback navigationStringCallback) {
        HBox nav = new NavigationMenu(navigationStringCallback);
        nav.setPrefWidth(prefSize);
        leftMenu.getChildren().add(nav);
    }

    public void setRightMenu(HBox menu) {
        rightMenu.getChildren().clear();
        rightMenu.getChildren().add(menu);
    }

    public void setCenterMenu(HBox menu) {
        centerMenu.getChildren().clear();
        centerMenu.getChildren().add(menu);
    }

    public void clearMenus() {
        rightMenu.getChildren().clear();
        centerMenu.getChildren().clear();
    }
}
