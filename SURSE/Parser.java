package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

//parsarea fisierelor json
public class Parser {
    //folosirea obiectului objectMapper pentru tranformarea datelor json in liste
    public static ObjectMapper objectMapper = new ObjectMapper();


    public static List<Actor> parseActors(String filePath) {
        try {
            return objectMapper.readValue(Paths.get(filePath).toFile(), new TypeReference<List<Actor>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Production> parseProductions(String filePath) {
        try {
            return objectMapper.readValue(Paths.get(filePath).toFile(), objectMapper.getTypeFactory().constructCollectionType(List.class, Production.class));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Request> parseRequests(String filePath) {
        try {
            return objectMapper.readValue(Paths.get(filePath).toFile(), new TypeReference<List<Request>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<User> parseUsers(String filePath) {
        try {
            return objectMapper.readValue(Paths.get(filePath).toFile(),objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
