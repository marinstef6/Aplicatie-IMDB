package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

//clasa pentru interfata
public class MovieApp extends JFrame {

    //butoane
    private JTextField userText;
    private JPasswordField passwordText;
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    static List<Actor> actors;
    private JComboBox<String> selectionBox;
    static List<Production> productions;
    private JPanel actorsPanel;
    private JPanel productionsPanel;
    private JPanel mainMenuPanel;

    //constructor
    public MovieApp() {
       //numele aplicatiei
        super("IMDB App");
        mainPanel = new JPanel(new BorderLayout());
        tabbedPane = new JTabbedPane();
       //parsare fisiere
        actors = Parser.parseActors("src/actors.json");
        productions = Parser.parseProductions("src/production.json");

        initializeComponents();
        initializeMainPage();

        //initializare dimensiuni pagina
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        //adaugare pagina principala a aplicatiei
        tabbedPane.addTab("Main Page", mainPanel);
    }

    //initializare pagina principala
    private void initializeMainPage() {
        //buton pentru alegere actori sau productie
        selectionBox = new JComboBox<>(new String[]{"Actors", "Productions"});
        selectionBox.addActionListener(e -> updateMainContent(selectionBox.getSelectedItem().toString()));
        //alegere unde sa pun butonul
        mainPanel.add(selectionBox, BorderLayout.NORTH);
        updateMainContent("Actors");
    }
    //functie pentru a actualiza pagina
    private void updateMainContent(String selection) {
       //alegere ce vreau sa afisez
        JPanel contentPanel = "Actors".equals(selection) ? createActorsPanel() : createProductionsPanel();

        //actulizare date pagina
        mainPanel.removeAll();
        //pastrare buton alegere sus
        mainPanel.add(selectionBox, BorderLayout.NORTH);
        //afisare text pe mijloc
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    //functie pentru a afisa detalii pentru un anumit actor
    private JPanel createDetailPanelActor(Actor actor) {
        JPanel detailPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel(actor.getName());
        //buton de intoarcere la pagina principala
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> updateMainContent("Actors"));
        //pentru afisarea actorului
        JTextArea detailText = new JTextArea(actor.toString());
        //afisarea decenta a textului
        //afisare textului pe linii cat permite pagina si impartirea corecta a cuvintelor daca se termina randul
        detailText.setLineWrap(true);
        detailText.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(detailText);
        detailPanel.add(titleLabel, BorderLayout.NORTH);
        //adaugare scroll in partea de mijloc a paginii
        detailPanel.add(scrollPane, BorderLayout.CENTER);
        //punerea butonului de back in josul paginii
        detailPanel.add(backButton, BorderLayout.SOUTH);

        return detailPanel;
    }
    //functie pentru actori
    private JPanel createActorsPanel() {
        JPanel actorsPanel = new JPanel(new BorderLayout());
        JLabel actorsLabel = new JLabel("Actors");
        DefaultListModel<String> actorsModel = new DefaultListModel<>();
        JList<String> actorsList = new JList<>(actorsModel);
        //afisez pe ecran numele tuturor actorilor
        if (actors != null) {
            for (Actor actor : actors) {
                actorsModel.addElement(actor.getName());
            }
        } else {
            actorsModel.addElement("No actors found.");
        }

        //adaugarea unui MouseListener pentru a ma ajuta sa afisez detalii cand dau dublu click
        actorsList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList)evt.getSource();
                //detectare dublu-click
                if (evt.getClickCount() == 2) {
                    int index = list.locationToIndex(evt.getPoint());
                    if(index >= 0 && index < actors.size()) {
                        Actor selectedActor = actors.get(index);
                        JPanel detailPanel = createDetailPanelActor(selectedActor);
                        switchPanel(detailPanel);
                    }
                }
            }
        });

        actorsPanel.add(actorsLabel, BorderLayout.NORTH);
        actorsPanel.add(new JScrollPane(actorsList), BorderLayout.CENTER);

        return actorsPanel;
    }

    private JPanel createDetailPanelProduction(Production production) {
        JPanel detailPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel(production.getTitle());
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> updateMainContent("Productions"));
        JTextArea detailText = new JTextArea(production.toString());
        detailText.setLineWrap(true);
        detailText.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(detailText);

        detailPanel.add(titleLabel, BorderLayout.NORTH);
        detailPanel.add(scrollPane, BorderLayout.CENTER);
        detailPanel.add(backButton, BorderLayout.SOUTH);

        return detailPanel;
    }

    private JPanel createProductionsPanel() {
        JPanel productionsPanel = new JPanel(new BorderLayout());
        JLabel productionsLabel = new JLabel("Productions");
        DefaultListModel<String> productionsModel = new DefaultListModel<>();
        JList<String> productionsList = new JList<>(productionsModel);

        if (productions != null) {
            for (Production production : productions) {
                productionsModel.addElement(production.getTitle());
            }
        } else {
            productionsModel.addElement("No productions found.");
        }
        productionsList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList)evt.getSource();
                if (evt.getClickCount() == 2) {
                    int index = list.locationToIndex(evt.getPoint());
                    Production selectedProduction = productions.get(index);
                    JPanel detailPanel = createDetailPanelProduction(selectedProduction);
                    switchPanel(detailPanel);
                }
            }
        });
        productionsPanel.add(productionsLabel, BorderLayout.NORTH);
        productionsPanel.add(new JScrollPane(productionsList), BorderLayout.CENTER);

        return productionsPanel;
    }

    //initializare pagina login
    private void initializeComponents() {
        initializeMenu();
        //creare pagina Loign
        tabbedPane.addTab("Login", createLoginPanel());
        add(tabbedPane);
    }

    //initializare meniu
    private void initializeMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        menu.add(exitItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        userText = new JTextField(20);
        passwordText = new JPasswordField(20);

        JLabel userLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = userText.getText();
                String password = new String(passwordText.getPassword());
                IMDB.getInstance().loginGUI(email, password, MovieApp.this);
                tabbedPane.removeTabAt(tabbedPane.indexOfComponent(loginPanel));
                tabbedPane.setSelectedIndex(tabbedPane.indexOfComponent(mainPanel));
            }
        });

        loginPanel.add(userLabel, c);
        loginPanel.add(userText, c);
        loginPanel.add(passwordLabel, c);
        loginPanel.add(passwordText, c);
        loginPanel.add(loginButton, c);
        loginPanel.add(loginButton, new GridBagConstraints());
        return loginPanel;
    }
    //functie pentru schimbarea paginii
    private void switchPanel(JPanel panel) {
        mainPanel.removeAll();
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MovieApp app = new MovieApp();
            app.setVisible(true);
        });
    }
}
