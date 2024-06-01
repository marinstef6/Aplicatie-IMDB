package org.example;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

//clasa User
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "userType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Regular.class, name = "Regular"),
        @JsonSubTypes.Type(value = Contributor.class, name = "Contributor"),
        @JsonSubTypes.Type(value = Admin.class, name = "Admin")
})
public abstract class User<T extends Comparable<T>> {
    //clasa Information unde am informatii despre useri
    public static class Information {
        private Credentials credentials;
        String name;
        String country;
        int age;
        String gender;
        LocalDate birthDate;

        //constrcutor
        public Information(@JsonProperty("credentials") Credentials credentials,
                           @JsonProperty("name") String name,
                           @JsonProperty("country") String country,
                           @JsonProperty("age") int age,
                           @JsonProperty("gender") String gender,
                           @JsonProperty("birthDate") String birthDate) {
            this.credentials = credentials;
            this.name = name;
            this.country = country;
            this.age = age;
            this.gender = gender;
            if (birthDate != null && birthDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                this.birthDate = LocalDate.parse(birthDate, formatter);
            } else {
                throw new IllegalArgumentException("Invalid or null birth date provided: " + birthDate);
            }
        }
        //getteri si setteri
        public Credentials getCredentials() {
            return this.credentials;
        }

        public Credentials setCredentials(Credentials credentials) {
            this.credentials = credentials;
            return this.credentials;
        }
        //metoda toString pentru afisare informatii
        @Override
        public String toString() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = (birthDate != null) ? birthDate.format(formatter) : "N/A";

