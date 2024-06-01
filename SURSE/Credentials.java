package org.example;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Credentials {
    private String email;
    private String password;

    //Constructor
    @JsonCreator
    public Credentials(@JsonProperty("email") String email, @JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
    }

   //Getteri si setteri
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // metoda toString() pentru a reprezenta obiectul Credentials
    @Override
    public String toString() {
        return "Credentials{" +
               "email='" + email + '\'' +
               ", password='" + password + '\'' +
               '}';
    }
}
