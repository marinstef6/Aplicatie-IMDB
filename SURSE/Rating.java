package org.example;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

//clasa rating
public class Rating {
    private String username;
    private int rating;
    private String comment;

    @JsonCreator
    public Rating(@JsonProperty("username") String username,
                  @JsonProperty("rating") int rating,
                  @JsonProperty("comment") String comment) {
        this.username = username;
        this.rating = rating;
        this.comment = comment;
    }
    //Getteri
    public String getUsername() {
        return username;
    }
    public int getRating() {
        return rating;
    }
    public String getComment() {
        return comment;
    }
    //Setteri
    public void setUsername(String username) {
        this.username = username;
    }
    public void setRating(int rating) {
        this.rating = Math.max(1, Math.min(rating, 10));
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    //Metoda afisare rating
    @Override
    public String toString() {
        return "{" +
                "username='" + username + '\'' +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }


}
