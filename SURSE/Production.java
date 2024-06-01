package org.example;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.awt.*;
import java.net.URI;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import static org.example.FlowApp.scanner;

@JsonSerialize(using = ProductionToStringSerializer.class)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Movie.class, name = "Movie"),
        @JsonSubTypes.Type(value = Series.class, name = "Series")})
//clasa Production
public abstract class Production implements Comparable<Production> {
    protected String title;
    protected ProductionType type;
    protected List<String> directors;
    protected List<String> actors;
    protected List<Genre> genres;
    protected List<Rating> ratings;
    protected String plot;
    protected Double averageRating;
    private Map<String, List<Episode>> seasons;

    //constructor
    @JsonCreator
    public Production(@JsonProperty("title") String title,
                      @JsonProperty("type") ProductionType type,
                      @JsonProperty("directors") List<String> directors,
                      @JsonProperty("actors") List<String> actors,
                      @JsonProperty("genres") List<Genre> genres,
                      @JsonProperty("ratings") List<Rating> ratings,
                      @JsonProperty("plot") String plot,
                      @JsonProperty("averageRating") Double averageRating) {
        this.title = title;
        this.type = type;
        this.directors = directors;
        this.actors = actors;
        this.genres = genres;
        this.ratings = ratings;
        this.plot = plot;
        this.averageRating = calculateRating(ratings);
        //calculateRating(ratings);
    }
    public Production()
    {

    }
    //metoda pentru a calcula rating-ul
    protected static double calculateRating(List<Rating> ratings){
        if(ratings == null || ratings.isEmpty()) {
            return 0.0;
        }
        double total = 0;
        for(Rating rating : ratings) {
            total += rating.getRating();
        }
        double average = total / ratings.size();
        DecimalFormat decimalFormat = new DecimalFormat("#,0"); // Format to 1 decimal place
        String formattedAverage = decimalFormat.format(average); // Formats the average
        return Double.parseDouble(formattedAverage); // Converts it back to double
    }

    // Getteri
    public String getTitle() {
        return title;
    }
    public String getPlot() {return plot;}
    public List<String> getDirectors() {
        return directors;
    }
    public List<String> getActors() {
        return actors;
    }
    public List<Genre> getGenres() {
        return genres;
    }
    public List<Rating> getRatings() {
        return ratings;
    }
    public ProductionType getType() {
        return type;
    }
    public Double getRating() {
        return averageRating;
    }

    //Setteri

