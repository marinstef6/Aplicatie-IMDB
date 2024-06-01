package org.example;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static org.example.Actor.*;
import static org.example.Production.*;
//functia principala
public class IMDB {
    private static IMDB instance = null;
    public static User currentUser;
    public static Integer releaseYear;
    public static Genre genre;
    public static String movieOrSeriesName;
    public static UserInterface userInterface;
    public List<User> users;
    public List<Actor> actors;
    public List<Request> requests;
    public List<Production> productions;

    //constructor
    private IMDB() {
        this.users = new ArrayList<>();
        this.actors = new ArrayList<>();
        this.requests = new ArrayList<>();
        this.productions = new ArrayList<>();
        this.userInterface = new ConsoleUserInterface();
    }

    //creare singleton
    public static IMDB getInstance() {
        if (instance == null) {
            instance = new IMDB();
        }
        return instance;
    }
    //serializare productie
    public void serializeProductions() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            for (Production production : productions) {
                String jsonString = mapper.writeValueAsString(production);
                System.out.println(jsonString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //metod pentru a porni aplicatia
    public void run() {

        presentInterfaceOptions();
        loadDataJSON();
        System.out.println("\n");
        userInterface.displayOutput("Welcome back to IMDB! Please enter your email and password to login.\n");
        login();

        startFlow(currentUser);
    }
    //incarcare date din fisierele json
    public void loadDataJSON() {
        Parser.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Parser.objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true);
        actors = Parser.parseActors("src/actors.json");
        productions = Parser.parseProductions("src/production.json");
        requests = Parser.parseRequests("src/requests.json");
        users = Parser.parseUsers("src/accounts.json");
    }
    //functie pentru a alege ce tip este fiecare utilizator
    public void startFlow(User user) {
        if (user == null) {
            System.out.println("Error: User is null");
            return;
        }
        switch (user.userType) {
            case Regular:
                Flow.startRegularFlow((Regular) user);
                break;
            case Admin:
                Flow.startAdminFlow((Admin) user);
                break;
            case Contributor:
                Flow.startContributorFlow((Contributor) user);
                break;
        }
    }
    //functie pentru logare in terminal
    public void login() {
        while (true) {
            userInterface.displayOutput("Please enter your email (or type 'exit' to quit):");
            String email = userInterface.getInput();
            if ("exit".equalsIgnoreCase(email)) break;

            userInterface.displayOutput("Please enter your password:");
            String password = userInterface.getInput();

            for (User user : users) {
                Credentials credentials = user.userInformation.getCredentials();
                if (credentials.getEmail().equals(email) && credentials.getPassword().equals(password)) {
                    currentUser = user;
                    userInterface.displayOutput("Welcome back, " + user.username + "!");
                    userInterface.displayOutput("Username: " + user.username + "!");
                    userInterface.displayOutput("User experience: " + user.experience);
                    return;
                }
            }
            userInterface.displayOutput("Invalid credentials. Please try again or type 'exit' to quit.");
        }
    }
    //functie pentru logare in interfata grafica
    public void loginGUI(String email, String password, JFrame frame) {
        for (User user : users) {
            Credentials credentials = user.userInformation.getCredentials();
            if (credentials.getEmail().equals(email) && credentials.getPassword().equals(password)) {
                currentUser = user;
                JOptionPane.showMessageDialog(frame, "Login Successful", "Welcome back, " + user.username, JOptionPane.INFORMATION_MESSAGE);
                if (currentUser instanceof Admin) {
                    FlowApp.startAdminFlowGUI((Admin) currentUser, frame);
                } else if (currentUser instanceof Contributor) {
                    FlowApp.startContributorFlowGUI((Contributor) currentUser, frame);
                } else if (currentUser instanceof Regular) {
                    FlowApp.startRegularFlowGUI((Regular) currentUser, frame);
                }
                return;
            }
        }
        if (currentUser instanceof Admin) {
            JFrame adminFrame = new FlowApp.AdminGUI((Admin) currentUser);
            adminFrame.setVisible(true);
        }
        JOptionPane.showMessageDialog(frame, "Invalid credentials. Please try again.", "Login Error", JOptionPane.ERROR_MESSAGE);
    }
    //functie pentru alege butonul: terminal sau grafica
    private void presentInterfaceOptions () {
        JFrame frame = new JFrame("Application Mode Selector");
        //buton pentru inchidere aplicatie
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new FlowLayout());

        JButton btnGUI = new JButton("Open GUI");
        JButton btnCLI = new JButton("Open CLI");

        //in functie de ce aleg se incepe scrierea: terminal sau aplicatie
        btnGUI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "GUI Mode Selected");

                SwingUtilities.invokeLater(() -> {
                    MovieApp movieApp = new MovieApp();
                    movieApp.setVisible(true);
                });
            }
        });

        btnCLI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                userInterface = new TerminalUI();
                System.out.println("CLI Mode Selected");
            }
        });

        frame.getContentPane().add(btnGUI);
        frame.getContentPane().add(btnCLI);
        frame.setVisible(true);
    }
    //functia principala
    public static void main (String[]args){
        IMDB imdb = IMDB.getInstance();
        imdb.run();
    }

    //functie pentru a sterge/crea o cerere
    public static class RequestsHolder {
        private static List<Request> requests = new ArrayList<>();

        public static void addRequest(Request request) {
            requests.add(request);
        }

        public static void removeRequest(Request request) {
            requests.remove(request);
        }

        public static List<Request> getRequests() {
            return new ArrayList<>(requests);
        }
    }
    }
