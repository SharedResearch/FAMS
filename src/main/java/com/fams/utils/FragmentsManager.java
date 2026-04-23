package com.fams.utils;

import com.fams.model.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum FragmentsManager {
    INSTANCE;
    private final Map<String, Fragment> fragments = new HashMap<>();

    FragmentsManager() {
        for (Fragment fragment : FeedbackDBUtil.INSTANCE.getAllFragments()) {
            fragments.put(fragment.getFragmentIdentifier(), fragment);
        }
    }

    public Fragment getFragmentById(String id) {
        return fragments.get(id);
    }
    /*
    public Fragment getFragmentBySerialNumber(int serialNumber) {
        return fragments.get(serialNumber);
    }*/

    /*
    public List<String> getAllFragmentIds() {
        return new ArrayList<>(fragments.keySet());
    }*/

    public List<String> getAllFragmentIdsOfType(String substring) {
        return fragments.keySet().stream()
                .filter(key -> key.contains(substring))
                .collect(Collectors.toList());
    }

    public List<String> getAllFragmentIDs() {
        return new ArrayList<>(fragments.keySet());
    }

    public List<Fragment> getAllFragments() {
        return new ArrayList<>(fragments.values());
    }

    public void updateFragment(Fragment fragment) {
        fragments.put(fragment.getFragmentIdentifier(), fragment);
        if (!FeedbackDBUtil.INSTANCE.updateFragment(fragment)) {
            System.out.println("Failed to update record!");
        }
    }

    public Fragment createNewFragment(String id, String course, String assignment) {
        String newID = AppConfig.FRAGMENT_SYMBOL_MAIN + id;
        if (!FeedbackDBUtil.INSTANCE.insertNewFragment(newID, course, assignment)) {
            return null;
        }

        Fragment fragment = new Fragment(newID, "", course, assignment);
        fragments.put(newID, fragment);
        return fragment;
    }

    public boolean deleteFragment(String id) {
        boolean success = FeedbackDBUtil.INSTANCE.removeFragment(id);
        if (success) {
            fragments.remove(id);
        }
        return success;
    }

    public List<Fragment> getFilteredFragments(String course, String assignment) {
        return fragments.values()
                .stream()
                .filter(fragment -> fragment.getCourse().equals(course) && fragment.getAssignment().equals(assignment))
                .collect(Collectors.toList());
    }

    public List<String> getFilterFragmentIDs(String course, String assignment) {
        return fragments.values()
                .stream()
                .filter(fragment -> fragment.getCourse().equals(course) && fragment.getAssignment().equals(assignment))
                .map(Fragment::getFragmentIdentifier)
                .collect(Collectors.toList());
    }

    public boolean checkIfFragmentExist(String fragmentID) {
        return fragments.containsKey(fragmentID);
    }
}
