package org.example;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;
import java.util.stream.Collectors;

public class Actor implements Comparable<Actor> {
    private static ProductionType type;
    public String name;
    //titlu filme si tipul lor(Movie sau Series)
    private List<ProductionRole> performances;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String biography;

    @JsonCreator
    public Actor(@JsonProperty("name") String name,
                 @JsonProperty("performances") List<ProductionRole> performances,
                 @JsonProperty("biography") String biography) {
        this.name = name;
        this.performances = performances;
        if (this.performances == null)
            this.performances = new ArrayList<>();
        this.biography = biography;
    }

    public void addRole(String productionName, String type) {
        performances.add(new ProductionRole(productionName, type));
    }

    // Getteri și Setteri
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductionRole> getPerformances() {
        return performances;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public int compareTo(Actor other) {
        return this.name.compareTo(other.name);
    }
    List<Actor> actors = new ArrayList<>();

    // Clasa interna ProductionRole pentru a reprezenta rolurile
    protected static class ProductionRole {
        private String title;
        private String type; // "Movie" sau "Series"

        public ProductionRole(@JsonProperty("title") String productionName,
                              @JsonProperty("type") String type) {
            this.title = productionName;
            this.type = type;
        }
    // afisare performante
        @Override
        public String toString() {
            return title + " (" + type + ")";
        }

        public String getTitle() {
            return title;
        }
        public String getType() { return type;}
        public void setType(String type) {this.type = type;}

        public void setTitle(String title) {
            this.title = title;
        }
    }
    //metoda pentru afisarea actorilor sortati
    public static void displaySortedActors(List<Actor> actors) {
        Collections.sort(actors);

        for (Actor actor : actors) {
            System.out.println(actor.toString());
        }
    }
    //afisare actori
    public static void printActors(List<Actor> actors)
    {
        for(Actor actor : actors)
            System.out.println(actor);
    }
    //cautare actori dupa nume
    public static void searchActorByName(List<Actor> actors, String nameToSearch) {
        boolean found = false;
        for (Actor actor : actors) {
            if (actor.getName().equalsIgnoreCase(nameToSearch)) {
                System.out.println(actor.toString());
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Actor not found: " + nameToSearch);
        }
    }
    //filtrare actori dupa un cuvant specific din biografie
    public static void filterActorsByBiography(List<Actor> actors, String keyword) {
        if (keyword == null) {
            System.out.println("Cuvântul cheie nu poate fi null.");
            return;
        }

        String lowerCaseKeyword = keyword.toLowerCase();

        for (Actor actor : actors) {
            if (actor.getBiography() != null && actor.getBiography().toLowerCase().contains(lowerCaseKeyword)) {
                System.out.println(actor);
            }
        }
    }
    //cautare actor dupa un cuvant specific din biografie pentru type(Movie sau Series)
    public static void searchActorByType(List<Actor> actors, String key) {
        if (key == null) {
            System.out.println("Tipul nu poate fi null.");
            return;
        }
        String lowerCaseType = key.toLowerCase();

        for (Actor actor : actors) {
            for (ProductionRole role : actor.getPerformances()) {
                if (role.type.toLowerCase().contains(lowerCaseType)) {
                    System.out.println(actor);
                    break;
                }
            }
        }
    }
    //cautare actori dupa film
    public static void searchActorByMovie(List<Actor> actors, String movieName) {
        if (movieName == null) {
            System.out.println("Numele filmului nu poate fi null sau gol.");
            return;
        }
        String lowerCaseMovieName = movieName.toLowerCase();
        boolean found = false; // Track if any actor is found for the given movie

        for (Actor actor : actors) {
            for (ProductionRole role : actor.getPerformances()) {
                if (role.title.toLowerCase().contains(lowerCaseMovieName)) {
                    System.out.println(actor); // Print actor's details
                    found = true;
                    // Do not break here as we want to find all actors for this movie
                }
            }
        }

        if (!found) {
            System.out.println("Nu s-au gasit actori pentru filmul: " + movieName);
        }
    }
    // metoda toString pentru a reprezenta actorul
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append("\n");
        sb.append(" Performances: ").append("\n");
        for (ProductionRole role : performances) {
            sb.append(role.toString()).append("\n");
        }
        sb.append("\n");
        // elimina ultimele doua caractere (", ") daca lista de roluri nu este goala
        if (!performances.isEmpty()) {
            // elimina ultimele doua caractere
            sb.setLength(sb.length() - 2);
        }
        sb.append("}");
        sb.append("\n");
        sb.append(" Biography: ").append(biography);
        sb.append("}").append("\n");
        sb.append("\n");
        sb.append("**********");
        sb.append("\n");
        return sb.toString();
    }
}
