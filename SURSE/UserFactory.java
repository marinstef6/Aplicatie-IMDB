package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

//functie pentru a crea un user
public class UserFactory {
    public static User createUser(@JsonProperty("username") String username,
                                  @JsonProperty("experience") int experience,
                                  @JsonProperty("information") User.Information information,
                                  @JsonProperty("userType") AccountType userType,
                                  @JsonProperty("productionsContribution") List<String> productionsContribution,
                                  @JsonProperty("actorsContribution") List<String> actorsContribution,
                                  @JsonProperty("favoriteProductions") List<String> favoriteProductions,
                                  @JsonProperty("favoriteActors") List<String> favoriteActors) {
        switch (userType) {
            case Regular:
                return new Regular<String>(username, experience, information, userType.Regular, productionsContribution,actorsContribution, favoriteProductions, favoriteActors);
            case Contributor:
                return new Contributor<String>(username, experience, information, userType.Contributor, productionsContribution,actorsContribution, favoriteProductions, favoriteActors);
            case Admin:
                return new Admin<String>(username, experience, information, userType.Admin, productionsContribution,actorsContribution, favoriteProductions, favoriteActors);
            default:
                throw new IllegalArgumentException("Unknown account type: " + userType);
        }
    }
}
