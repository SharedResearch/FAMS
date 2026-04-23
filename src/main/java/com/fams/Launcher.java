package com.fams;

/**
 * External launcher is needed in order to shade the JAR.
 * Check out the following forum thread, and work on proper solution:
 * https://stackoverflow.com/questions/52653836/maven-shade-javafx-runtime-components-are-missing
 */
public class Launcher {
    public static void main(String... args) {
        new App().launchApp();
    }
}
