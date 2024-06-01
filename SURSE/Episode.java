package org.example;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Episode {
    private String episodeName;
    private String duration;
    //constructor
    @JsonCreator
    public Episode(@JsonProperty("episodeName") String episodeName,
                   @JsonProperty("duration") String duration) {
        this.episodeName = episodeName;
        this.duration = duration;
    }

    // Getteri si Setteri
    public String getEpisodeName() {

        return episodeName;
    }

    public String getDuration() {

        return duration;
    }

    public void setEpisodeName(String episodeName) {

        this.episodeName = episodeName;
    }

    public void setDuration(String duration) {

        this.duration = duration;
    }
    // metoda pentru a afișa informațiile episodului
    public void displayInfo() {
    }

    public String toString() {
        return "\n      {\n        \"episodeName\": \"" + episodeName + "\",\n" +
                "        \"duration\": \"" + duration + " minutes\"\n      }";
    }
}
