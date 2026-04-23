package com.fams.utils;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AppConfig {
    private AppConfig() { throw new IllegalStateException("Utility class"); }

    public static final String COURSE_NAME = "DT180G";
    public static final String COMPONENT_ASPECT = "Aspect";
    public static final String COMPONENT_HEADING = "Heading";
    public static final String COMPONENT_STATIC_CONTENT = "Static Content";

    public static final String NAVIGATION_GRADING_VIEW = "Grading Management";
    public static final String NAVIGATION_FEEDBACK_VIEW = "Feedback Management";
    public static final String NAVIGATION_PROTOCOL_VIEW = "Protocol Management";

    public static final String GRADING_VIEW_PROTOCOL = "Protocol";
    public static final String GRADING_VIEW_OUTPUT = "Output";
    public static final String GRADING_VIEW_SOURCE = "Source";

    public static final String FRAGMENT_SYMBOL_MAIN = "@@@_";
    public static final String FRAGMENT_SYMBOL_COMPOSITE = "&&&_";

    public static Map<String, List<String>> getCourseDetails() {
        Map<String, List<String>> courses = new HashMap<>();
        courses.put("DT180G", Arrays.asList("Laboration 1", "Laboration 2", "Laboration 3", "Project", "Report", "Misc"));
        return courses;
    }

    public static String getBasePathForJar(Class<?> classs) {
        URL jarLocation;
        jarLocation = classs.getProtectionDomain().getCodeSource().getLocation();

        // Check if the class is in a JAR file (i.e., it's being run from a JAR)
        if (jarLocation.getFile().endsWith(".jar")) {
            //return new File(jarLocation.getFile()).getParentFile().getPath();
            return new File(jarLocation.getFile()).getParentFile().getAbsolutePath();
        } else {
            // Check for classes directory (typical for IDE)
            String path = jarLocation.getFile().replace("target/classes/", "");
            if (path.endsWith("classes/")) {
                path = path.replace("classes/", "");
            }
            return path;
        }
    }

}
