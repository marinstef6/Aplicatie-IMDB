package org.example;

//returnare experinta pentru scrierea unui review
class ReviewStrategy implements ExperienceStrategy {
    @Override
    public int calculateExperience() {
        return 5;
    }
}