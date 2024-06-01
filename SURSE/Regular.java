package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.SortedSet;
//clasa regular(un tip de al userului)
public class Regular<T extends Comparable<T>> extends User<T> implements RequestsManager {
    private List<String> productionsContribution;
    private List<String> actorsContribution;
    private List<String> favoriteProductions;
    private List<String> favoriteActors;
    public Regular(@JsonProperty("username") String username,
                   @JsonProperty("experience") int experience,
                   @JsonProperty("information") Information information,
                   @JsonProperty("userType") AccountType userType,
                   @JsonProperty("productionsContribution") List<String> productionsContribution,
                   @JsonProperty("actorsContribution") List<String> actorsContribution,
                   @JsonProperty("favoriteProductions") List<String> favoriteProductions,
                   @JsonProperty("favoriteActors") List<String> favoriteActors) {
        super(username, experience, information, userType.Regular, productionsContribution,actorsContribution, favoriteProductions, favoriteActors);
        this.productionsContribution = productionsContribution;
        this.actorsContribution = actorsContribution;
        this.favoriteProductions = favoriteProductions;
        this.favoriteActors = favoriteActors;
    }
    //getteri si setteri
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

    @Override
    public void createRequest(Request r) {
    }

    @Override
    public void removeRequest(Request r) {
    }
}
