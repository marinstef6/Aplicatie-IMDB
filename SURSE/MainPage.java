package org.example;

import javax.swing.*;
import java.awt.*;
//functie pentru pagina pricipala a interfetei grafice
public class MainPage {
    private JPanel mainPanel;
    private JButton searchButton;
    private JTextField searchField;
    private JList<String> movieList;
    private JComboBox<String> genreFilter;

    //constructor
    public MainPage() {
        //butoane
        mainPanel = new JPanel(new BorderLayout());
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchPanel.add(new JLabel("Search Movie/Series/Actor:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        movieList = new JList<>();
        //buton de scroll
        JScrollPane scrollPane = new JScrollPane(movieList);

        String[] genres = {"All", "Action", "Comedy", "Drama"};
        genreFilter = new JComboBox<>(genres);

        //butoane de cautare/pentru genuri de filme si scroll
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(genreFilter, BorderLayout.WEST);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    //getter
    public JPanel getMainPanel() {
        return mainPanel;
    }

    //functia principala pentru afisarea numelui paginii si butonul de inchidere
    public static void main(String[] args) {
        JFrame frame = new JFrame("Main Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainPage mainPage = new MainPage();
        frame.add(mainPage.getMainPanel());
        //pentru dimensiune
        frame.pack();
        //sa fie vizibila
        frame.setVisible(true);
    }
}
