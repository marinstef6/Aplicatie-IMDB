package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

//clasa staff
public abstract class Staff<T extends Comparable<T>> extends User<T> implements StaffInterface {
    protected List<Request> personalRequests;
    protected SortedSet<T> managedItems;
    public Staff(@JsonProperty("username") String username,
                 @JsonProperty("experience") int experience,
                 @JsonProperty("information") Information information,
                 @JsonProperty("userType") AccountType userType,
                 @JsonProperty("productionsContribution") List<String> productionsContribution,
                 @JsonProperty("actorsContribution") List<String> actorsContribution,
                 @JsonProperty("favoriteProductions") List<String> favoriteProductions,
                 @JsonProperty("favoriteActors") List<String> favoriteActors) {
        super(username, experience, information, userType, productionsContribution,actorsContribution, favoriteProductions, favoriteActors);
        this.personalRequests = new ArrayList<>();
        this.managedItems = new TreeSet<>();
    }

    //functii pentru sistem care tin de actori si productii
    //implementare in Flow
    @Override
    public void addProductionSystem(Production p) {
    }

    @Override
    public void addActorSystem(Actor a) {
    }

    @Override
    public void removeProductionSystem(String name) {
    }

    @Override
    public void removeActorSystem(String name) {
    }

    public void updateProduction(Production p) {
    }
    public void updateActor(Actor a) {
    }

    @Override
    public void resolveRequests() {
    }

    //Getteri si Setteri
    public List<Request> getPersonalRequests() {
        return personalRequests;
    }

    public void setPersonalRequests(List<Request> personalRequests) {
        this.personalRequests = personalRequests;
    }

    public SortedSet<T> getManagedItems() {
        return managedItems;
    }

    public void setManagedItems(SortedSet<T> managedItems) {
        this.managedItems = managedItems;
    }

}


