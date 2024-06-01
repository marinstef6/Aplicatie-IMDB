package org.example;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.example.Movie;
import org.example.Production;
import org.example.Rating;
import org.example.Series;

import java.io.IOException;

//clasa pentru a serializa production
public class ProductionToStringSerializer extends JsonSerializer<Production> {
    @Override
    public void serialize(Production value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String serializedString = convertProductionToString(value);
        gen.writeString(serializedString);
    }

    //convertire
    private String convertProductionToString(Production production) {
        StringBuilder sb = new StringBuilder();
        sb.append("Title: ").append(production.getTitle()).append("\n");
        sb.append("Type: ").append(production.getType()).append("\n");
        sb.append("Directors: ").append(production.getDirectors()).append("\n");
        sb.append("Actors: ").append(production.getActors()).append("\n");
        sb.append("Genres: ").append(production.getGenres()).append("\n");
        sb.append("Ratings: [");

        for (Rating rating : production.getRatings()) {
            sb.append(rating.toString()).append(", ");
        }
        if (!production.getRatings().isEmpty()) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("]\n");
        sb.append("Plot: ").append(production.getPlot()).append("\n");
        sb.append("Average Rating: ").append(production.getRating()).append("\n");

        if (production instanceof Movie) {
            Movie movie = (Movie) production;
            sb.append("Duration: ").append(movie.getDuration()).append(" minutes\n");
            sb.append("Release Year: ").append(movie.getReleaseYear()).append("\n");
        } else if (production instanceof Series) {
            Series series = (Series) production;
            sb.append("Number of Seasons: ").append(series.getNumberOfSeasons()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}