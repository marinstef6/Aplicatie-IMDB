package org.example;
//clasa care returneaza experienta user-ului cand adauga ceva in sistem
class AddSystemStrategy implements ExperienceStrategy {
    @Override
    public int calculateExperience() {
        return 15;
    }
}