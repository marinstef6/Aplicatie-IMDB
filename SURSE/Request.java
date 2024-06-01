package org.example;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//clasa request
public class Request {
    public RequestTypes type;
    private String to;
    private LocalDateTime createdDate;
    private String movieTitle;
    private String actorName;
    private String description;
    public String username;
    NotificationService notificationService = null;

    @JsonCreator
    public Request(@JsonProperty("type") RequestTypes type,
                   @JsonProperty("to") String to,
                   @JsonProperty("createdDate") String createdDate,
                   @JsonProperty("movieTitle") String movieTitle,
                   @JsonProperty("actorName") String actorName,
                   @JsonProperty("description") String description,
                   @JsonProperty("username") String username) {
        this.type = type;
        this.to = to;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        this.createdDate = LocalDateTime.parse(createdDate, formatter);
        this.movieTitle = movieTitle;
        this.actorName = actorName;
        this.description = description;
        this.username = username;
    }
    public String determineResolverUsername(RequestTypes requestType) {
        if (requestType == RequestTypes.DELETE_ACCOUNT || requestType == RequestTypes.OTHERS) {
            return "ADMIN";
        } else if (requestType == RequestTypes.ACTOR_ISSUE || requestType == RequestTypes.MOVIE_ISSUE) {
            return username;
        }
        return null;
    }
    // Getter pentru type
    public RequestTypes getType() {
        return type;
    }
    //Getteri
    public String getTo() {
        return to;
    }
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    public String getMovieTitle() {
        return movieTitle;
    }
    public String getDescription() {
        return description;
    }
    public String getUsername() {
        return username;
    }
    //Setteri
    public void setRequestType(RequestTypes requestType) {
        this.to = to;
    }
    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUserUsername(String userUsername) {
        this.username = username;
    }
    public void onRequestResolved(String requestDetails) {
        String message = "Your request '" + requestDetails + "' has been resolved.";
        notificationService.notifyUpdate(message);
    }
    public void onNewReviewAdded(String productionName, String reviewDetails) {
        String message = "A new review for '" + productionName + "': " + reviewDetails;
        notificationService.notifyUpdate(message);
    }

    //metoda toString pentru a afisa cererile

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Request{\n");
        sb.append("  type: ").append(type).append(",\n");
        sb.append("  createdDate: ").append(createdDate.toString()).append(",\n");
        sb.append("  username: ").append(username).append("\n");
        sb.append("  movieTitle: ").append(movieTitle).append(",\n");
        sb.append("  actorName: ").append(actorName).append(",\n");
        sb.append("  to: ").append(to).append(",\n");
        sb.append("  description: ").append(description).append(",\n");

        sb.append("}");
        return sb.toString();
    }
}
