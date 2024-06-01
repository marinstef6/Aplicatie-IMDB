package org.example;
//functie pentru a creste experienta daca rezolva o problema(cerere)
class IssueStrategy implements ExperienceStrategy {
    @Override
    public int calculateExperience() {
        return 10;

    }
}