            return String.format("Information{name='%s', country='%s', age=%d, gender=%s, birthDate=%s}",
                    name, country, age, gender, formattedDate);
        }


    }

    public String username;
    public int experience;
    public AccountType userType;
    public Information userInformation;
    public List<String> notifications;
    public List<String> favoriteProductions;
    public List<String> productionsContribution;
    public List<String> actorsContribution;
    public List<String> favoriteActors;

    //constructor
    public User(@JsonProperty("username") String username,
                @JsonProperty("experience") int experience,
                @JsonProperty("information") Information information,
                @JsonProperty("userType") AccountType userType,
                @JsonProperty("productionsContribution") List<String> productionsContribution,
                @JsonProperty("actorsContribution") List<String> actorsContribution,
                @JsonProperty("favoriteProductions") List<String> favoriteProductions,
                @JsonProperty("favoriteActors") List<String> favoriteActors){
        this.userInformation = information;
        this.userType = userType;
        this.username = username;
        this.experience = experience;
        this.notifications = notifications;
        this.productionsContribution = productionsContribution;
        this.actorsContribution = actorsContribution;
        this.favoriteProductions = favoriteProductions;
        this.favoriteActors = favoriteActors;
    }
    //getteri si setteri
    public AccountType getUserType() {
        return userType;
    }
    public void setUserType(AccountType userType) {

        this.userType = userType;
    }

    //metoda pentru experienta
    private ExperienceStrategy experienceStrategy;

    public void setExperienceStrategy(ExperienceStrategy strategy) {
        this.experienceStrategy = strategy;
    }

    //calculare experienta in functie de actiunea pe care o face
    public int performActionAndGetExperience() {

        return experienceStrategy.calculateExperience();
    }

    public void performActionAndUpdateExperience(ExperienceStrategy strategy) {
        int experienceGained = strategy.calculateExperience();
        updateExperience(experienceGained);
    }
    //vizualizare notificari
    public void viewNotifications() {
        if (notifications == null || notifications.isEmpty()) {
            System.out.println("No notifications.");
            return;
        }
    }
    //adaugare notificari
    public void addNotification(String notification) {
        if (notifications == null) {
            notifications = new ArrayList<>();
        }
        notifications.add(notification);
    }
    //getteri si setteri pentru listele de la favorite
    public List<String> getNotifications(){
        return notifications;
    }

    //returnarea datelor din favorite fara sa modificam ceva sau sa returnam null daca este goala
    public List<String> getFavoriteProductions() {
        return (this.favoriteProductions != null) ? Collections.unmodifiableList(this.favoriteProductions) : Collections.emptyList();
    }

    public void setFavoriteProductions(List<String> favoriteProductions) {
        if (favoriteProductions == null) {
            this.favoriteProductions = new ArrayList<>();
        } else {
            this.favoriteProductions = favoriteProductions;
        }
    }
    public List<String> getFavoriteActors() {
        return (this.favoriteActors != null) ? Collections.unmodifiableList(this.favoriteActors) : Collections.emptyList();
    }
    public List<String> getActorsContribution() {
        return (this.actorsContribution!= null) ? Collections.unmodifiableList(this.actorsContribution) : Collections.emptyList();
    }
    public List<String> getProductionsContributions() {
        return (this.productionsContribution != null) ? Collections.unmodifiableList(this.productionsContribution) : Collections.emptyList();
    }
    public void setActorsContribution(List<String> actorsContribution) {
        if (actorsContribution == null) {
            this.actorsContribution = new ArrayList<>();
        } else {
            this.actorsContribution = actorsContribution;
        }
    }
    public void setProductionsContribution(List<String> productionsContribution) {
        if (productionsContribution == null) {
            this.productionsContribution = new ArrayList<>();
        } else {
            this.productionsContribution = productionsContribution;
        }
    }
    public void setFavoriteActors(List<String> favoriteActors) {
        if (favoriteActors == null) {
            this.favoriteActors = new ArrayList<>();
        } else {
            this.favoriteActors = favoriteActors;
        }
    }

    public String getUsername() {
        return this.username;
    }
    Information credentials;
    //metoda toString pentru afisare
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User{ \n");
        sb.append(" username: '").append(username).append("',\n");
        sb.append(" experience: ").append(experience).append(",\n");
        sb.append(" Information: ").append(userInformation != null ? userInformation.toString() : "No Information").append(",\n");
        sb.append(" credentials: ").append(userInformation != null ? userInformation.getCredentials() : "No Credentials").append(",\n");
        sb.append(" userType: '").append(userType).append("',\n");
        sb.append(" ProductionsContribution: [").append(productionsContribution != null ? String.join(", ", productionsContribution) : "").append("],\n");
        sb.append(" ActorsContribution: [").append(actorsContribution != null ? String.join(", ", actorsContribution) : "").append("],\n");
        sb.append(" favoriteProductions: [").append(favoriteProductions != null ? String.join(", ", favoriteProductions) : "").append("],\n");
        sb.append(" favoriteActors: [").append(favoriteActors != null ? String.join(", ", favoriteActors) : "").append("],\n");
        sb.append(" notifications: [").append(notifications != null ? String.join(", ", notifications) : "").append("]\n");
        sb.append("}"); // End of User object
        return sb.toString();
    }

    //functie de comparare pentru productii
    private class nameComparator implements Comparator<Comparable> {
        @Override
        public int compare(Comparable o1, Comparable o2) {
            String name1 = getName(o1);
            String name2 = getName(o2);
            return name1.compareTo(name2);
        }

        private String getName(Comparable obj) {
            if (obj instanceof Actor) {
                return ((Actor) obj).name;
            } else if (obj instanceof Production) {
                return ((Production) obj).title;
            }
            throw new IllegalArgumentException("Object is not an Actor or Production");
        }
    }

    //actualiare experienta
    public void updateExperience(int experience) {
        this.experience += experience;
        if (this.experience > 100) {
            this.experience = 100;
        }
    }

    //functie pentru delogare
    public void logout() {
        if (this.userInformation != null) {
            this.userInformation.setCredentials(null);
        }
        //curatare liste
        this.notifications.clear();
        this.favoriteProductions.clear();
        this.favoriteActors.clear();
    }
}