    public void setTitle(String title) {
        this.title = title;
    }
    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }
    public void setActors(List<String> actors) {
        this.actors = actors;
    }
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
    public void setPlot(String plot) {
        this.plot = plot;
    }

    public void setRating(List<Rating> ratings) {
        this.ratings = ratings;
        this.averageRating = averageRating(ratings);
    }
    public void setType(ProductionType type) {

        this.type = type;
    }
    public abstract void displayInfo();

    //implementarea metodei compareTo pentru a compara dupa titlu

    public int compareTo(Production other) {

        return this.title.compareTo(other.title);
    }

    //metoda auxiliara pentru calcularea mediei ratingurilor
    protected Double averageRating(List<Rating> ratings) {
        if (ratings == null || ratings.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Rating rating : ratings) {
            sum += rating.getRating();
        }
        return sum / ratings.size();
    }

    //metoda pentru a adauga un rating
    public void addRating(Rating rating) {
        if (ratings == null) {
            ratings = new ArrayList<>();
        }
        ratings.add(rating);
        calculateRating(ratings);
    }
    public static void searchMovie(List<Production> productions, String movieName) {
        if (movieName == null || movieName.trim().isEmpty()) {
            System.out.println("Numele filmului nu poate fi null sau gol.");
            return;
        }

        String lowerCaseMovieName = movieName.toLowerCase().trim();
        boolean found = false;

        for (Production production : productions) {
            if (production.getTitle().toLowerCase().contains(lowerCaseMovieName)) {
                System.out.println(production);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Nu s-au gasit productii pentru filmul: " + movieName);
        }
    }
    public static void filterProductionsByGenre(List<Production> productions, String genre) {
        if (productions == null || productions.isEmpty()) {
            System.out.println("No productions to display.");
            return;
        }
        //filtrare productii in functie de gen
        List<Production> filteredProductions = productions.stream()
                .filter(production -> production.getGenres().stream()
                        .anyMatch(genre1 -> genre1.name().equalsIgnoreCase(genre)))
                .collect(Collectors.toList());

        if (filteredProductions.isEmpty()) {
            System.out.println("No productions found with genre " + genre);
        } else {
            for (Production production : filteredProductions) {
                System.out.println(production);
            }
        }
    }

    public static void filterProductionsByYear(List<Production> productions, int year) {
        if (productions == null || productions.isEmpty()) {
            System.out.println("No productions to display.");
            return;
        }
        List<Production> filteredProductions = productions.stream()
                .filter(production -> {
                    if (production instanceof Series) {
                        return ((Series) production).getReleaseYear() == year;
                    } else if (production instanceof Movie) {
                        return ((Movie) production).getReleaseYear() == year;
                    }
                    return false;
                })
                .toList();

        if (filteredProductions.isEmpty()) {
            System.out.println("No productions found for year " + year);
        } else {
            for (Production production : filteredProductions) {
                System.out.println(production);
            }
        }
    }
    public static void printProductions(List<Production> productions) {
        if (productions != null && !productions.isEmpty()) {
            for (Production production : productions) {
                System.out.println(production);
            }
        } else {
            System.out.println("Failed to parse productions from the file or the file is empty.");
        }
    }
    public static void sortHighProductionsByRating(List<Production> productions) {
        if (productions == null || productions.isEmpty()) {
            System.out.println("No productions to display.");
            return;
        }

        List<Production> sortedProductions = productions.stream()
                .sorted(Comparator.comparingDouble(Production::getRating))
                .collect(Collectors.toList());

        for (Production production : sortedProductions) {
            System.out.println(production);
        }
    }
    public static void sortProductionsByRating(List<Production> productions) {
        if (productions == null || productions.isEmpty()) {
            System.out.println("No productions to display.");
            return;
        }

        List<Production> sortedProductions = productions.stream()
                .sorted(Comparator.comparingDouble(Production::getRating).reversed())
                .toList();

        for (Production production : sortedProductions) {
            System.out.println(production);
        }
    }
    public static void sortProductionsChronologically(List<Production> productions) {
        if (productions == null || productions.isEmpty()) {
            System.out.println("No productions to sort.");
            return;
        }

        List<Production> sortedProductions = new ArrayList<>(productions);

        sortedProductions.sort(new Comparator<Production>() {
            @Override
            public int compare(Production p1, Production p2) {
                Integer year1 = getReleaseYear(p1);
                Integer year2 = getReleaseYear(p2);

                // Handle null years if any, assuming nulls last
                if (year1 == null) {
                    return (year2 == null) ? 0 : 1;
                }
                if (year2 == null) {
                    return -1;
                }

                return year1.compareTo(year2);
            }

            public Integer getReleaseYear(Production production) {
                if (production instanceof Series) {
                    return ((Series) production).getReleaseYear();
                } else if (production instanceof Movie) {
                    return ((Movie) production).getReleaseYear();
                }
                return null;
            }
        });

        for (Production production : sortedProductions) {
            System.out.println(production);
        }
    }
    public static void filterProductionsByActor(List<Production> productions, String actor) {
        if (productions == null || productions.isEmpty()) {
            System.out.println("No productions to display.");
            return;
        }

        List<Production> filteredProductions = productions.stream()
                .filter(production -> production.getActors().stream()
                        .anyMatch(actor1 -> actor1.equalsIgnoreCase(actor)))
                .toList();

        if (filteredProductions.isEmpty()) {
            System.out.println("No productions found with actor " + actor);
        } else {
            for (Production production : filteredProductions) {
                System.out.println(production);
            }
        }
    }
    public static void playTrailer(String title) {
        String trailerUrl = "";
        String titleLowerCase = title.toLowerCase();
        switch (titleLowerCase) {
            case "the shawshank redemption":
                trailerUrl = "https://www.youtube.com/watch?v=6hB3S9bIaco";
                break;
            case "breaking bad":
                trailerUrl = "https://www.youtube.com/watch?v=HhesaQXLuRY";
                break;
            case "pulp fiction":
                trailerUrl = "https://www.youtube.com/watch?v=s7EdQ4FqbhY";
                break;
            case "the godfather":
                trailerUrl = "https://www.youtube.com/watch?v=sY1S34973zA";
                break;
            case "schindler's list":
                trailerUrl = "https://www.youtube.com/watch?v=JdRGC-w9syA";
                break;
            case "the lord of the rings: the return of the king":
                trailerUrl = "https://www.youtube.com/watch?v=r5X-hFf6Bwo";
                break;
            case "the pianist":
                trailerUrl = "https://www.youtube.com/watch?v=u_jE7-6Uv7E";
                break;
            case "the matrix":
                trailerUrl = "https://www.youtube.com/watch?v=m8e-FF8MsqU";
                break;
            case "sherlock":
                trailerUrl = "https://www.youtube.com/watch?v=qlcWFoNqZHc";
                break;
            case "the dark knight":
                trailerUrl = "https://www.youtube.com/watch?v=EXeTwQWrcwY";
                break;
            case "inception":
                trailerUrl = "https://www.youtube.com/watch?v=YoHD9XEInc0";
                break;
            case "stranger things":
                trailerUrl = "https://www.youtube.com/watch?v=b9EkMc79ZSU&ab_channel=Netflix";
                break;
            case "forrest gump":
                trailerUrl = "https://www.youtube.com/watch?v=uPIEn0M8su0";
                break;
            case "the green mile":
                trailerUrl = "https://www.youtube.com/watch?v=Ki4haFrqSrw";
                break;
            case "inglourious basterds":
                trailerUrl = "https://www.youtube.com/watch?v=KnrRy6kSFF0";
                break;
            case "parasite":
                trailerUrl = "https://www.youtube.com/watch?v=5xH0HfJHsaY";
                break;
            case "joker":
                trailerUrl = "https://www.youtube.com/watch?v=zAGVQLHvwOY";
                break;
            case "1917":
                trailerUrl = "https://www.youtube.com/watch?v=YqNYrYUiMfg";
                break;
            case "the irishman":
                trailerUrl = "https://www.youtube.com/watch?v=WHXxVmeGQUc";
                break;
            case "the mandalorian":
                trailerUrl = "https://www.youtube.com/watch?v=aOC8E8z_ifw";
                break;
            case "the queen's gambit":
                trailerUrl = "https://www.youtube.com/watch?v=oZn3qSgmLqI&ab_channel=RottenTomatoesTV";
                break;
            case "squid game":
                trailerUrl = "https://www.youtube.com/watch?v=oqxAJKy0ii4&ab_channel=Netflix";
                break;
            case "whiplash":
                trailerUrl = "https://www.youtube.com/watch?v=7d_jQycdQGo";
                break;
            case "the social network":
                trailerUrl = "https://www.youtube.com/watch?v=lB95KLmpLR4";
                break;
            case "etenal sunshine of the spotless mind":
                trailerUrl = "https://www.youtube.com/watch?v=1GiLxkDK8sI";
                break;
            case "la la land":
                trailerUrl = "https://www.youtube.com/watch?v=0pdqf4P9MB8";
                break;
            case "the grand budapest hotel":
                trailerUrl = "https://www.youtube.com/watch?v=1Fg5iWmQjwk";
                break;
            case "the shape of water":
                trailerUrl = "https://www.youtube.com/watch?v=XFYWazblaUA";
                break;
            case "black swan":
                trailerUrl = "https://www.youtube.com/watch?v=5jaI1XOB-bs";
                break;
            case "the revenant":
                trailerUrl = "https://www.youtube.com/watch?v=LoebZZ8K5N0";
                break;
            case "mad max: fury road":
                trailerUrl = "https://www.youtube.com/watch?v=hEJnMQG9ev8";
                break;
            case "a beautiful mind":
                trailerUrl = "https://www.youtube.com/watch?v=aS_d0Ayjw4o";
                break;
            case "the departed":
                trailerUrl = "https://www.youtube.com/watch?v=iojhqm0JTW4";
                break;
            case "vasile versaci, cel mai bun pregratar gateste orez cu lapte!":
                trailerUrl = "https://www.youtube.com/watch?v=3FLmCTATy9o&ab_channel=%C8%98oimu%C8%99anDani";
                break;
            default:
                System.out.println("No trailer found for " + title);
                break;
        }

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(trailerUrl));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //metoda toString pentru a afisa productiile
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" title: '").append(title).append("\n");
        sb.append(" type: '").append(type).append("\n");
        sb.append(" directors: ").append(directors).append("\n");
        sb.append(" actors: ").append(actors).append("\n");
        sb.append(" genres: ").append(genres).append("\n");
        sb.append(" ratings: [");

        for (Rating rating : ratings) {
            sb.append(rating.toString()).append("\n");
        }

        if (!ratings.isEmpty()) {
            sb.setLength(sb.length() - 2);
        }

        sb.append("], ").append("\n");
        sb.append("plot: ").append(plot).append("\n");
        sb.append("averageRating: ").append(averageRating);

        if ("Movie".equals(type) && this instanceof Movie) {
            Movie movie = (Movie) this;
            sb.append(", Duration: ").append(movie.getDuration()).append(" minutes");
            sb.append(", Release Year: ").append(movie.getReleaseYear());
        } else if ("Series".equals(type) && this instanceof Series) {
            Series series = (Series) this;
            sb.append(", Seasons: ").append(series.getNumberOfSeasons());
            sb.append(", Seasons Details: {");

            for (String season : series.getSeasons().keySet()) {
                sb.append("\n\tSeason ").append(season).append(": ");
                List<Episode> episodes = series.getSeasons().get(season);
                for (Episode episode : episodes) {
                    sb.append(episode.toString()).append(", ");
                }
                if (!episodes.isEmpty()) {
                    sb.setLength(sb.length() - 2);
                }
                sb.append("\n");
            }

            sb.append("}").append("\n");

        }
        sb.append("}");
        return sb.toString();
    }

    public Map<String,List<Episode>> getSeasons() {
        return seasons;
    }

    public void setSeasons(Map<String,List<Episode>> seasons) {
        this.seasons = seasons;
    }
}

