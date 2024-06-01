package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

import static org.example.FlowApp.ratings;
import static org.example.IMDB.currentUser;
import static org.example.IMDB.getInstance;
import static org.example.PasswordGenerator.generateRandomPassword;
import static org.example.Production.*;
import static org.example.Actor.*;
import static org.example.Request.*;
//exceptie pentru comenzi invalide
class InvalidCommandException extends Exception {
    public InvalidCommandException(String message) {
        super(message);
    }
}
//exceptie pentru informatie incompleta
class InformationIncompleteException extends Exception {
    public InformationIncompleteException(String message) {
        super(message);
    }
}
@SuppressWarnings("unchecked")
public class Flow {
    static IMDB imdb;
    static Admin admin;
    static Contributor contributor;
    static Regular regular;
    static AccountType userType;
    static List<Actor> actors;
    static List<Request> requests;
    static List<User> users;
    static Credentials credentials;
    static String name;
    static String country;
    static int age;
    static String gender;
    static String birthDate = null;
    static List<Production> productions;
    //functie pentru meniul adminului
    public static void startAdminFlow(Admin admin) {
        Scanner scanner = new Scanner(System.in);
        productions = new ArrayList<>();
        actors = new ArrayList<>();
        actors = Parser.parseActors("src/actors.json");
        productions = Parser.parseProductions("src/production.json");
        requests = Parser.parseRequests("src/requests.json");
        users = Parser.parseUsers("src/accounts.json");
        boolean exit = false;
        while (!exit) {
            System.out.println("Admin Menu:");
            System.out.println("Choose an option please:");
            System.out.println("\t1. View details of all productions");
            System.out.println("\t2. View details of all actors");
            System.out.println("\t3. View received notifications");
            System.out.println("\t4. Search for a specific movie/series/actor");
            System.out.println("\t5. Add/Delete a production/actor to/from favorites");
            System.out.println("\t6. Create/Resolve a request");
            System.out.println("\t7. Add/Delete a production/actor from the system");
            System.out.println("\t8. View and resolve received requests");
            System.out.println("\t9. Update information about productions/actors");
            System.out.println("\t10. Add/Delete a review for a production");
            System.out.println("\t11. Add/Delete a user from the system");
            System.out.println("\t12. Logout");
            System.out.println("Enter your choice:");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    productionsMenu(productions, scanner, actors);
                    break;
                case 2:
                    actorsMenu(actors, scanner, productions);
                    break;
                case 3:
                    viewNotifications(admin);
                    break;
                case 4:
                    movieSeriesActorMenu(actors, scanner, productions);
                    break;
                case 5:
                    System.out.println("Choose an option please:");
                    System.out.println("\t1.Actor");
                    System.out.println("\t2.Production");
                    int choiceAandP = scanner.nextInt();
                    switch (choiceAandP) {
                        case 1:
                            System.out.println("Choose an option please:");
                            System.out.println("\t1.Delete Actor from favorite");
                            System.out.println("\t2.Add actor to favorite");
                            int choiceActorfav = scanner.nextInt();
                            switch (choiceActorfav) {
                                case 1:
                                    removeFavoriteActor(currentUser, scanner);
                                    break;
                                case 2:
                                    addFavoriteActor(currentUser, scanner);
                            }
                            break;
                        case 2:
                            System.out.println("Choose an option please:");
                            System.out.println("\t1.Delete Production from favorite");
                            System.out.println("\t2.Add Production to favorite");
                            int choiceProductionfav = scanner.nextInt();
                            switch (choiceProductionfav) {
                                case 1:
                                    removeFavoriteProduction(currentUser, scanner);
                                    break;
                                case 2:
                                    addFavoriteProduction(currentUser, scanner);
                                    break;
                            }
                            break;
                    }
                    break;
                case 6:
                    System.out.println("Choose an option please:");
                    System.out.println("\t1.Create request");
                    System.out.println("\t2.Resolve request");
                    int choiceRequest = scanner.nextInt();
                    switch (choiceRequest) {
                        case 1:
                            createRequest(requests, scanner);
                            break;
                        case 2:
                            resolveRequest(requests, productions, users, actors, scanner);
                            break;
                    }
                    break;
                case 7:
                    System.out.println("Choose an option please:");
                    System.out.println("\t1.Actor");
                    System.out.println("\t2.Production");
                    int choicePandA = scanner.nextInt();
                    switch (choicePandA) {
                        case 1:
                            System.out.println("Choose an option please:");
                            System.out.println("\t1.Delete Actor");
                            System.out.println("\t2.Create Actor");
                            int choiceActor = scanner.nextInt();
                            switch (choiceActor) {
                                case 1:
                                    removeActorFromSystem(actors, scanner);
                                    break;
                                case 2:
                                    createActorFromSystem(actors, scanner);
                                    break;
                            }
                            break;
                        case 2:
                            System.out.println("Choose an option please:");
                            System.out.println("\t1.Delete Production");
                            System.out.println("\t2.Create Production");
                            int choiceProduction = scanner.nextInt();
                            switch (choiceProduction) {
                                case 1:
                                    removeProductionFromSystem(productions, scanner);
                                    break;
                                case 2:
                                    System.out.println("Choose an option please:");
                                    System.out.println("\t1.Movie");
                                    System.out.println("\t2.Series");
                                    int choiceMorS = scanner.nextInt();
                                    switch (choiceMorS) {
                                        case 1:
                                            createNewProductionMovie(scanner, productions);
                                            break;
                                        case 2:
                                            createNewProductionSeries(scanner, productions);
                                            break;
                                    }
                            }
                            break;
                    }
                    break;
                case 8:
                    System.out.println("Choose an option please:");
                    System.out.println("\t1.View request");
                    System.out.println("\t2.Resolve request");
                    int choiceReq = scanner.nextInt();
                    switch (choiceReq) {
                        case 1:
                            for(Request request : requests)
                                System.out.println(request);
                            break;
                        case 2:
                            resolveRequest(requests, productions, users, actors, scanner);
                            break;
                    }
                    break;
                case 9:
                    System.out.println("Choose an option please:");
                    System.out.println("\t1.Update actor");
                    System.out.println("\t2.Update production");
                    int choice2 = scanner.nextInt();
                    switch (choice2) {
                        case 1:
                            updateActor(actors, scanner);
                            break;
                        case 2:
                            updateProduction(productions, scanner);
                            break;
                    }
                    break;
                case 10:
                    System.out.println("Choose an option please:");
                    System.out.println("\t1.Delete review");
                    System.out.println("\t2.Create review");
                    int choiceReview = scanner.nextInt();
                    switch (choiceReview) {
                        case 1:
                            deleteReview(productions, scanner);
                            break;
                        case 2:
                            createReview(productions, scanner);
                            break;
                    }
                    break;
                case 11:
                    System.out.println("Choose an option please:");
                    System.out.println("\t1.Delete user");
                    System.out.println("\t2.Create user");
                    int choiceUser = scanner.nextInt();
                    switch (choiceUser) {
                        case 1:
                            removeUser(users, scanner);
                            break;
                        case 2:
                            try {
                                createUser(users, scanner);
                            } catch (InformationIncompleteException e) {
                                System.out.println("Could not create user: " + e.getMessage());
                            }
                            break;
                    }
                    break;
                case 12:
                    System.out.println("Logging out...");
                    System.out.println("Choose an option please:");
                    System.out.println("\t1.Close application");
                    System.out.println("\t2.Relog");
                    int choice1 = scanner.nextInt();
                    switch (choice1) {
                        case 1:
                            exit = true;
                            break;
                        case 2:
                            loginAgain();
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    //functie pentru meniul contributorului
    public static void startContributorFlow(Contributor contributor) {
        Scanner scanner = new Scanner(System.in);
        productions = new ArrayList<>();
        actors = new ArrayList<>();
        actors = Parser.parseActors("src/actors.json");
        productions = Parser.parseProductions("src/production.json");
        requests = Parser.parseRequests("src/requests.json");
        users = Parser.parseUsers("src/accounts.json");
        boolean exit = false;
        while (!exit) {
            System.out.println("Contributor Menu:");
            System.out.println("Choose an option please:");
            System.out.println("\t1. View details of all productions");
            System.out.println("\t2. View details of all actors");
            System.out.println("\t3. View received notifications");
            System.out.println("\t4. Search for a specific movie/series/actor");
            System.out.println("\t5. Add/Delete a production/actor to/from favorites");
            System.out.println("\t6. Add/Delete a production/actor from the system");
            System.out.println("\t7. Update information about productions/actors");
            System.out.println("\t8. Create/Resolve a request");
            System.out.println("\t9. Logout");
            System.out.println("Enter your choice:");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    productionsMenu(productions, scanner, actors);
                    break;
                case 2:
                    actorsMenu(actors, scanner, productions);
                    break;
                case 3:
                    viewNotifications(contributor);
                    break;
                case 4:
                    movieSeriesActorMenu(actors, scanner, productions);
                    break;
                case 5:
                    System.out.println("Choose an option please:");
                    System.out.println("\t1.Actor");
                    System.out.println("\t2.Production");
                    int choiceAandP = scanner.nextInt();
                    switch (choiceAandP) {
                        case 1:
                            System.out.println("Choose an option please:");
                            System.out.println("\t1.Delete Actor from favorite");
                            System.out.println("\t2.Add actor to favorite");
                            int choiceActorfav = scanner.nextInt();
                            switch (choiceActorfav) {
                                case 1:
                                    removeFavoriteActor(currentUser, scanner);
                                    break;
                                case 2:
                                    addFavoriteActor(currentUser, scanner);
                                    break;
                            }
                            break;
                        case 2:
                            System.out.println("Choose an option please:");
                            System.out.println("\t1.Delete Production from favorite");
                            System.out.println("\t2.Add Production to favorite");
                            int choiceProductionfav = scanner.nextInt();
                            switch (choiceProductionfav) {
                                case 1:
                                    removeFavoriteProduction(currentUser, scanner);
                                    break;
                                case 2:
                                    addFavoriteProduction(currentUser, scanner);
                                    break;
                            }
                            break;
                    }
                    break;
                case 6:
                    System.out.println("Choose an option please:");
                    System.out.println("\t1.Actor");
                    System.out.println("\t2.Production");
                    int choicePandA = scanner.nextInt();
                    switch (choicePandA) {
                        case 1:
                            System.out.println("Choose an option please:");
                            System.out.println("\t1.Delete Actor");
                            System.out.println("\t2.Create Actor");
                            int choiceActor = scanner.nextInt();
                            switch (choiceActor) {
                                case 1:
                                    removeActorFromSystem(actors, scanner);
                                    break;
                                case 2:
                                    createActorFromSystem(actors, scanner);
                                    break;
                            }
                            break;
                        case 2:
                            System.out.println("Choose an option please:");
                            System.out.println("\t1.Delete Production");
                            System.out.println("\t2.Create Production");
                            int choiceProduction = scanner.nextInt();
                            switch (choiceProduction) {
                                case 1:
                                    removeProductionFromSystem(productions, scanner);
                                    break;
                                case 2:
                                    System.out.println("Choose an option please:");
                                    System.out.println("\t1.Movie");
                                    System.out.println("\t2.Series");
                                    int choiceMorS = scanner.nextInt();
                                    switch (choiceMorS) {
                                        case 1:
                                            createNewProductionMovie(scanner, productions);
                                            break;
                                        case 2:
                                            createNewProductionSeries(scanner, productions);
                                            break;
                                    }
                            }
                            break;
                    }
                    break;
                case 7:
                    System.out.println("Choose an option please:");
                    System.out.println("\t1.Update actor");
                    System.out.println("\t2.Update production");
                    int choice2 = scanner.nextInt();
                    switch (choice2) {
                        case 1:
                            updateActor(actors, scanner);
                            break;
                        case 2:
                            updateProduction(productions, scanner);
                            break;
                    }
                    break;
                case 8:
                    System.out.println("Choose an option please:");
                    System.out.println("\t1.Create request");
                    System.out.println("\t2.Resolve request");
                    int choiceRequest = scanner.nextInt();
                    switch (choiceRequest) {
                        case 1:
                            createRequest(requests, scanner);
                            break;
                        case 2:
                            resolveRequest(requests, productions, users, actors, scanner);
                            break;
                    }
                    break;
                case 9:
                    System.out.println("Logging out...");
                    System.out.println("Choose an option please:");
                    System.out.println("\t1.Close application");
                    System.out.println("\t2.Relog");
                    int choice1 = scanner.nextInt();
                    switch (choice1) {
                        case 1:
                            exit = true;
                            break;
                        case 2:
                            loginAgain();
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    //functie pentru meniul Regularului
    public static void startRegularFlow(Regular regular) {
        Scanner scanner = new Scanner(System.in);
        productions = new ArrayList<>();
        actors = new ArrayList<>();
        actors = Parser.parseActors("src/actors.json");
        productions = Parser.parseProductions("src/production.json");
        requests = Parser.parseRequests("src/requests.json");
        users = Parser.parseUsers("src/accounts.json");
        boolean exit = false;
        while (!exit) {
            System.out.println("Regular User Menu:");
            System.out.println("Choose an option please:");
            System.out.println("\t1. View details of all productions");
            System.out.println("\t2. View details of all actors");
            System.out.println("\t3. View received notifications");
            System.out.println("\t4. Search for a specific movie/series/actor");
            System.out.println("\t5. Add/Delete a production/actor to/from favorites");
            System.out.println("\t6. Create/Resolve a request");
            System.out.println("\t7. Add/Delete a review for a production");
            System.out.println("\t8. Logout");
            System.out.println("Enter your choice:");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    productionsMenu(productions, scanner, actors);
                    break;
                case 2:
                    actorsMenu(actors, scanner, productions);
                    break;
                case 3:
                    viewNotifications(regular);
                    break;
                case 4:
                    movieSeriesActorMenu(actors, scanner, productions);
                    break;
                case 5:
                    System.out.println("Choose an option please:");
                    System.out.println("\t1.Actor");
                    System.out.println("\t2.Production");
                    int choiceAandP = scanner.nextInt();
                    switch (choiceAandP) {
                        case 1:
                            System.out.println("Choose an option please:");
                            System.out.println("\t1.Delete Actor from favorite");
                            System.out.println("\t2.Add actor to favorite");
                            int choiceActorfav = scanner.nextInt();
                            switch (choiceActorfav) {
                                case 1:
                                    removeFavoriteActor(currentUser, scanner);
                                    break;
                                case 2:
                                    addFavoriteActor(currentUser, scanner);
                                    break;
                            }
                            break;
                        case 2:
                            System.out.println("Choose an option please:");
                            System.out.println("\t1.Delete Production from favorite");
                            System.out.println("\t2.Add Production to favorite");
                            int choiceProductionfav = scanner.nextInt();
                            switch (choiceProductionfav) {
                                case 1:
                                    removeFavoriteProduction(currentUser, scanner);
                                    break;
                                case 2:
                                    addFavoriteProduction(currentUser, scanner);
                                    break;
                            }
                            break;
                    }
                    break;
                case 6:
                    System.out.println("Choose an option please:");
                    System.out.println("\t1.Create request");
                    System.out.println("\t2.Resolve request");
                    int choiceRequest = scanner.nextInt();
                    switch (choiceRequest) {
                        case 1:
                            createRequest(requests, scanner);
                            break;
                        case 2:
                            resolveRequest(requests, productions, users, actors, scanner);
                            break;
                    }
                    break;
                case 7:
                    System.out.println("Choose an option please:");
                    System.out.println("\t1.Delete review");
                    System.out.println("\t2.Create review");
                    int choiceReview = scanner.nextInt();
                    switch (choiceReview) {
                        case 1:
                            deleteReview(productions, scanner);
                            break;
                        case 2:
                            createReview(productions, scanner);
                            break;
                    }
                    break;
                case 8:
                    System.out.println("Logging out...");
                    System.out.println("Choose an option please:");
                    System.out.println("\t1.Close application");
                    System.out.println("\t2.Relog");
                    int choice1 = scanner.nextInt();
                    switch (choice1) {
                        case 1:
                            exit = true;
                            break;
                        case 2:
                            currentUser = null;
                            loginAgain();
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void loginAgain()
    {
        imdb = IMDB.getInstance();
        imdb.login();
        User loggedInUser = currentUser;
        if (loggedInUser != null) {
            if (loggedInUser instanceof Admin) {
                startAdminFlow((Admin) loggedInUser);
            } else if (loggedInUser instanceof Contributor) {
                startContributorFlow((Contributor) loggedInUser);
            } else if (loggedInUser instanceof Regular) {
                startRegularFlow((Regular) loggedInUser);
            }
        } else {
            System.out.println("Error: No user found in session.");
        }
    }
    public static void viewAndResolveRequest(List<Request> requests, List<Production> productions, List<User> users, List<Actor> actors, Scanner scanner) {
        String currentUsername = currentUser.username;
        String currentType = String.valueOf(currentUser.userType);
        List<Request> relevantRequests = new ArrayList<>();
        for (Request request : requests) {
            System.out.println(request);
        }
        for (Request request : requests) {
            if (request.getTo().equals(currentUsername) || request.getTo().equalsIgnoreCase(currentType)) {
                relevantRequests.add(request);
            }
        }


        while (!relevantRequests.isEmpty()) {
            System.out.println("Please select a request to resolve (enter number):");
            int i = 1;
            for (Request request : relevantRequests) {
                System.out.println(i + ". " + request);
                i++;
            }
            scanner.nextLine();
            int choice = scanner.nextInt();
            if (choice > 0 && choice <= relevantRequests.size()) {
                Request selectedRequest = relevantRequests.get(choice - 1);
                switch (selectedRequest.getType()) {
                    case ACTOR_ISSUE:
                        updateActor(actors, scanner);
                        break;
                    case MOVIE_ISSUE:
                        updateProduction(productions, scanner);
                        break;
                    case DELETE_ACCOUNT:
                        removeUser(users, scanner);
                        break;
                    case OTHERS:
                        System.out.println("Enter your choice:");
                        System.out.println("\t1.Actor");
                        System.out.println("\t2.Production");
                        int choice1 = scanner.nextInt();
                        switch (choice1) {
                            case 1:
                                createActorFromSystem(actors, scanner);
                                break;
                            case 2:
                                System.out.println("Enter your choice:");
                                System.out.println("\t1.Movie");
                                System.out.println("\t2.Series");
                                int choice2 = scanner.nextInt();
                                switch (choice2) {
                                    case 1:
                                        createNewProductionMovie(scanner, productions);
                                        break;
                                    case 2:
                                        createNewProductionSeries(scanner, productions);
                                        break;
                                }
                        }
                        break;
                }

                relevantRequests.remove(selectedRequest);
            } else {
                System.out.println("Invalid choice, please select a number between 1 and " + relevantRequests.size());
            }
        }

        removeRequest(requests, scanner);
    }

    //functie pentru a sterge de la favorite o productie
    public static void removeFavoriteProduction(User user, Scanner scanner) {
        try {
            System.out.println("Your current favorite productions are:");
            //pun in lista productiile favorite si apoi lucrez cu ea in toata functia
            List<String> favorites = user.getFavoriteProductions();

            if (favorites == null || favorites.isEmpty()) {
                System.out.println("You have no favorite productions.");
                return;
            }

            for (int i = 0; i < favorites.size(); i++) {
                System.out.println(i + ": " + favorites.get(i));
            }

            System.out.println("Enter the index of the production you wish to remove:");
            int index = Integer.parseInt(scanner.nextLine());

            if (index >= 0 && index < favorites.size()) {
                //dupa ce am ales indexul pentru o productie o sterg
                String toRemove = favorites.get(index);
                favorites.remove(toRemove);
                System.out.println(toRemove + " has been removed from your favorites.");

                System.out.println("Updated favorite productions:");
                for (String production : favorites) {
                    System.out.println(production);
                }
            } else {
                System.out.println("Invalid index entered. Please try again with a valid index.");
            }
            //exceptie daca introduc un numar mai mare decat ultimul index
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: Please enter a valid number for the index.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    //functie pentru a sterge de la favorite un actor
    public static void removeFavoriteActor(User user, Scanner scanner) {
        try {
            System.out.println("Your current favorite actors are:");
            //pun intr-o lista toti actorii si apoi lucrez cu ea in toata functia
            List<String> favorites = user.getFavoriteActors();

            if (favorites == null || favorites.isEmpty()) {
                System.out.println("You have no favorite actors.");
                return;
            }

            for (int i = 0; i < favorites.size(); i++) {
                System.out.println(i + ": " + favorites.get(i));
            }

            System.out.println("Enter the index of the actor you wish to remove:");
            int index = Integer.parseInt(scanner.nextLine());

            if (index >= 0 && index < favorites.size()) {
                //sterg actorul in functie de indexul pe care l-am ales
                String toRemove = favorites.get(index);
                favorites.remove(toRemove);
                System.out.println(toRemove + " has been removed from your favorites.");

                System.out.println("Updated favorite actors:");
                for (String actor : favorites) {
                    System.out.println(actor);
                }
            } else {
                System.out.println("Invalid index entered. Please try again with a valid index.");
            }
            //exceptie daca am introdus un numar mai mare decat ultimul index
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: Please enter a valid number for the index.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    //adaugare la favorite o productie
    public static void addFavoriteProduction(User user, Scanner scanner) {
        scanner.nextLine();
        System.out.println("Enter the title of the production you wish to add:");
        //pun in lista toate productiile pe care le am
        List<String> favorites = user.getFavoriteProductions();
        if (favorites == null) {
            System.out.println("The list is empty!");
            return;
        }
        String title = scanner.nextLine();
        //adaug o productie si dupa afisez tot
        Production productionTitle = findProductionByName(title);
        //daca nu o gasesc afisez mesajul si ies din functie
        if(productionTitle == null)
        {
            System.out.println("The title doesn't exist in system!");
            return;
        }
        favorites.add(title);
        System.out.println("Updated favorite productions:");

        for (String production : favorites) {
            System.out.println(production);
        }
    }
    //adaugare la favorite un actor
    public static void addFavoriteActor(User user, Scanner scanner) {
        scanner.nextLine();
        System.out.println("Enter the title of the production you wish to add:");
        //pun in lista toti actorii
        List<String> favorites = user.getFavoriteActors();
        if (favorites == null) {
            System.out.println("The list is empty!");
            return;
        }
        String name = scanner.nextLine();
        //citesc un nume si apoi il adaug in lista dupa care afisez noua lista
        Actor actorName = findActorByName(name);
        //daca nu gasesc numele in lista din sistem afisez un mesja si ies din functie
        if(actorName == null)
        {
            System.out.println("The title doesn't exist in system!");
            return;
        }
        favorites.add(name);
        System.out.println("Updated favorite actors:");

        for (String actor : favorites) {
            System.out.println(actor);
        }
    }
    //functie pentru a crea un review
    public static void createReview(List<Production> productions, Scanner scanner) {
        scanner.nextLine();
        System.out.println("Enter the title of the production you want to review:");
        String productionTitle = scanner.nextLine();

        //functie pentru a cauta titlul
        Production production = findProductionByName( productionTitle);
        //daca nu il gaseste se afiseaza un mesaj pe ecran si se iese din functie
        if (production == null) {
            System.out.println("Production not found!");
            return;
        }

        //adaug in list toate ratingurile si apoi lucrez cu ea in toata functia
        List<Rating> ratings = production.getRatings();

        //citesc de la tastatura toate campurile pentru a crea un rating
        while (true) {
            System.out.println("Enter rating username (or 'done' to finish):");
            String username = scanner.nextLine();

            if(username.equalsIgnoreCase("done")) {
                break;
            }

            System.out.println("Enter rating value:");
            int ratingValue = Integer.parseInt(scanner.nextLine());

            System.out.println("Enter comment:");
            String comment = scanner.nextLine();

            Rating rating = new Rating(username, ratingValue, comment);
            ratings.add(rating);

            System.out.println("Rating added: " + rating);
        }
        //afisez toate ratingurile
        System.out.println("All ratings for " + productionTitle + ":");
        for (Rating rating : ratings) {
            System.out.println(rating);
        }
        //cresc experienta pentru userul curent
        ExperienceStrategy strategy = new ReviewStrategy();
        currentUser.performActionAndUpdateExperience(strategy);
        System.out.println(currentUser.experience);
    }
    //creare cerere
    public static void createRequest(List<Request> requests, Scanner scanner) {
        RequestTypes type = null;
        String actorName = null;
        String movieTitle  = null;
        scanner.nextLine();
        while(type == null) {
            //citire campuri pentru a crea cererea
            try {
                System.out.println("Enter the type of request (DELETE_ACCOUNT, ACTOR_ISSUE, MOVIE_ISSUE, OTHERS): ");
                String typeInput = scanner.nextLine().toUpperCase();
                type = RequestTypes.valueOf(typeInput);
                //daca nu este tipul de cerere de mai sus trebuie sa afisez un mesaj
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid request type. Please enter one of the following: DELETE_ACCOUNT, ACTOR_ISSUE, MOVIE_ISSUE, OTHERS.");
            }
        }
        System.out.println("Enter the created date (yyyy-MM-dd'T'HH:mm:ss): ");
        String createdDate = scanner.nextLine();

        System.out.println("Enter the recipient username: ");
        String to = scanner.nextLine();
        //aflu daca userul exista
        User toUser = findUserByUsername(users, to);
        //daca userul a fost gasit aflu ce tip este si daca este admin se va crea cererea
        if (toUser != null) {
            String userTypeTo = String.valueOf(toUser.getUserType());

            if (type == RequestTypes.DELETE_ACCOUNT) {
                if ("Admin".equalsIgnoreCase(userTypeTo)) {

                    System.out.println("Enter the description: ");
                    String description = scanner.nextLine();

                    System.out.println("Enter your username: ");
                    String username = scanner.nextLine();

                    Request request = new Request(type, to, createdDate, null, null, description, username); // assuming 'toUser' should be here instead of 'to'
                    requests.add(request);

                } else {
                    System.out.println("DELETE_ACCOUNT requests can only be sent to ADMIN.");
                }
            }
        } else {
            System.out.println("User not found.");
        }
        //daca userul a fost gasit aflu ce tip este si daca este admin se va crea cererea
        if (toUser != null) {
            String userTypeTo = String.valueOf(toUser.getUserType());

            if (type == RequestTypes.OTHERS) {
                if ("Admin".equalsIgnoreCase(userTypeTo)) {

                    System.out.println("Enter the description: ");
                    String description = scanner.nextLine();

                    System.out.println("Enter your username: ");
                    String username = scanner.nextLine();

                    Request request = new Request(type, to, createdDate, null, null, description, username); // assuming 'toUser' should be here instead of 'to'
                    requests.add(request);

                } else {
                    System.out.println("OTHERS requests can only be sent to ADMIN.");
                }
            }
        } else {
            System.out.println("User not found.");
        }

        if (type == RequestTypes.ACTOR_ISSUE) {
            System.out.println("Enter the actor name: ");
            actorName = scanner.nextLine();
            User userTo = findUserByUsername(users, to);
            if (userTo != null && !userTo.getActorsContribution().contains(actorName)) {
                System.out.println("The actor name is not part of ActorContributor for the user.");
                return;
            }
            System.out.println("Enter the description: ");
            String description = scanner.nextLine();

            System.out.println("Enter your username: ");
            String username = scanner.nextLine();

            Request request = new Request(type, to, createdDate, null, actorName, description, username);
            requests.add(request);
        }
        if (type == RequestTypes.MOVIE_ISSUE) {
            System.out.println("Enter the movie title: ");
            movieTitle = scanner.nextLine();
            User userTo = findUserByUsername(users, to);
            if (userTo != null && !userTo.getProductionsContributions().contains(movieTitle)) {
                System.out.println("The production name is not part of ProductionContributor for the user.");
                return;
            }
            System.out.println("Enter the description: ");
            String description = scanner.nextLine();

            System.out.println("Enter your username: ");
            String username = scanner.nextLine();

            Request request = new Request(type, to, createdDate, movieTitle, null, description, username);
            requests.add(request);
        }
        //afisez toate cererile
        for(Request request1 : requests)
            System.out.println(request1);
        System.out.println("Request created successfully and added to the list.");
        //cresc experienta
        ExperienceStrategy strategy = new IssueStrategy();
        currentUser.performActionAndUpdateExperience(strategy);
        System.out.println(currentUser.experience);
    }
    //functie pentru a gasi numele unui user
    public static User findUserByUsername(List<User> users, String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    //functie pentru a sterge o cerere
    public static void removeRequest(List<Request> requests, Scanner scanner)
    {
        //adaug toate cererile intr-o lista si lucrez cu ea
        List<Request> deletedRequests = new ArrayList<>();

        System.out.println("Available Requests:");
        for (int i = 0; i < requests.size(); i++) {
            System.out.println(i + ": " + requests.get(i));
        }
        scanner.nextLine();
        System.out.println("Type the index of the Request to delete:");
        int requestIndex = scanner.nextInt();


        if (requestIndex >= 0 && requestIndex < requests.size()) {
            //aleg un index si sterg cererea respectiva
            Request toDelete = requests.remove(requestIndex);
            deletedRequests.add(toDelete);
            System.out.println("Request Deleted: " + toDelete);
        } else {
            System.out.println("Invalid request index.");
            return;
        }

        System.out.println("Updated Requests List:");
        for (Request request : requests) {
            System.out.println(request);
        }
    }

    //functie de a sterge un review
    public static void deleteReview(List<Production> productions, Scanner scanner) {
        scanner.nextLine();
        System.out.println("Enter the title of the production:");
        String title = scanner.nextLine();
        Production production = findProductionByName(title);
        //daca numele productiei nu exista ies din functie
        if (production != null) {
            List<Rating> ratings = production.getRatings();
            System.out.println("Current ratings for " + title + ":");
            for (int i = 0; i < ratings.size(); i++) {
                Rating rating = ratings.get(i);
                System.out.println(i + 1 + ". " + rating);
            }

            System.out.println("Enter the number of the rating you want to remove:");
            int ratingIndex = Integer.parseInt(scanner.nextLine()) - 1;

            if (ratingIndex >= 0 && ratingIndex < ratings.size()) {
                //sterg reviewul care are indexul respectiv
                ratings.remove(ratingIndex);
                System.out.println("Rating removed. Updated ratings list:");

                for (Rating rating : ratings) {
                    System.out.println(rating);
                }
            } else {
                System.out.println("Invalid rating number!");
            }
        } else {
            System.out.println("Production not found!");
        }
        production.averageRating = production.calculateRating(ratings);
        //cresc experienta
        ExperienceStrategy strategy = new ReviewStrategy();
        currentUser.performActionAndUpdateExperience(strategy);
        System.out.println(currentUser.experience);
        System.out.println(production.averageRating);
    }

    //functie pentru a vedea daca am notificari
    public static void viewNotifications(User user) {
        if (user instanceof Admin) {
            Admin admin = (Admin) user;
            List<String> notifications = admin.getNotifications();

            if (notifications == null) {
                System.out.println("No new notifications.");
            } else {
                System.out.println("You have the following notifications:");
                for (String notification : notifications) {
                    System.out.println(" - " + notification);
                }
            }
        } else if (user instanceof Contributor) {
            Contributor contributor = (Contributor) user;
            List<String> notifications = contributor.getNotifications();

            if (notifications == null) {
                System.out.println("No new notifications.");
            } else {
                System.out.println("You have the following notifications:");
                for (String notification : notifications) {
                    System.out.println(" - " + notification);
                }
            }
        } else if (user instanceof Regular) {
            Regular regular = (Regular) user;
            List<String> notifications = regular.getNotifications();

            if (notifications == null) {
                System.out.println("No new notifications.");
            } else {
                System.out.println("You have the following notifications:");
                for (String notification : notifications) {
                    System.out.println(" - " + notification);
                }
            }
        }
    }

    //functie pentru a rezolva o cerere
    public static void resolveRequest(List<Request> requests, List<Production> productions, List<User> users, List<Actor> actors, Scanner scanner)
    {
        String currentUsername = currentUser.username;
        String currentType = String.valueOf(currentUser.userType);
        for (Request request : requests) {
            System.out.println(request);
        }
        for (Request request : requests) {
            if (request.getTo().equals(currentUsername)) {
                System.out.println("Found a request for the current user: " + request);
                if (request.getType() == RequestTypes.ACTOR_ISSUE) {
                    updateActor(actors, scanner);
                    break;
                }
                if (request.getType() == RequestTypes.MOVIE_ISSUE) {
                    updateProduction(productions, scanner);
                    break;
                }
            }
        }
        for(Request request : requests)
        {
            if (request.getTo().equalsIgnoreCase(currentType)) {
                System.out.println("Found a request for the current user: " + request);
                if(request.getType() == RequestTypes.DELETE_ACCOUNT)
                {
                    removeUser(users, scanner);
                    break;
                }
                if(request.getType() == RequestTypes.OTHERS)
                {
                    System.out.println("Enter your choice:");
                    System.out.println("\t1.Actor");
                    System.out.println("\t2.Production");
                    int choice1 = scanner.nextInt();
                    switch (choice1) {
                        case 1:
                            createActorFromSystem(actors, scanner);
                            break;
                        case 2:
                            System.out.println("Enter your choice:");
                            System.out.println("\t1.Movie");
                            System.out.println("\t2.Series");
                            int choice2 = scanner.nextInt();
                            switch (choice2) {
                                case 1:
                                    createNewProductionMovie(scanner, productions);
                                    break;
                                case 2:
                                    createNewProductionSeries(scanner, productions);
                                    break;
                            }
                    }
                }
            }
        }
        //dupa ce am rezolva cererea o sterg din lista de cereri
        removeRequest(requests, scanner);
        ExperienceStrategy strategy = new IssueStrategy();
        currentUser.performActionAndUpdateExperience(strategy);
        System.out.println(currentUser.experience);
    }
    //functie pentu a crea un actor pentru sistem
    public static void createActorFromSystem(List<Actor> actors,Scanner scanner) {
        //citesc fiecare camp pe care il are un actor
            scanner.nextLine();
            System.out.println("Enter Actor Name:");
            String name = scanner.nextLine();

            List<ProductionRole> performances = new ArrayList<>();
            System.out.println("How many performances would you like to enter?");
            int numPerformances = Integer.parseInt(scanner.nextLine());

            for (int i = 0; i < numPerformances; i++) {
                System.out.println("Enter the title of performance " + (i + 1) + ":");
                String title = scanner.nextLine();
                System.out.println("Enter the type of performance " + (i + 1) + " (Movie/Series):");
                String type = scanner.nextLine();

                performances.add(new ProductionRole(title, type));
            }

            System.out.println("Enter Actor Biography:");
            String biography = scanner.nextLine();

            Actor newActor = new Actor(name, performances, biography);
            actors.add(newActor);

        System.out.println("New actor added: ");
        for(Actor actor1 : actors)
            System.out.println(actor1);
        //cresc experienta userului curent
        ExperienceStrategy strategy = new AddSystemStrategy();
        currentUser.performActionAndUpdateExperience(strategy);
        System.out.println(currentUser.experience);
    }
    //functie pentru a crea o productie pentru sistem de tip Movie
    public static void createNewProductionMovie(Scanner scanner, List<Production> productions) {
        //citesc fiecare camp pentru a putea sa creez productia
        scanner.nextLine();
        System.out.println("Enter Production Title:");
        String title = scanner.nextLine();

        System.out.println("Enter Production Type (Movie):");
        String type = scanner.nextLine();
        ProductionType productionType = ProductionType.valueOf(type);

        System.out.println("Enter Production Director:");
        List<String> director = Collections.singletonList(scanner.nextLine());

        List<String> actors = new ArrayList<>();

        System.out.println("How many actors would you like to enter?");
        int numActor = Integer.parseInt(scanner.nextLine());

        //citesc cati actori vreau sa introduc
        for (int i = 0; i < numActor; i++) {
            System.out.println("Enter actor " + (i + 1) + ":");
            String actor = scanner.nextLine();
            actors.add(actor);
        }

        System.out.println("You entered actors: " + actors);

        System.out.println("How many genres would you like to enter?");
        int numGenres = Integer.parseInt(scanner.nextLine());

        List<Genre> genres = new ArrayList<>();

        for (int i = 0; i < numGenres; i++) {
            System.out.println("Enter genre " + (i + 1) + ":");
            String genre = scanner.nextLine();
            genres.add(Genre.valueOf(genre));
        }

        System.out.println("You entered genres: " + genres);

        List<Rating> ratings = new ArrayList<>();

        while (true) {
            System.out.println("Enter rating username (or 'done' to finish):");
            String username = scanner.nextLine();

            if(username.equalsIgnoreCase("done")) {
                break;
            }

            System.out.println("Enter comment:");
            String comment = scanner.nextLine();

            System.out.println("Enter rating value:");
            int ratingValue = Integer.parseInt(scanner.nextLine());

            Rating rating = new Rating(username, ratingValue, comment);
            ratings.add(rating);

            System.out.println("Rating added: " + rating);
        }

        System.out.println("All ratings:");
        for (Rating rating : ratings) {
            System.out.println(rating);
        }

        System.out.println("Enter Production Duration:");
        String duration= scanner.nextLine();

        System.out.println("Enter Production Plot:");
        String plot = scanner.nextLine();

        System.out.println("Enter Production Release year:");
        int releaseYear = Integer.parseInt(scanner.nextLine());
        Double averageRating = Production.calculateRating(ratings);
        Production newProduction = new Movie(title, productionType, director, actors, genres, ratings, duration, plot, releaseYear, averageRating);
        productions.add(newProduction);
        System.out.println("New production added: " + newProduction);
        for(Production production1 : productions)
            System.out.println(production1);
        ExperienceStrategy strategy = new AddSystemStrategy();
        currentUser.performActionAndUpdateExperience(strategy);
        System.out.println(currentUser.experience);
    }
    //creez o productie de tip Series
    public static void createNewProductionSeries(Scanner scanner, List<Production> productions) {
        scanner.nextLine();
        System.out.println("Enter Production Title:");
        String title = scanner.nextLine();

        System.out.println("Enter Production Type (Series):");
        String type = scanner.nextLine();
        ProductionType productionType = ProductionType.valueOf(type);

        System.out.println("Enter Production Director:");
        List<String> director = Collections.singletonList(scanner.nextLine());

        List<String> actors = new ArrayList<>();

        System.out.println("How many actors would you like to enter?");
        int numActor = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < numActor; i++) {
            System.out.println("Enter actor " + (i + 1) + ":");
            String actor = scanner.nextLine();
            actors.add(actor);
        }

        System.out.println("You entered actors: " + actors);

        System.out.println("How many genres would you like to enter?");
        int numGenres = Integer.parseInt(scanner.nextLine());

        List<Genre> genres = new ArrayList<>();

        for (int i = 0; i < numGenres; i++) {
            System.out.println("Enter genre " + (i + 1) + ":");
            String genre = scanner.nextLine();
            genres.add(Genre.valueOf(genre));
        }

        System.out.println("You entered genres: " + genres);

        List<Rating> ratings = new ArrayList<>();

        while (true) {
            System.out.println("Enter rating username (or 'done' to finish):");
            String username = scanner.nextLine();

            if(username.equalsIgnoreCase("done")) {
                break;
            }

            System.out.println("Enter comment:");
            String comment = scanner.nextLine();

            System.out.println("Enter rating value:");
            int ratingValue = Integer.parseInt(scanner.nextLine());

            Rating rating = new Rating(username, ratingValue, comment);
            ratings.add(rating);

            System.out.println("Rating added: " + rating);
        }

        System.out.println("Ratings:");
        for (Rating rating : ratings) {
            System.out.println(rating);
        }

        System.out.println("Enter Production Plot:");
        String plot = scanner.nextLine();

        System.out.println("Enter Production Release year:");
        int releaseYear = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter Production Number of season:");
        int numberOfSeason = Integer.parseInt(scanner.nextLine());

        Map<String, List<Episode>> seasons = new HashMap<>();

        for (int i = 0; i < numberOfSeason; i++) {
            System.out.println("Enter Season " + (i + 1) + " name or number:");
            String seasonName = scanner.nextLine();

            System.out.println("How many episodes are in Season " + seasonName + "?");
            int numEpisodes = Integer.parseInt(scanner.nextLine());

            List<Episode> episodes = new ArrayList<>();

            for (int j = 0; j < numEpisodes; j++) {
                System.out.println("Enter Episode " + (j + 1) + " name:");
                String episodeName = scanner.nextLine();

                System.out.println("Enter Episode " + (j + 1) + " duration (e.g., '42 minutes'):");
                String durationEpisode = scanner.nextLine();

                episodes.add(new Episode(episodeName, durationEpisode));
            }

            seasons.put(seasonName, episodes);
        }
        Double averageRating = Production.calculateRating(ratings);
        Production newProduction = new Series(title, productionType, director, actors, genres, ratings,  releaseYear, plot, averageRating,  numberOfSeason, seasons);
        productions.add(newProduction);
        System.out.println("New production added: " + newProduction);
        for(Production production1 : productions)
            System.out.println(production1);
        ExperienceStrategy strategy = new AddSystemStrategy();
        currentUser.performActionAndUpdateExperience(strategy);
        System.out.println(currentUser.experience);
    }

    //sterg o productie din sistem
    public static void removeProductionFromSystem(List<Production> productions, Scanner scanner) {
        List<Production> deletedProductions = new ArrayList<>();

        System.out.println("Available Productions:");
        for (int i = 0; i < productions.size(); i++) {
            System.out.println(i + ": " + productions.get(i));
        }
        scanner.nextLine();
        System.out.println("Type the index of the Production to delete:");
        int productionIndex = scanner.nextInt();


        if (productionIndex >= 0 && productionIndex < productions.size()) {
            Production toDelete = productions.remove(productionIndex);
            deletedProductions.add(toDelete);
            System.out.println("Production Deleted: " + toDelete.getTitle());
        } else {
            System.out.println("Invalid production index.");
            return;
        }

        System.out.println("Updated Productions List:");
        for (Production production : productions) {
            System.out.println(production.getTitle() + " - " + production);
        }
    }
    //sterg un user din sistem
    public static void removeUser(List<User> users, Scanner scanner) {
        //adaug intr-o lista toti userii
        List<User> deletedUsers = new ArrayList<>();

        System.out.println("Available Users:");
        //aleg un index si dupa sterg userul respectiv
        for (int i = 0; i < users.size(); i++) {
            System.out.println(i + ": " + users.get(i));
        }

        System.out.println("Type the index of the User to delete:");
        int userIndex = scanner.nextInt();
        scanner.nextLine();

        if (userIndex >= 0 && userIndex < users.size()) {
            User toDelete = users.remove(userIndex);
            deletedUsers.add(toDelete);
            System.out.println("User Deleted: " + toDelete.userInformation.name);
        } else {
            System.out.println("Invalid user index.");
            return;
        }

        System.out.println("Updated Users List:");
        for (User user : users) {
            System.out.println(user);
        }
    }
    //sterg un actor din sistem
    public static void removeActorFromSystem(List<Actor> actors,Scanner scanner){
        //adaug toti actorii intr-o lista si lucrez cu ea in toata functia
        List<Actor> deletedActors= new ArrayList<>();

        System.out.println("Available Actors:");
        for (int i = 0; i < actors.size(); i++) {
            System.out.println(i + ": " + actors.get(i).getName() + " - " + actors.get(i));
        }
        scanner.nextLine();
        System.out.println("Type the index of the Actor to delete:");
        int actorIndex = scanner.nextInt();
        //aleg un index si sterg actorul respectiv
        if (actorIndex >= 0 && actorIndex < actors.size()) {
            Actor toDelete = actors.remove(actorIndex);
            deletedActors.add(toDelete);
            System.out.println("Actor Deleted: " + toDelete.getName());
        } else {
            System.out.println("Invalid actor index.");
            return;
        }
    }
    //functie pentru a crea un user
    public static void createUser(List<User> users, Scanner scanner) throws InformationIncompleteException
    {
        //citesc fiecare camp pentru a crea un user
        System.out.println("Hello, enter your information! :)");
        System.out.println("Enter your hobby:");
        String hobby = scanner.nextLine();

        scanner.nextLine();

        System.out.println("Enter email:");
        String email = scanner.nextLine();

        System.out.println("Enter the number letters of password:");
        int nrPassword = Integer.parseInt(scanner.nextLine());
        Credentials credentials = new Credentials(email,generateRandomPassword(nrPassword));

        System.out.println("Enter name:");
        String name = scanner.nextLine();

        System.out.println("Enter country:");
        String country = scanner.nextLine();

        System.out.println("Enter age:");
        int age = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter gender:");
        String gender = scanner.nextLine();

        System.out.println("Enter birth date (YYYY-MM-DD):");
        String birthDate = scanner.nextLine();
        //daca informatiile sunt incomplete voi afisa un mesaj
        if (email == null || email.isEmpty() || name == null || name.isEmpty() ||
                country == null || country.isEmpty() || age == 0 || gender == null || gender.isEmpty()) {
            throw new InformationIncompleteException("Information is incomplete.");
        }
        // Now create the User.Information object with the data
        User.Information information = new User.Information(credentials, name, country, age, gender, birthDate);

        // Prepare other details (you might want to read these from the user too)
        List<String> productionsContribution = new ArrayList<>();
        List<String> actorsContribution = new ArrayList<>();
        List<String> favoriteProductions = new ArrayList<>();
        List<String> favoriteActors = new ArrayList<>();

        // Read the type of user to create
        System.out.println("Enter type of user (Regular, Contributor, Admin):");
        String type = scanner.nextLine();
        AccountType accountType = AccountType.valueOf(type);  // Convert string to AccountType

        // Create the user
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        int experience = 0;
        User user = UserFactory.createUser(username, experience, information, accountType,
                productionsContribution, actorsContribution, favoriteProductions, favoriteActors);

        users.add(user);
        System.out.println("User created: " + user.toString());
        for(User user1 : users)
            System.out.println(user1);
        //cresc experienta userului curent care s-a ocupat de asta
        ExperienceStrategy strategy = new AddSystemStrategy();
        currentUser.performActionAndUpdateExperience(strategy);
        System.out.println(currentUser.experience);
    }
    //functie pentru a actualiza o productie
    public static void updateProduction(List<Production> productions, Scanner scanner)
    {
        //scriu numele productiei si apoi aleg ce vrea sa sterg de aici
        scanner.nextLine();
        try {
            System.out.println("Type the name of the Production:");
            String productionName = scanner.nextLine();
            Production productionToUpdate = findProductionByName(productionName);  // Ensure this method correctly returns a production or null

            if (productionToUpdate == null) {
                System.out.println("No production was found to update. Please ensure the production exists and try again.");
                return;
            }
            System.out.println("What do you want to change about your production?");
            System.out.println("\t1) Title");
            System.out.println("\t2) Directors");
            System.out.println("\t3) Actors");
            System.out.println("\t4) Genres");
            System.out.println("\t5) Ratings");
            System.out.println("\t6) Plot");
            System.out.println("\t7) Seasons");
            int updateChoice = scanner.nextInt();
            scanner.nextLine();
            switch (updateChoice) {
                case 1:
                    System.out.println("Enter the new title for your production");
                    String newNameActor = scanner.nextLine();
                    System.out.println("Old title: " + productionToUpdate.getTitle());
                    productionToUpdate.setTitle(newNameActor);
                    System.out.println("New title: " + productionToUpdate.getTitle());
                    break;
                case 2:
                    System.out.println("Enter the new director for your production:");
                    String newDirector = scanner.nextLine();
                    System.out.println("Old director: " + productionToUpdate.getDirectors());
                    productionToUpdate.setDirectors(Collections.singletonList(newDirector));
                    System.out.println("New director: " + productionToUpdate.getDirectors());
                    break;
                case 3:
                    List<String> actors = new ArrayList<>(productionToUpdate.getActors());
                    System.out.println("Which actor do you want to replace?");
                    for (int i = 0; i < actors.size(); i++) {
                        System.out.println(i + ": " + actors.get(i));
                    }
                    int actorIndex = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Who is the new actor?");
                    String newActorName = scanner.nextLine();
                    actors.set(actorIndex, newActorName);
                    productionToUpdate.setActors(actors);
                    System.out.println("Updated actors: " + productionToUpdate.getActors());
                    break;
                case 4:
                    List<Genre> genres = new ArrayList<>(productionToUpdate.getGenres());
                    System.out.println("Current genres:");
                    for (int i = 0; i < genres.size(); i++) {
                        System.out.println(i + ": " + genres.get(i));
                    }
                    System.out.println(genres.size() + ": Add a new genre");

                    System.out.println("Select a genre to replace or add a new one:");
                    int genreIndex = scanner.nextInt();
                    scanner.nextLine();

                    if (genreIndex < genres.size()) {
                        System.out.println("Enter the new genre:");
                        String newGenre = scanner.nextLine();
                        genres.set(genreIndex, Genre.valueOf(newGenre));  // Replace the existing genre
                    } else if (genreIndex == genres.size()) {  // Add a new genre
                        System.out.println("Enter the new genre to add:");
                        String newGenre = scanner.nextLine();
                        genres.add(Genre.valueOf(newGenre));  // Add the new genre
                    } else {
                        System.out.println("Invalid selection. Please try again.");
                        break;
                    }
                    productionToUpdate.setGenres(genres);
                    System.out.println("Updated genres: " + productionToUpdate.getGenres());
                    break;
                case 5:
                    List<Rating> ratings = productionToUpdate.getRatings();
                    System.out.println("Which rating would you like to modify?");
                    for (int i = 0; i < ratings.size(); i++) {
                        System.out.println(i + ": " + ratings.get(i));
                    }
                    int ratingIndex = scanner.nextInt();
                    scanner.nextLine();
                    Rating ratingToModify = ratings.get(ratingIndex);

                    System.out.println("What would you like to modify?");
                    System.out.println("1: Username");
                    System.out.println("2: Rating");
                    System.out.println("3: Comment");
                    int modifyOption = scanner.nextInt();
                    scanner.nextLine();

                    switch (modifyOption) {
                        case 1:
                            System.out.println("Enter the new username:");
                            String newUsername = scanner.nextLine();
                            ratingToModify.setUsername(newUsername);
                            break;
                        case 2: // Modify rating value
                            System.out.println("Enter the new rating (0-10):");
                            int newRatingValue = scanner.nextInt();
                            scanner.nextLine();
                            ratingToModify.setRating(newRatingValue);
                            break;
                        case 3: // Modify comment
                            System.out.println("Enter the new comment:");
                            String newComment = scanner.nextLine();
                            ratingToModify.setComment(newComment);
                            break;
                        default:
                            System.out.println("Invalid option, returning to main menu.");
                    }
                    productionToUpdate.averageRating = productionToUpdate.calculateRating(ratings);
                    productionToUpdate.setRating(ratings);
                    System.out.println("Updated rating: " + ratingToModify);
                    System.out.println("Average rating: " + productionToUpdate.averageRating(ratings));
                    break;
                case 6:
                    System.out.println("Enter the new plot for your production:");
                    String newPlot = scanner.nextLine();
                    System.out.println("Old plot: " + productionToUpdate.getPlot());
                    productionToUpdate.setPlot(newPlot);
                    System.out.println("New plot: " + productionToUpdate.getPlot());
                    break;
                case 7:
                    System.out.println("Which season's episode would you like to modify? (e.g., 'Season x')");
                    Map<String, List<Episode>> seasons = productionToUpdate.getSeasons();
                    seasons.keySet().forEach(System.out::println);  // Print all seasons

                    String seasonName = scanner.nextLine();
                    List<Episode> episodes = seasons.get(seasonName);

                    if (episodes != null) {
                        System.out.println("Select an episode to modify:");
                        for (int i = 0; i < episodes.size(); i++) {
                            System.out.println(i + ": " + episodes.get(i));
                        }

                        int episodeIndex = scanner.nextInt();
                        scanner.nextLine();


                        if(episodeIndex >= 0 && episodeIndex < episodes.size()) {
                            Episode episodeToModify = episodes.get(episodeIndex);
                            System.out.println("What would you like to modify?");
                            System.out.println("1: Episode Name");
                            System.out.println("2: Duration");
                            int option = scanner.nextInt();
                            scanner.nextLine();

                            switch (option) {
                                case 1: // Modify episode name
                                    System.out.println("Enter the new episode name:");
                                    String newEpisodeName = scanner.nextLine();
                                    episodeToModify.setEpisodeName(newEpisodeName);
                                    break;
                                case 2: // Modify duration
                                    System.out.println("Enter the new duration (e.g., '47 minutes'):");
                                    String newDuration = scanner.nextLine();
                                    episodeToModify.setDuration(newDuration);
                                    break;
                                default:
                                    System.out.println("Invalid option, returning to main menu.");
                            }

                            episodes.set(episodeIndex, episodeToModify);
                            seasons.put(seasonName, episodes);
                            productionToUpdate.setSeasons(seasons);
                            System.out.println("Updated episode: " + episodeToModify);
                        } else {
                            System.out.println("Invalid episode index.");
                        }
                    } else {
                        System.out.println("Season not found.");
                    }
                    break;
            }
            //afisez un mesaj daca nu introduc o valoare corecta
        } catch (InputMismatchException e) {
            System.out.println("Invalid input provided. Please ensure to provide the correct input format.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    //functie pentru a actualiza un actor
    public static void updateActor(List<Actor> actors, Scanner scanner) {
        System.out.println("Type the name of the Actor:");
        //citesc numele si aleg ce vreau sa modific
        scanner.nextLine();
        String actorName = scanner.nextLine();
        Actor actorToUpdate = findActorByName(actorName);
        ProductionRole performanceToUpdate;
        if (actorToUpdate == null) {
            System.out.println("No actor was found to update. Please ensure the actor exists and try again.");
            return;
        }

        System.out.println("What do you want to change about your actor?");
        System.out.println("\t1) Name");
        System.out.println("\t2) Performances");
        System.out.println("\t3) Biography");
        int updateChoice = scanner.nextInt();
        scanner.nextLine();
        switch (updateChoice) {
            case 1:
                System.out.println("Enter the new name for your actor");
                String newNameActor = scanner.nextLine();
                System.out.println("Old name: " + actorToUpdate.getName());
                actorToUpdate.setName(newNameActor);
                System.out.println("New name: " + actorToUpdate.getName());
                break;
            case 2:
                List<ProductionRole> performances = actorToUpdate.getPerformances();
                if (performances.isEmpty()) {
                    System.out.println("No performances to update for " + actorToUpdate.getName());
                    break;
                }
                System.out.println("Select the performance to update:");
                for (int i = 0; i < performances.size(); i++) {
                    ProductionRole p = performances.get(i);
                    System.out.println("\t" + (i + 1) + ") Title: " + p.getTitle() + ", Type: " + p.getType());
                }
                int performanceChoice = scanner.nextInt();
                scanner.nextLine();

                if (performanceChoice < 1 || performanceChoice > performances.size()) {
                    System.out.println("Invalid performance selection");
                    return;
                }
                performanceToUpdate = performances.get(performanceChoice - 1);

                System.out.println("What do you want to update for this performance?");
                System.out.println("\t1) Title");
                System.out.println("\t2) Type");
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 1) {
                    System.out.println("Enter the new title for the performance");
                    String newTitle = scanner.nextLine();
                    performanceToUpdate.setTitle(newTitle);
                    System.out.println("New title set: " + performanceToUpdate.getTitle());
                } else if (choice == 2) {
                    System.out.println("Enter the new type for the performance");
                    String newType = scanner.nextLine();
                    performanceToUpdate.setType(newType);
                    System.out.println("New type set: " + performanceToUpdate.getType());
                } else {
                    System.out.println("Invalid choice");
                }
                break;
            case 3:
                System.out.println("Enter the new biography for your actor:");
                String newBiography = scanner.nextLine();

                System.out.println("Old biography: " + actorToUpdate.getBiography());

                actorToUpdate.setBiography(newBiography);

                System.out.println("New biography: " + actorToUpdate.getBiography());

                break;
            default:
                System.out.println("Invalid option.");
                break;
        }
    }

    //functie pentru a gasi titlul unei productii
    public static Production findProductionByName(String productionName){
        for(Production production : productions)
            if(production.getTitle().equalsIgnoreCase(productionName))
                return production;
        return null;
    }
    //functie pentru a gasi numele unui actor
    public static Actor findActorByName(String name) {
        for (Actor actor : actors)
            if (actor.getName().equalsIgnoreCase(name))
                return actor;
        return null;
    }

    //functie pentru a gasi un film/serial sau actor in functie de umele pe care il tastez
    public static void movieSeriesActorMenu(List<Actor> actors, Scanner scanner, List<Production> productions) {
        System.out.println("\t1. Search Movie");
        System.out.println("\t2. Search Series");
        System.out.println("\t3. Search Actors");
        System.out.println("\t4. Return menu");
        System.out.println("Enter your choice:");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                System.out.println("Enter the name of the movie:");
                scanner.nextLine();
                String nameMovie = scanner.nextLine();
                Production.searchMovie(productions, nameMovie);

                System.out.println("Action completed successfully! Please choose another option.");
                movieSeriesActorMenu(actors, scanner, productions);
                break;
            case 2:
                System.out.println("Enter the name of the series: ");
                scanner.nextLine();
                String nameSeries = scanner.nextLine();
                Production.searchMovie(productions, nameSeries);

                System.out.println("Action completed successfully! Please choose another option.");
                movieSeriesActorMenu(actors, scanner, productions);
                break;
            case 3:
                System.out.println("Enter the name of the actor:");
                scanner.nextLine();
                String name = scanner.nextLine();
                Actor.searchActorByName(actors, name);

                System.out.println("Action completed successfully! Please choose another option.");
                movieSeriesActorMenu(actors, scanner, productions);
            case 4:
                System.out.println("Returning to main menu...");
                if(currentUser.userType == userType.Admin)
                    startAdminFlow(admin);
                else if(currentUser.userType == userType.Contributor)
                    startContributorFlow(contributor);
                else
                    startRegularFlow(regular);
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }
    //functie pentru a putea alege ce vreau sa afisez un actorii in functie de diefrite criterii
    public static void actorsMenu(List<Actor> actors, Scanner scanner, List<Production> productions) {
        System.out.println("\t1. Display actors in alphabetical order");
        System.out.println("\t2. Display actor by movie/series");
        System.out.println("\t3. Search actor by type");
        System.out.println("\t4. Display actors only");
        System.out.println("\t5. Search actor by name");
        System.out.println("\t6. Display American actors");
        System.out.println("\t7. Return to main menu");
        System.out.println("Enter your choice:");

        int choice = scanner.nextInt();

        switch(choice) {
            //afisez actorii alfabetic
            case 1:
                Actor.displaySortedActors(actors);
                System.out.println("Action completed successfully! Please choose another option.");

                actorsMenu(actors, scanner, productions);
                break;
                //afisze actorul in functie de productia in care joaca
            case 2:
                System.out.println("Enter the title of the movie/series:");
                scanner.nextLine();
                String movieOrSeriesName = scanner.nextLine();

                Actor.searchActorByMovie(actors, movieOrSeriesName);
                System.out.println("Action completed successfully! Please choose another option.");

                actorsMenu(actors, scanner, productions);
                break;
            case 3:
                //afisez actorii care joaca in seriale sau doar filme
                System.out.println("Enter the type (Movie/Series):");
                scanner.nextLine();
                String type = scanner.nextLine();

                Actor.searchActorByType(actors, type);

                System.out.println("Action completed successfully! Please choose another option.");

                actorsMenu(actors, scanner, productions);
                break;
            case 4:
                //afisez toti actorii(barbatii)
                Actor.filterActorsByBiography(actors, "actor");

                System.out.println("Action completed successfully! Please choose another option.");

                actorsMenu(actors, scanner, productions);
                break;
            case 5:
                //afisez un actor in functie de  numele cautat
                System.out.println("Enter the name of the actor:");
                scanner.nextLine();
                String name = scanner.nextLine();
                Actor.searchActorByName(actors, name);

                System.out.println("Action completed successfully! Please choose another option.");

                actorsMenu(actors, scanner, productions);
                break;
            case 6:
                //afisez actorii americani
                Actor.filterActorsByBiography(actors, "American");

                System.out.println("Action completed successfully! Please choose another option.");

                actorsMenu(actors, scanner, productions);
                break;
            case 7:
                System.out.println("Returning to main menu...");
                if(currentUser.userType == userType.Admin)
                    startAdminFlow(admin);
                else if(currentUser.userType == userType.Contributor)
                    startContributorFlow(contributor);
                else
                    startRegularFlow(regular);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    //functie pentru a afisa productiile in functie de mai multe criterii
    public static void productionsMenu(List<Production> productions, Scanner scanner, List<Actor> actors) {
        System.out.println("\t1. Display all productions");
        System.out.println("\t2. Display productions by genre");
        System.out.println("\t3. Display productions released in a certain year");
        System.out.println("\t4. Display productions from highest to lowest rating");
        System.out.println("\t5. Display productions from lowest to highest rating");
        System.out.println("\t6. Display productions chronologically");
        System.out.println("\t7. Display productions featuring a certain actor");
        System.out.println("\t8. Play trailer for a production");
        System.out.println("\t9. Return to main menu");

        System.out.println("Enter your choice:");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                //afisez toate productiile
                printProductions(productions);
                System.out.println("Action completed successfully! Please choose another option.");

                productionsMenu(productions, scanner, actors);
                break;
            case 2:
                //afisze in functie de gen
                System.out.println("Enter the genre:");
                scanner.nextLine();
                String genre = scanner.nextLine();
                filterProductionsByGenre(productions, genre);
                System.out.println("Action completed successfully! Please choose another option.");

                productionsMenu(productions, scanner, actors);
                break;
            case 3:
                //afisez in functie de an
                try {
                    System.out.print("Enter the year to filter by: ");
                    int year = scanner.nextInt();
                    filterProductionsByYear(productions, year);
                } catch (InputMismatchException e) {
                    System.out.println("You have entered an invalid year. Please enter a number.");
                    scanner.next();
                }
                System.out.println("Action completed successfully! Please choose another option.");
                productionsMenu(productions, scanner, actors);
                break;
            case 4:
                //afisez productiile in functie de rating mare-mic
                sortProductionsByRating(productions);
                System.out.println("Action completed successfully! Please choose another option.");

                productionsMenu(productions, scanner, actors);
                break;
            case 5:
                //afisez productiile mic-mare in functie de rating
                sortHighProductionsByRating(productions);
                System.out.println("Action completed successfully! Please choose another option.");

                productionsMenu(productions, scanner, actors);
                break;
            case 6:
                //afisez productiile in functie de anul in care au fost facute
                sortProductionsChronologically(productions);
                System.out.println("Action completed successfully! Please choose another option.");

                productionsMenu(productions, scanner, actors);
                break;
            case 7:
                //afisez productia in functie de numele unui actor
                System.out.println("Enter the name of the actor:");
                scanner.nextLine();
                String actor_name = scanner.nextLine();
                filterProductionsByActor(productions, actor_name);
                productionsMenu(productions, scanner, actors);
                break;
            case 8:
                //afisez o anumita productie
                System.out.println("Enter the title of the production:");
                scanner.nextLine();
                String production_title = scanner.nextLine();
                playTrailer(production_title);
                productionsMenu(productions, scanner, actors);
                break;

            case 9:
                System.out.println("Returning to main menu...");
                if(currentUser.userType == userType.Admin)
                    startAdminFlow(admin);
                else if(currentUser.userType == userType.Contributor)
                    startContributorFlow(contributor);
                else
                    startRegularFlow(regular);
                break;
            default:
                System.out.println("Invalid choice.");
        }

    }
}
