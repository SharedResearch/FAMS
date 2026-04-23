package com.fams.model;

import com.fams.utils.AppConfig;
import com.fams.utils.FragmentsManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fragment {
    private final String fragmentIdentifier;
    private String content;

    private String course;

    private String assignment;

    private boolean isComposite;

    public Fragment(String identifier, String content, String course, String assignment) {
        this.fragmentIdentifier = identifier;
        this.content = content;
        this.course = course;
        this.assignment = assignment;
        isComposite = content.contains(AppConfig.FRAGMENT_SYMBOL_COMPOSITE);
    }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public String getAssignment() { return assignment; }
    public void setAssignment(String assignment) { this.assignment = assignment; }

    public String getFragmentIdentifier() {
        return fragmentIdentifier;
    }

    public String getContent() {
        return content;
    }

    /*
    public String getCompositeContent() {
        if (!isComposite)
            return getContent();

        Pattern pattern = Pattern.compile(AppConfig.FRAGMENT_SYMBOL_COMPOSITE + "[A-Z_0-9]+");
        Matcher matcher = pattern.matcher(content);

        Map<String, String> identifierContentMap = new TreeMap<>(
                Comparator.comparingInt(String::length)
                        .reversed()
                        .thenComparing(Comparator.naturalOrder())
        );

        while (matcher.find()) {
            String id = matcher.group(0).trim();
            identifierContentMap.put(id,
                    FragmentsManager.INSTANCE.getFragmentById(id.replace(AppConfig.FRAGMENT_SYMBOL_COMPOSITE,
                            AppConfig.FRAGMENT_SYMBOL_MAIN)).getContent());
        }
        return replaceIdentifiersWithContent(content, identifierContentMap);
    }*/


    public String getCompositeContent() {
        if (!isComposite)
            return getContent();

        Pattern pattern = Pattern.compile(AppConfig.FRAGMENT_SYMBOL_COMPOSITE + "[A-Z_0-9]+");
        Matcher matcher = pattern.matcher(content);

        Map<String, String> identifierContentMap = new HashMap<>();

        while (matcher.find()) {
            String id = matcher.group(0).trim();
            identifierContentMap.put(id,
                    FragmentsManager.INSTANCE.getFragmentById(id.replace(AppConfig.FRAGMENT_SYMBOL_COMPOSITE,
                            AppConfig.FRAGMENT_SYMBOL_MAIN)).getContent());
        }
        return replaceIdentifiersWithContent(content, identifierContentMap);
    }

    private String replaceIdentifiersWithContent(String baseContent, Map<String, String> identifierContentMap) {
        String replacedContent = baseContent;

        // make sure longer symbols gets replaced before shorter, so that they
        // won't overwrite if they share base name.
        List<String> keys = new ArrayList<>(identifierContentMap.keySet());
        keys.sort(Comparator.comparingInt(String::length).reversed());

        for (String key : keys) {
            replacedContent = replacedContent.replace(key, identifierContentMap.get(key));
        }

        return replacedContent;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

