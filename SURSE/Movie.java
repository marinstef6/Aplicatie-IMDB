package org.example;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
//clasa pentru tipul productiei (filme)
public class Movie extends Production {
    //durata filmului in minute
    private String duration;
    //descrierea filmului
    private String plot;
    //anul lansarii filmului
    private Integer releaseYear;

    //constructor
    @JsonCreator
    public Movie(@JsonProperty("title") String title,
                 @JsonProperty("type") ProductionType type,
                 @JsonProperty("directors") List<String> directors,
                 @JsonProperty("actors") List<String> actors,
                 @JsonProperty("genres") List<Genre> genres,
                 @JsonProperty("ratings") List<Rating> ratings,
                 @JsonProperty("duration") String duration,
                 @JsonProperty("plot") String plot,
                 @JsonProperty("releaseYear") int releaseYear,
                 @JsonProperty("averageRating") double averageRating) {
        super(title, ProductionType.Movie, directors, actors, genres, ratings, plot, averageRating);
        this.releaseYear = releaseYear;
        this.duration = duration;
    }

    // Getteri
    public String getDuration() {

        return duration;
    }
    public String getPlot() {

        return plot;
    }
    public int getReleaseYear() {

        return releaseYear;
    }

    //Setteri
    public void setDuration(String duration) {

        this.duration = duration;
    }
    public void setPlot(String plot) {

        this.plot = plot;
    }

    public void setReleaseYear(int releaseYear) {

        this.releaseYear = releaseYear;
    }

    //implementarea metodei abstracte displayInfo
    @Override
    public void displayInfo() {
    }
    //metoda toString pentru afisare
    public String toString() {
        return super.toString() + "\n" +
                "duration: " + duration +"\n" +
                "releaseYear: " + releaseYear + "\n" +
                "*********" + "\n";
    }
}
