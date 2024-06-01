package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.SortedSet;

public class Admin<T extends Comparable<T>> extends Staff<T> {
    //liste pentru productiile si actorii pentru care sunt responsabili
    private List<String> productionsContribution;
    private List<String> actorsContribution;
    //liste pentru actorii si productiile care sunt la favorite
    private List<String> favoriteProductions;
    private List<String> favoriteActors;

    //constructor

    public Admin(@JsonProperty("username") String username,
                 @JsonProperty("experience") int experience,
                 @JsonProperty("information") Information information,
                 @JsonProperty("userType") AccountType userType,
                 @JsonProperty("productionsContribution") List<String> productionsContribution,
                 @JsonProperty("actorsContribution") List<String> actorsContribution,
                 @JsonProperty("favoriteProductions") List<String> favoriteProductions,
                 @JsonProperty("favoriteActors") List<String> favoriteActors) {
        super(username, experience, information, userType.Admin, productionsContribution,actorsContribution, favoriteProductions, favoriteActors);
        this.productionsContribution = productionsContribution;
        this.actorsContribution = actorsContribution;
        this.favoriteProductions = favoriteProductions;
        this.favoriteActors = favoriteActors;
    }

        //Getteri si setteri

    public List<String> getProductionsContribution() {
        return productionsContribution;
    }

    public void setProductionsContribution(List<String> productionsContribution) {
        this.productionsContribution = productionsContribution;
    }

    public List<String> getActorsContribution() {
        return actorsContribution;
    }

    public void setActorsContribution(List<String> actorsContribution) {
        this.actorsContribution = actorsContribution;
    }

    public List<String> getFavoriteProductions() {
        return favoriteProductions;
    }

    public void setFavoriteProductions(List<String> favoriteProductions) {
        this.favoriteProductions = favoriteProductions;
    }

    public List<String> getFavoriteActors() {
        return favoriteActors;
    }

    public void setFavoriteActors(List<String> favoriteActors) {
        this.favoriteActors = favoriteActors;
    }
}
