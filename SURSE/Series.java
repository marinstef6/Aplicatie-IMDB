package org.example;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//clasa pentru un latip de productie(serial)
public class Series extends Production {
    //Integer pentru a permite null
    private String plot;
    private Integer releaseYear;
    @JsonProperty("numSeasons")
    private Integer numberOfSeasons;
    private Map<String, List<Episode>> seasons;

    @JsonCreator
    public Series(@JsonProperty("title") String title,
                  @JsonProperty("type") ProductionType type,
                  @JsonProperty("directors") List<String> directors,
                  @JsonProperty("actors") List<String> actors,
                  @JsonProperty("genres") List<Genre> genres,
                  @JsonProperty("ratings") List<Rating> ratings,
                  @JsonProperty("releaseYear") int releaseYear,
                  @JsonProperty("plot") String plot,
                  @JsonProperty("averageRating") double averageRating,
                  @JsonProperty("numSeasons") int numberOfSeasons,
                  @JsonProperty("seasons") Map<String,List<Episode>> seasons) {
        super(title, ProductionType.Series, directors, actors, genres, ratings,plot, averageRating);
        this.plot = plot;
        this.releaseYear = releaseYear;
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
    }

    //Getteri
    public int getReleaseYear() {
        return releaseYear;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public Map<String, List<Episode>> getSeasons() {
        return seasons;
    }

    //Setteri
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public void setSeasons(Map<String, List<Episode>> seasons) {
        this.seasons = seasons;
    }

    //Metoda afisare informatii series
    public void displayInfo() {

 }
 //metoda toString pentru a afisa
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("\n");
        sb.append("releaseYear: ").append(releaseYear).append("\n");
        sb.append("numSeasons: ").append(numberOfSeasons).append("\n");

        if (seasons != null && !seasons.isEmpty()) {
            sb.append(", Seasons: {");
            for (Map.Entry<String, List<Episode>> seasonEntry : seasons.entrySet()) {
                sb.append("").append(seasonEntry.getKey()).append(": [");
                List<String> episodeDescriptions = seasonEntry.getValue().stream()
                        .map(Episode::toString)
                        .collect(Collectors.toList());
                sb.append(String.join(", ", episodeDescriptions));
                sb.append("], ");
            }
            sb.delete(sb.length() - 2, sb.length());
            sb.append("}");
        }
        return sb.toString();
    }
}
