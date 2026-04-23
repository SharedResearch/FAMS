package com.fams.utils;

import com.fams.App;
import com.fams.model.Component;
import com.fams.model.Protocol;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ProtocolManager {
    INSTANCE;

    private String course = AppConfig.COURSE_NAME;  // Assuming AppConfig.COURSE_NAME is a constant string for course name.
    private Map<String, Protocol> protocols = new HashMap<>();

    ProtocolManager() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Read the entire JSON file
            /*
            String basePath = AppConfig.getBasePathForJar(App.class);
            Path jsonPath = Paths.get(basePath, AppConfig.COURSE_NAME + ".json");
            String jsonString = new String(Files.readAllBytes(jsonPath));*/

            InputStream jsonStream = ProtocolManager.class.getResourceAsStream("/DT180G.json");
            String jsonString = new String(jsonStream.readAllBytes());

            // Convert JSON to a map structure
            Map<String, Map<String, Object>> rawProtocols = mapper.readValue(jsonString, HashMap.class);

            for (Map.Entry<String, Map<String, Object>> entry : rawProtocols.entrySet()) {
                String protocolName = entry.getKey();
                List<String> categories = (List<String>) entry.getValue().get("Categories");

                // Convert the component list
                CollectionType listType = TypeFactory.defaultInstance().constructCollectionType(List.class, Component.class);
                List<Component> components = mapper.convertValue(entry.getValue().get("Components"), listType);

                protocols.put(protocolName, new Protocol(protocolName, categories, components));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Protocol getProtocol(String protocolName) {
        return protocols.get(protocolName);
    }

    public Map<String, Protocol> getAllProtocols() {
        return protocols;
    }

    public void addProtocol(String name, Protocol protocol) {
        protocols.put(name, protocol);
    }

    public void removeProtocol(String name) {
        protocols.remove(name);
    }

    public List<String> getProtocolNames() {
        return new ArrayList<>(protocols.keySet());
    }

    public void updateProtocol(Protocol protocol) {
        protocols.put(protocol.getProtocolName(), protocol);
    }

    private void saveProtocolsToFile() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);  // pretty print

        try {
            Path outputPath = Paths.get(course + ".json");
            mapper.writeValue(outputPath.toFile(), protocols);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
