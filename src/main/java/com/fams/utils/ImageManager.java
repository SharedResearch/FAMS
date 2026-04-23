package com.fams.utils;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public enum ImageManager {
    INSTANCE;
    private final Map<String, Image> images = new HashMap<>();
    private String encodedMiunLogo = "";

    ImageManager() {
        List<String> fileNames = Arrays.asList(
                "binoculars", "box", "brain", "cross", "document", "eye", "exam", "fragments", "grade", "html5", "idea",
                "inspect", "inspect_code", "inspect_output", "lock", "menu", "miun_icon_small", "pdf", "pencil", "plus",
                "remove", "report_card", "save", "settings", "settings_2", "student", "students", "task", "text",
                "unlock", "update");

        fileNames.stream()
                .map(imageName -> new AbstractMap.SimpleEntry<>(imageName, String.format("/img/%s.png", imageName)))
                .forEach(entry -> Optional.ofNullable(getClass().getResource(entry.getValue()))
                        .ifPresentOrElse(
                                url -> images.put(entry.getKey(), new Image(url.toExternalForm())),
                                () -> System.out.println("Image file not found: " + entry.getValue())
                        ));

        // load the Miun logo
        try (InputStream inputStream = getClass().getResourceAsStream("/img/miun_logo.png")) {
            assert inputStream != null;
            byte[] imageBytes = inputStream.readAllBytes();
            encodedMiunLogo = Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException | AssertionError e) {
            e.printStackTrace();
        }
    }

    public ImageView getImage(String name) {
        Image img = images.get(name);
        ImageView imgv = new ImageView(img);
        imgv.setEffect(new DropShadow());
        return imgv;
        //return (img != null) ? new ImageView(img) : null;
    }

    public String getMiunLogoHTML() {
        return "<span>\n" +
                "  <img src=\"data:image/png;base64," + encodedMiunLogo + "\" width=\"60%\" style=\"text-align:center;display:block;margin-left:auto;margin-right:auto;margin-bottom:30px;\"></img>\n" +
                "</span>";
    }
}

