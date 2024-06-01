package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.IMDB.currentUser;
import static org.example.Actor.*;

@SuppressWarnings("unchecked")
public class FlowApp extends Component {
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
	static Scanner scanner = null;
	static List<Rating> ratings;


	public static void startAdminFlowGUI(Admin admin, JFrame previousFrame) {
		productions = new ArrayList<>();
		actors = new ArrayList<>();
		actors = Parser.parseActors("src/actors.json");
		productions = Parser.parseProductions("src/production.json");
		requests = Parser.parseRequests("src/requests.json");
		users = Parser.parseUsers("src/accounts.json");
		AdminGUI adminGUI = new AdminGUI(admin);
		adminGUI.setVisible(true);
		previousFrame.dispose();
	}

	public static void startContributorFlowGUI(Contributor contributor, JFrame previousFrame) {
		productions = new ArrayList<>();
		actors = new ArrayList<>();
		actors = Parser.parseActors("src/actors.json");
		productions = Parser.parseProductions("src/production.json");
		requests = Parser.parseRequests("src/requests.json");
		users = Parser.parseUsers("src/accounts.json");
		ContributorGUI contributorGUI = new ContributorGUI(contributor);
		contributorGUI.setVisible(true);
		previousFrame.dispose();
	}


	public static void startRegularFlowGUI(Regular regular, JFrame previousFrame) {
		productions = new ArrayList<>();
		actors = new ArrayList<>();
		actors = Parser.parseActors("src/actors.json");
		productions = Parser.parseProductions("src/production.json");
		requests = Parser.parseRequests("src/requests.json");
		users = Parser.parseUsers("src/accounts.json");
		RegularGUI regularGUI = new RegularGUI(regular);
		regularGUI.setVisible(true);
		previousFrame.dispose();
	}

	static class AdminGUI extends JFrame {
		Admin admin;
		String[] options = {
				"View details of all productions",
				"View details of all actors",
				"View received notifications",
				"Search for a specific movie/series/actor",
				"Add/Delete a production/actor to/from favorites",
				"Create/Resolve a request",
				"Add/Delete a production/actor from the system",
				"View and resolve received requests",
				"Update information about productions/actors",
				"Add/Delete a review for a production",
				"Add/Delete a user from the system",
				"Logout"
		};

		public AdminGUI(Admin admin) {
			this.admin = admin;
			initializeUI();
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLocationRelativeTo(null);
		}

		private void initializeUI() {
			setTitle("Admin Menu");
			setSize(400, 200);
			setLayout(new BorderLayout());

			// Panou pentru selectarea opțiunii
			JPanel selectionPanel = new JPanel();
			JLabel label = new JLabel("Choose an action: ");
			JComboBox<String> optionsComboBox = new JComboBox<>(options);
			selectionPanel.add(label);
			selectionPanel.add(optionsComboBox);

			JButton okButton = new JButton("OK");
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int selectedOption = optionsComboBox.getSelectedIndex();
					handleOption(selectedOption);
				}
			});

			add(selectionPanel, BorderLayout.CENTER);
			add(okButton, BorderLayout.SOUTH);
		}

		private void handleOption(int optionIndex) {
			switch (optionIndex) {
				case 0:
					JOptionPane.showMessageDialog(this, "Viewing details of all productions...");
					showProductionDisplayOptions(this);
					break;
				case 1:
					JOptionPane.showMessageDialog(this, "Viewing details of all actors...");
					showActorDisplayOptions(this);
					break;
				case 2:
					JOptionPane.showMessageDialog(this, "View received notifications");
					viewNotifications(admin, this);
					break;
				case 3:
					JOptionPane.showMessageDialog(this, "Search for a specific movie/series/actor");
					movieSeriesActorMenu(this);
					break;
				case 4:
					JOptionPane.showMessageDialog(this, "Add/Delete a production/actor to/from favorites");
					showFavorites(this);
					break;
				case 5:
					JOptionPane.showMessageDialog(this, "Create/Resolve a request");
					showRequest(this);
					break;
				case 6:
					JOptionPane.showMessageDialog(this, "Add/Delete a production/actor from the system");
					showCreateorDeleteAorP(this);
					break;
				case 7:
					JOptionPane.showMessageDialog(this, "View and resolve received requests");
					showRequestView(this);
					break;
				case 8:
					JOptionPane.showMessageDialog(this, "Update information about productions/actors");
					showUpdate(this);
					break;
				case 9:
					JOptionPane.showMessageDialog(this, "Add/Delete a review for a production");
					showReview(this);
					break;
				case 10:
					JOptionPane.showMessageDialog(this, "Add/Delete a user from the system");
					showUser(this);
					break;
				case 11:
					JOptionPane.showMessageDialog(this, "Logout");
					dispose();
					break;
				default:
					JOptionPane.showMessageDialog(this, "Option not implemented yet.");
					break;
			}

		}
	}

	static class ContributorGUI extends JFrame {
		Contributor contributor;

		public ContributorGUI(Contributor contributor) {
			this.contributor = contributor;
			initializeUI();
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLocationRelativeTo(null);
		}

		String[] options = {
				"View details of all productions",
				"View details of all actors ",
				"View received notifications",
				"Search for a specific movie/series/actor",
				"Add/Delete a production/actor to/from favorites",
				"Add/Delete a production/actor from the system",
				"Update information about productions/actors",
				"Create/Resolve a request",
				"Logout"

		};

		private void initializeUI() {
			setTitle("Contributor Menu");
			setSize(400, 200);
			setLayout(new BorderLayout());

			JPanel selectionPanel = new JPanel();
			JLabel label = new JLabel("Choose an action: ");
			JComboBox<String> optionsComboBox = new JComboBox<>(options);
			selectionPanel.add(label);
			selectionPanel.add(optionsComboBox);

			JButton okButton = new JButton("OK");
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int selectedOption = optionsComboBox.getSelectedIndex();
					handleOption(selectedOption);
				}
			});

			add(selectionPanel, BorderLayout.CENTER);
			add(okButton, BorderLayout.SOUTH);
		}

		private void handleOption(int optionIndex) {
			switch (optionIndex) {

				case 0:
					JOptionPane.showMessageDialog(this, "Viewing details of all productions...");
					showProductionDisplayOptions(this);
					break;
				case 1:
					JOptionPane.showMessageDialog(this, "Viewing details of all actors...");
					showActorDisplayOptions(this);
					break;
				case 2:
					JOptionPane.showMessageDialog(this, "View received notifications");
					viewNotifications(contributor, this);
					break;
				case 3:
					JOptionPane.showMessageDialog(this, "Search for a specific movie/series/actor");
					movieSeriesActorMenu(this);
					break;
				case 4:
					JOptionPane.showMessageDialog(this, "Add/Delete a production/actor to/from favorites");
					showFavorites(this);
					break;
				case 5:
					JOptionPane.showMessageDialog(this, "Add/Delete a production/actor from the system");
					showCreateorDeleteAorP(this);
					break;
				case 6:
					JOptionPane.showMessageDialog(this, "Update information about productions/actors");
					showUpdate(this);
					break;
				case 7:
					JOptionPane.showMessageDialog(this, "Create/Resolve a request");
					showRequest(this);
					break;
				case 8:
					JOptionPane.showMessageDialog(this, "Logout");
					dispose();
					break;
				default:
					JOptionPane.showMessageDialog(this, "Option not implemented yet.");
					break;
			}

		}
	}

	static class RegularGUI extends JFrame {
		Regular regular;

		public RegularGUI(Regular regular) {
			this.regular = regular;
			initializeUI();
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLocationRelativeTo(null);
		}

		String[] options = {
				"View details of all productions",
				"View details of all actors",
				"View received notifications",
				"Search for a specific movie/series/actor",
				"Add/Delete a production/actor to/from favorites",
				"Create/Resolve a request",
				"Add/Delete a review for a production",
				"Logout"
		};

		private void initializeUI() {
			setTitle("Regular Menu");
			setSize(400, 200);
			setLayout(new BorderLayout());

			JPanel selectionPanel = new JPanel();
			JLabel label = new JLabel("Choose an action: ");
			JComboBox<String> optionsComboBox = new JComboBox<>(options);
			selectionPanel.add(label);
			selectionPanel.add(optionsComboBox);

			JButton okButton = new JButton("OK");
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int selectedOption = optionsComboBox.getSelectedIndex();
					handleOption(selectedOption);
				}
			});

			add(selectionPanel, BorderLayout.CENTER);
			add(okButton, BorderLayout.SOUTH);
		}

		private void handleOption(int optionIndex) {

			switch (optionIndex) {
				case 0:
					JOptionPane.showMessageDialog(this, "Viewing details of all productions...");
					showProductionDisplayOptions(this);
					break;
				case 1:
					JOptionPane.showMessageDialog(this, "Viewing details of all actors...");
					showActorDisplayOptions(this);
					break;
				case 2:
					JOptionPane.showMessageDialog(this, "View received notifications");
					viewNotifications(regular, this);
					break;
				case 3:
					JOptionPane.showMessageDialog(this, "Search for a specific movie/series/actor");
					movieSeriesActorMenu(this);
					break;
				case 4:
					JOptionPane.showMessageDialog(this, "Add/Delete a production/actor to/from favorites");
					showFavorites(this);
					break;
				case 5:
					JOptionPane.showMessageDialog(this, "Create/Resolve a request");
					showRequest(this);
					break;
				case 6:
					JOptionPane.showMessageDialog(this, "Add/Delete a review for a production");
					showReview(this);
					break;
				case 7:
					JOptionPane.showMessageDialog(this, "Logout");
					dispose();
					break;
				default:
					JOptionPane.showMessageDialog(this, "Option not implemented yet.");
					break;
			}
		}
	}
	//pentru a putea mostenii proprietatile unei interfete grafice
	//Folosirea parent asigura ca dialogurile sunt centrate corespunzator si se comporta cum trebuie
	//in raport cu restul aplicației
	public static void createUser(List<User> users, Component parent) {
		String hobby = JOptionPane.showInputDialog(parent, "Enter your hobby:");
		String email = JOptionPane.showInputDialog(parent, "Enter email:");
		String password = JOptionPane.showInputDialog(parent, "Enter password:");
		Credentials credentials = new Credentials(email, password);

		String name = JOptionPane.showInputDialog(parent, "Enter name:");
		String country = JOptionPane.showInputDialog(parent, "Enter country:");
		int age = Integer.parseInt(JOptionPane.showInputDialog(parent, "Enter age:"));
		String gender = JOptionPane.showInputDialog(parent, "Enter gender:");
		String birthDate = JOptionPane.showInputDialog(parent, "Enter birth date (YYYY-MM-DD):");

		User.Information information = new User.Information(credentials, name, country, age, gender, birthDate);

		List<String> productionsContribution = new ArrayList<>();
		List<String> actorsContribution = new ArrayList<>();
		List<String> favoriteProductions = new ArrayList<>();
		List<String> favoriteActors = new ArrayList<>();


		String type = JOptionPane.showInputDialog(parent, "Enter type of user (Regular, Contributor, Admin):");
		AccountType accountType = AccountType.valueOf(type);

		String username = JOptionPane.showInputDialog(parent, "Enter username:");
		int experience = 0;

		User user = UserFactory.createUser(username, experience, information, accountType,
				productionsContribution, actorsContribution, favoriteProductions, favoriteActors);

		FlowApp.users.add(user);

		JOptionPane.showMessageDialog(parent, "User created: " + user.toString());
		ExperienceStrategy strategy = new AddSystemStrategy();
		currentUser.performActionAndUpdateExperience(strategy);
		JOptionPane.showMessageDialog(parent, "Experience: " + currentUser.experience);
	}
	public static void removeUser(List<User> users, Component parent) {
		if (FlowApp.users.isEmpty()) {
			JOptionPane.showMessageDialog(parent, "No users available to delete.");
			return;
		}

		String[] userStrings = new String[FlowApp.users.size()];
		for (int i = 0; i < FlowApp.users.size(); i++) {
			userStrings[i] = (i + ": " + FlowApp.users.get(i).toString());
		}

		String selectedValue = (String) JOptionPane.showInputDialog(
				parent,
				"Select a user to delete:",
				"Remove User",
				JOptionPane.QUESTION_MESSAGE,
				null,
				userStrings,
				userStrings[0]);

		if (selectedValue != null && !selectedValue.isEmpty()) {
			int selectedIndex = Integer.parseInt(selectedValue.split(":")[0]);
			User removed = FlowApp.users.remove(selectedIndex);
			JOptionPane.showMessageDialog(parent, "Removed: " + removed.userInformation.name);

			StringBuilder updatedList = new StringBuilder("Updated Users List:\n");
			for (User user : FlowApp.users) {
				updatedList.append(user).append("\n");
			}
			JTextArea textArea = new JTextArea(updatedList.toString());
			textArea.setEditable(false);
			JScrollPane scrollPane = new JScrollPane(textArea);
			scrollPane.setPreferredSize(new Dimension(300, 150));
			JOptionPane.showMessageDialog(parent, scrollPane, "Updated Users", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(parent, "No user was selected for deletion!");
		}
	}
	private static void showUser(Component parent) {
		String[] options = {
				"Create User",
				"Remove Users",
		};

		String selectedOption = (String) JOptionPane.showInputDialog(
				parent,
				"Select how you want to view:",
				"Display Options",
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]
		);

		if (selectedOption != null) {
			for (int i = 0; i < options.length; i++) {
				if (selectedOption.equals(options[i])) {
					executeUser(i + 1, parent);
					break;
				}
			}
		}
	}
	private static void executeUser(int option, Component parent) {
		switch (option) {
			case 1:
				createUser(users, parent);
				break;
			case 2:
				removeUser(users, parent);
				break;
			default:
				JOptionPane.showMessageDialog(parent, "Invalid option selected.");
				break;
		}
	}
	private static void showRequestView(Component parent) {
		String[] options = {
				"View Request",
				"Resolve Request",
		};

		String selectedOption = (String) JOptionPane.showInputDialog(
				parent,
				"Select how you want to view:",
				"Display Options",
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]
		);

		if (selectedOption != null) {
			for (int i = 0; i < options.length; i++) {
				if (selectedOption.equals(options[i])) {
					executeRequestView(i + 1, parent);
					break;
				}
			}
		}
	}
	private static void executeRequestView(int option, Component parent) {
		switch (option) {
			case 1:
				viewRequest(requests, parent);
				break;
			case 2:
				resolveRequest(requests, productions, users, actors, parent);
				break;
			default:
				JOptionPane.showMessageDialog(parent, "Invalid option selected.");
				break;
		}
	}
	public static void viewRequest(List<Request> requests, Component parent) {
		StringBuilder requestListView = new StringBuilder("Requests:\n");
		for (Request request : requests) {
			requestListView.append(request).append("\n");
		}
		JTextArea textArea = new JTextArea(requestListView.toString());
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(250, 150));

		JOptionPane.showMessageDialog(parent, scrollPane, "View Requests", JOptionPane.INFORMATION_MESSAGE);
	}
	public static void createRequest(List<Request> requests, Component parent) {
		RequestTypes type = null;
		while (type == null) {
			try {
				String typeInput = JOptionPane.showInputDialog(parent, "Enter the type of request (DELETE_ACCOUNT, ACTOR_ISSUE, MOVIE_ISSUE, OTHERS):").toUpperCase();
				type = RequestTypes.valueOf(typeInput);
			} catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(parent, "Invalid request type. Please try again.");
			}
		}

		String createdDate = JOptionPane.showInputDialog(parent, "Enter the created date (yyyy-MM-dd'T'HH:mm:ss):");
		String to = JOptionPane.showInputDialog(parent, "Enter the recipient username:");
		User toUser = findUserByUsername(users, to);

		if (toUser != null) {
			String userTypeTo = String.valueOf(toUser.getUserType());

			if ((type == RequestTypes.DELETE_ACCOUNT || type == RequestTypes.OTHERS) && "Admin".equalsIgnoreCase(userTypeTo)) {
				String description = JOptionPane.showInputDialog(parent, "Enter the description:");
				String username = JOptionPane.showInputDialog(parent, "Enter your username:");
				Request request = new Request(type, to, createdDate, null, null, description, username);
				requests.add(request);
				JOptionPane.showMessageDialog(parent, type + " request created successfully and added to the list.");
			} else if ((type == RequestTypes.DELETE_ACCOUNT || type == RequestTypes.OTHERS) && !"Admin".equalsIgnoreCase(userTypeTo)) {
				JOptionPane.showMessageDialog(parent, type + " requests can only be sent to ADMIN.");
				return;
			}

			String actorName = null;
			String movieTitle = null;
			String description = null;
			String username = null;

			if (type == RequestTypes.ACTOR_ISSUE) {
				actorName = JOptionPane.showInputDialog(parent, "Enter the actor name:");
			} else if (type == RequestTypes.MOVIE_ISSUE) {
				movieTitle = JOptionPane.showInputDialog(parent, "Enter the movie title:");
			}

			if (actorName != null || movieTitle != null) {
				description = JOptionPane.showInputDialog(parent, "Enter the description:");
				username = JOptionPane.showInputDialog(parent, "Enter your username:");
				Request request = new Request(type, to, createdDate, movieTitle, actorName, description, username);
				requests.add(request);
				JOptionPane.showMessageDialog(parent, type + " request created successfully and added to the list.");
			}
		} else {
			JOptionPane.showMessageDialog(parent, "User not found.");
		}
		ExperienceStrategy strategy = new IssueStrategy();
		currentUser.performActionAndUpdateExperience(strategy);
		JOptionPane.showMessageDialog(parent, "Experince" + currentUser.experience);
	}

	public static User findUserByUsername(List<User> users, String username) {
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}
	public static void removeRequest(List<Request> requests, Component parent) {
		if (requests.isEmpty()) {
			JOptionPane.showMessageDialog(parent, "No requests available to delete.");
			return;
		}

		String[] requestStrings = new String[requests.size()];
		for (int i = 0; i < requests.size(); i++) {
			requestStrings[i] = (i + ": " + requests.get(i).toString());
		}

		String selectedValue = (String) JOptionPane.showInputDialog(parent, "Select a request to delete:", "Remove Request", JOptionPane.QUESTION_MESSAGE, null, requestStrings, requestStrings[0]);

		if (selectedValue != null && !selectedValue.isEmpty()) {
			int selectedIndex = Integer.parseInt(selectedValue.split(":")[0]);
			Request removed = requests.remove(selectedIndex);
			JOptionPane.showMessageDialog(parent, "Removed: " + removed);

			StringBuilder updatedList = new StringBuilder("Updated Requests List:\n");
			for (Request request : requests) {
				updatedList.append(request).append("\n");
			}
			JTextArea textArea = new JTextArea(updatedList.toString());
			textArea.setEditable(false);
			JScrollPane scrollPane = new JScrollPane(textArea);
			scrollPane.setPreferredSize(new Dimension(300, 150));
			JOptionPane.showMessageDialog(parent, scrollPane, "Updated Requests", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(parent, "No request was selected for deletion!");
		}
	}

	public static void resolveRequest(List<Request> requests, List<Production> productions, List<User> users, List<Actor> actors, Component parent) {
		String currentUsername = currentUser.username;
		String currentType = String.valueOf(currentUser.userType);

		if (requests.isEmpty()) {
			JOptionPane.showMessageDialog(parent, "No requests available.");
			return;
		}
		StringBuilder requestList = new StringBuilder("Requests:\n");
		for (Request request : requests) {
			requestList.append(request).append("\n");
		}
		JTextArea textArea = new JTextArea(requestList.toString());
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(300, 150));
		JOptionPane.showMessageDialog(parent, scrollPane, "All Requests", JOptionPane.INFORMATION_MESSAGE);

		for (Request request : requests) {
			if (request.getTo().equals(currentUsername)) {
				int resolve = JOptionPane.showConfirmDialog(parent, "Resolve this request? " + request, "Resolve Request", JOptionPane.YES_NO_OPTION);
				if (resolve == JOptionPane.YES_OPTION) {
					if (request.getType() == RequestTypes.ACTOR_ISSUE) {
						updateActor(actors, parent);
					} else if (request.getType() == RequestTypes.MOVIE_ISSUE) {
						updateProduction(productions, parent);
					}
				}
			} else if (request.getTo().equalsIgnoreCase(currentType)) {
				int resolve = JOptionPane.showConfirmDialog(parent, "Resolve this request? " + request, "Resolve Request", JOptionPane.YES_NO_OPTION);
				if (resolve == JOptionPane.YES_OPTION) {
					switch (request.getType()) {
						case DELETE_ACCOUNT:
							removeUser(users, parent);
							break;
						case OTHERS:
							handleOtherRequests(users, actors, productions, parent);
							break;
					}
				}
			}
		}
		removeRequest(requests, parent);
		ExperienceStrategy strategy = new IssueStrategy();
		currentUser.performActionAndUpdateExperience(strategy);
		JOptionPane.showMessageDialog(parent, "Experince" + currentUser.experience);
	}
	private static void showRequest(Component parent) {
		String[] options = {
				"Create Request",
				"Resolve Productions",
		};

		String selectedOption = (String) JOptionPane.showInputDialog(
				parent,
				"Select how you want to view:",
				"Display Options",
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]
		);

		if (selectedOption != null) {
			for (int i = 0; i < options.length; i++) {
				if (selectedOption.equals(options[i])) {
					executeRequest(i + 1, parent);
					break;
				}
			}
		}
	}
	private static void executeRequest(int option, Component parent) {
		switch (option) {
			case 1:
				createRequest(requests, parent);
				break;
			case 2:
				resolveRequest(requests, productions, users, actors, parent);
				break;
			default:
				JOptionPane.showMessageDialog(parent, "Invalid option selected.");
				break;
		}
	}
	private static void handleOtherRequests(List<User> users, List<Actor> actors, List<Production> productions, Component parent) {
		String[] options = {"Add Actor", "Add Production", "Custom Task"};
		String selectedOption = (String) JOptionPane.showInputDialog(
				parent,
				"Select the task you want to perform:",
				"Handle Other Requests",
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]
		);

		if (selectedOption != null) {
			switch (selectedOption) {
				case "Add Actor":
					createActorFromSystem(parent, actors);
					break;
				case "Add Production":
					createNewProductionSeries(parent, productions);
					break;
				default:
					JOptionPane.showMessageDialog(parent, "No valid operation selected.");
					break;
			}
		} else {
			JOptionPane.showMessageDialog(parent, "No option was selected.");
		}
	}
	private static void showUpdate(Component parent) {
		String[] options = {
				"Update Actors",
				"Update Productions",
		};

		String selectedOption = (String) JOptionPane.showInputDialog(
				parent,
				"Select how you want to view:",
				"Update Display Options",
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]
		);

		if (selectedOption != null) {
			for (int i = 0; i < options.length; i++) {
				if (selectedOption.equals(options[i])) {
					executeUpdate(i + 1, parent);
					break;
				}
			}
		}
	}
	private static void executeUpdate(int option, Component parent) {
		switch (option) {
			case 1:
				updateActor(actors, parent);
				break;
			case 2:
				updateProduction(productions, parent);
				break;
			default:
				JOptionPane.showMessageDialog(parent, "Invalid option selected.");
				break;
		}
	}
	public static void updateProduction(List<Production> productions, Component parent) {
		String productionName = JOptionPane.showInputDialog(parent, "Type the name of the Production:");
		Production productionToUpdate = findProductionByName(productions, productionName);

		if (productionToUpdate == null) {
			JOptionPane.showMessageDialog(parent, "There is no production named " + productionName);
			return;
		}

		String[] options = {"Title", "Directors", "Actors", "Genres", "Ratings", "Plot", "Seasons"};
		String updateChoice = (String) JOptionPane.showInputDialog(
				parent, "What do you want to change about your production?",
				"Update Production",
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]);

		if (updateChoice == null) {
			JOptionPane.showMessageDialog(parent, "No option selected. Update canceled.");
			return;
		}

		switch (updateChoice) {
			case "Title":
				String newTitle = JOptionPane.showInputDialog(parent, "Enter the new title for your production");
				productionToUpdate.setTitle(newTitle);
				JOptionPane.showMessageDialog(parent, "New title set: " + productionToUpdate.getTitle());
				break;
			case "Directors":
				String newDirector = JOptionPane.showInputDialog(parent, "Enter the new director for your production");
				productionToUpdate.setDirectors(Collections.singletonList(newDirector));
				JOptionPane.showMessageDialog(parent, "New director set: " + productionToUpdate.getDirectors());
				break;
			case "Actors":
				String currentActors = getActorListAsString(productionToUpdate.getActors());
				String newActors = JOptionPane.showInputDialog(parent, "Enter the new list of actors (comma-separated):", currentActors);
				List<String> actorList = Arrays.asList(newActors.split("\\s*,\\s*"));
				productionToUpdate.setActors(actorList);
				JOptionPane.showMessageDialog(parent, "Updated actors: " + productionToUpdate.getActors());
				break;
			case "Ratings":
				List<Rating> currentRatings = productionToUpdate.getRatings();
				String[] ratingOptions = {"Add Rating", "Remove Rating", "Modify Rating"};
				String ratingChoice = (String) JOptionPane.showInputDialog(parent, "Choose an action for ratings:", "Update Ratings", JOptionPane.QUESTION_MESSAGE, null, ratingOptions, ratingOptions[0]);
				if ("Add Rating".equals(ratingChoice)) {
					String username = JOptionPane.showInputDialog(parent, "Enter rating username:");
					int ratingValue = Integer.parseInt(JOptionPane.showInputDialog(parent, "Enter rating value (0-10):"));
					String comment = JOptionPane.showInputDialog(parent, "Enter comment:");
					currentRatings.add(new Rating(username, ratingValue, comment));
				} else if ("Remove Rating".equals(ratingChoice)) {
					String[] ratingsAsStrings = currentRatings.stream().map(Rating::toString).toArray(String[]::new);
					String selectedRating = (String) JOptionPane.showInputDialog(parent, "Select a rating to remove:", "Remove Rating", JOptionPane.QUESTION_MESSAGE, null, ratingsAsStrings, ratingsAsStrings[0]);
					if (selectedRating != null) {
						int indexToRemove = Arrays.asList(ratingsAsStrings).indexOf(selectedRating);
						if (indexToRemove >= 0) {
							currentRatings.remove(indexToRemove);
						}
					}
				} else if ("Modify Rating".equals(ratingChoice)) {
					String[] ratingsAsStrings = currentRatings.stream().map(Rating::toString).toArray(String[]::new);
					String selectedRating = (String) JOptionPane.showInputDialog(parent, "Select a rating to modify:", "Modify Rating", JOptionPane.QUESTION_MESSAGE, null, ratingsAsStrings, ratingsAsStrings[0]);
					if (selectedRating != null) {
						int indexToModify = Arrays.asList(ratingsAsStrings).indexOf(selectedRating);
						if (indexToModify >= 0) {
							String newUsername = JOptionPane.showInputDialog(parent, "Enter new username:", currentRatings.get(indexToModify).getUsername());
							int newRatingValue = Integer.parseInt(JOptionPane.showInputDialog(parent, "Enter new rating value (0-10):", currentRatings.get(indexToModify).getRating()));
							String newComment = JOptionPane.showInputDialog(parent, "Enter new comment:", currentRatings.get(indexToModify).getComment());
							currentRatings.set(indexToModify, new Rating(newUsername, newRatingValue, newComment));
						}
					}
				}

				productionToUpdate.averageRating = productionToUpdate.calculateRating(ratings);
				productionToUpdate.setRating(currentRatings);
				JOptionPane.showMessageDialog(parent, "Updated ratings.");
				break;
			case "Plot":
				String newPlot = JOptionPane.showInputDialog(parent, "Enter the new plot for your production");
				productionToUpdate.setPlot(newPlot);
				JOptionPane.showMessageDialog(parent, "New plot set: " + productionToUpdate.getPlot());
				break;
			case "Seasons":
				String seasonChoice = (String) JOptionPane.showInputDialog(parent,
						"Which season's episode would you like to modify? (e.g., 'Season 1')",
						"Choose Season",
						JOptionPane.QUESTION_MESSAGE,
						null,
						getSeasonListAsString(productionToUpdate.getSeasons()).toArray(),
						null);
				if (seasonChoice != null) {
					List<Episode> episodes = productionToUpdate.getSeasons().get(seasonChoice);
					String[] episodeOptions = episodes.stream().map(Episode::getEpisodeName).toArray(String[]::new);
					String episodeChoice = (String) JOptionPane.showInputDialog(parent,
							"Choose the episode to modify:",
							"Choose Episode",
							JOptionPane.QUESTION_MESSAGE,
							null,
							episodeOptions,
							episodeOptions[0]);
					if (episodeChoice != null) {
						int episodeIndex = Arrays.asList(episodeOptions).indexOf(episodeChoice);
						Episode episodeToModify = episodes.get(episodeIndex);

						String newEpisodeName = JOptionPane.showInputDialog(parent, "Enter the new name for the episode:", episodeToModify.getEpisodeName());
						if (newEpisodeName != null && !newEpisodeName.trim().isEmpty()) {
							episodeToModify.setEpisodeName(newEpisodeName);
						}

						String newEpisodeDuration = JOptionPane.showInputDialog(parent, "Enter the new duration for the episode:", episodeToModify.getDuration());
						if (newEpisodeDuration != null && !newEpisodeDuration.trim().isEmpty()) {
							episodeToModify.setDuration(newEpisodeDuration);
						}

						episodes.set(episodeIndex, episodeToModify);
						productionToUpdate.getSeasons().put(seasonChoice, episodes);
						JOptionPane.showMessageDialog(parent, "Episode updated: " + episodeToModify);
					}
				} else {
					JOptionPane.showMessageDialog(parent, "No season selected. Update canceled.");
				}
				break;
			default:
				JOptionPane.showMessageDialog(parent, "Invalid option selected.");
				break;
		}
	}
	private static String getActorListAsString(List<String> actors) {
		return String.join(", ", actors);
	}

	private static List<String> getSeasonListAsString(Map<String, List<Episode>> seasons) {
		return new ArrayList<>(seasons.keySet());
	}

	public static void updateActor(List<Actor> actors, Component parent) {
		String actorName = JOptionPane.showInputDialog(parent, "Type the name of the Actor:");
		Actor actorToUpdate = findActorByName(actorName);

		if (actorToUpdate == null) {
			JOptionPane.showMessageDialog(parent, "There is no actor named " + actorName);
			return;
		}

		String[] options = {"Name", "Performances", "Biography"};
		String updateChoice = (String) JOptionPane.showInputDialog(
				parent, "What do you want to change about your actor?",
				"Update Actor",
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]);

		if (updateChoice == null) {
			JOptionPane.showMessageDialog(parent, "No option selected. Update canceled.");
			return;
		}

		switch (updateChoice) {
			case "Name":
				String newNameActor = JOptionPane.showInputDialog(parent, "Enter the new name for your actor");
				actorToUpdate.setName(newNameActor);
				JOptionPane.showMessageDialog(parent, "New name set: " + actorToUpdate.getName());
				break;
			case "Performances":
				String[] performanceChoices = getPerformanceChoices(actorToUpdate.getPerformances());
				String performanceChoice = (String) JOptionPane.showInputDialog(parent,
						"Select the performance to update:",
						"Update Performance",
						JOptionPane.QUESTION_MESSAGE,
						null,
						performanceChoices,
						performanceChoices[0]);

				if (performanceChoice == null) {
					JOptionPane.showMessageDialog(parent, "No performance selected. Update canceled.");
					break;
				}

				int performanceIndex = Arrays.asList(performanceChoices).indexOf(performanceChoice);
				ProductionRole performanceToUpdate = actorToUpdate.getPerformances().get(performanceIndex);

				String[] performanceOptions = {"Title", "Type"};
				String performanceUpdateChoice = (String) JOptionPane.showInputDialog(parent,
						"What do you want to update for this performance?",
						"Update Performance Details",
						JOptionPane.QUESTION_MESSAGE,
						null,
						performanceOptions,
						performanceOptions[0]);

				if (performanceUpdateChoice == null) {
					JOptionPane.showMessageDialog(parent, "No option selected. Update canceled.");
					break;
				}

				if (performanceUpdateChoice.equals("Title")) {
					String newTitle = JOptionPane.showInputDialog(parent, "Enter the new title for the performance");
					performanceToUpdate.setTitle(newTitle);
					JOptionPane.showMessageDialog(parent, "New title set: " + performanceToUpdate.getTitle());
				} else if (performanceUpdateChoice.equals("Type")) {
					String newType = JOptionPane.showInputDialog(parent, "Enter the new type for the performance");
					performanceToUpdate.setType(newType);
					JOptionPane.showMessageDialog(parent, "New type set: " + performanceToUpdate.getType());
				}
				break;
			case "Biography":
				String newBiography = JOptionPane.showInputDialog(parent, "Enter the new biography for your actor:");
				actorToUpdate.setBiography(newBiography);
				JOptionPane.showMessageDialog(parent, "New biography set.");
				break;
			default:
				JOptionPane.showMessageDialog(parent, "Invalid option selected.");
				break;
		}
	}
	private static String[] getPerformanceChoices(List<ProductionRole> performances) {
		String[] choices = new String[performances.size()];
		for (int i = 0; i < performances.size(); i++) {
			ProductionRole p = performances.get(i);
			choices[i] = (i + 1) + ") Title: " + p.getTitle() + ", Type: " + p.getType();
		}
		return choices;
	}
	public static Actor findActorByName(String name) {
		for (Actor actor : actors)
			if (actor.getName().equalsIgnoreCase(name))
				return actor;
		return null;
	}
	public static void removeProductionFromSystem(List<Production> productions, Component parent) {
		if (productions.isEmpty()) {
			JOptionPane.showMessageDialog(parent, "There are no productions in the system to delete.");
			return;
		}

		String[] productionName = productions.stream()
				.map(Production::getTitle)
				.toArray(String[]::new);

		String selectedProductionName = (String) JOptionPane.showInputDialog(parent,
				"Select an production to delete:",
				"Remove Production",
				JOptionPane.QUESTION_MESSAGE,
				null,
				productionName,
				productionName[0]);

		if (selectedProductionName != null) {
			for (int i = 0; i < productions.size(); i++) {
				if (productions.get(i).getTitle().equals(selectedProductionName)) {
					productions.remove(i);
					JOptionPane.showMessageDialog(parent, "Production " + selectedProductionName + " has been removed.");
					return;
				}
			}
		} else {
			JOptionPane.showMessageDialog(parent, "No production was selected for removal.");
		}
	}
	public static void removeActorFromSystem(List<Actor> actors, Component parent) {
		if (actors.isEmpty()) {
			JOptionPane.showMessageDialog(parent, "There are no actors in the system to delete.");
			return;
		}
		String[] actorNames = actors.stream()
				.map(Actor::getName)
				.toArray(String[]::new);

		String selectedActorName = (String) JOptionPane.showInputDialog(parent,
				"Select an actor to delete:",
				"Remove Actor",
				JOptionPane.QUESTION_MESSAGE,
				null,
				actorNames,
				actorNames[0]);

		if (selectedActorName != null) {
			for (int i = 0; i < actors.size(); i++) {
				if (actors.get(i).getName().equals(selectedActorName)) {
					actors.remove(i);
					JOptionPane.showMessageDialog(parent, "Actor " + selectedActorName + " has been removed.");
					return;
				}
			}
		} else {
			JOptionPane.showMessageDialog(parent, "No actor was selected for removal.");
		}
	}
	public static void createActorFromSystem(Component parent, List<Actor> actors) {
		String name = JOptionPane.showInputDialog(parent, "Enter Actor Name:");
		if (name == null || name.trim().isEmpty()) {
			JOptionPane.showMessageDialog(parent, "Actor creation canceled.");
			return;
		}

		int numPerformances = Integer.parseInt(JOptionPane.showInputDialog(parent, "How many performances would you like to enter?"));
		List<ProductionRole> performances = new ArrayList<>();

		for (int i = 0; i < numPerformances; i++) {
			String title = JOptionPane.showInputDialog(parent, "Enter the title of performance " + (i + 1) + ":");
			String type = JOptionPane.showInputDialog(parent, "Enter the type of performance " + (i + 1) + " (Movie/Series):");
			performances.add(new ProductionRole(title, type));

			String biography = JOptionPane.showInputDialog(parent, "Enter Actor Biography:");

			Actor newActor = new Actor(name, performances, biography);
			actors.add(newActor);

			JOptionPane.showMessageDialog(parent, "New actor added: " + newActor);
		}
		ExperienceStrategy strategy = new AddSystemStrategy();
		currentUser.performActionAndUpdateExperience(strategy);
		JOptionPane.showMessageDialog(parent, "Experince " +currentUser.experience);
	}
	public static void createNewProductionMovie(Component parent, List<Production> productions) {
		String title = JOptionPane.showInputDialog(parent, "Enter Production Title:");
		if (title == null || title.trim().isEmpty()) {
			JOptionPane.showMessageDialog(parent, "Production creation canceled.");
			return;
		}
		ProductionType productionType = ProductionType.Movie;

		String directorName = JOptionPane.showInputDialog(parent, "Enter Production Director:");
		List<String> director = Collections.singletonList(directorName);

		int numActors = Integer.parseInt(JOptionPane.showInputDialog(parent, "How many actors would you like to enter?"));
		List<String> actors = new ArrayList<>();
		for (int i = 0; i < numActors; i++) {
			String actor = JOptionPane.showInputDialog(parent, "Enter actor " + (i + 1) + ":");
			actors.add(actor);
		}

		int numGenres = Integer.parseInt(JOptionPane.showInputDialog(parent, "How many genres would you like to enter?"));
		List<Genre> genres = new ArrayList<>();
		for (int i = 0; i < numGenres; i++) {
			String genreStr = JOptionPane.showInputDialog(parent, "Enter genre " + (i + 1) + ":");
			Genre genre = Genre.valueOf(genreStr);
			genres.add(genre);
		}

		List<Rating> ratings = new ArrayList<>();
		while (true) {
			String username = JOptionPane.showInputDialog(parent, "Enter rating username (or 'done' to finish):");
			if ("done".equalsIgnoreCase(username)) {
				break;
			}
			int ratingValue = Integer.parseInt(JOptionPane.showInputDialog(parent, "Enter rating value:"));
			String comment = JOptionPane.showInputDialog(parent, "Enter comment:");
			Rating rating = new Rating(username, ratingValue, comment);
			ratings.add(rating);
		}
		String duration = JOptionPane.showInputDialog(parent, "Enter Production Duration:");
		String plot = JOptionPane.showInputDialog(parent, "Enter Production Plot:");
		int releaseYear = Integer.parseInt(JOptionPane.showInputDialog(parent, "Enter Production Release year:"));
		Double averageRating = Production.calculateRating(ratings);
		Production newProduction = new Movie(title, productionType, director, actors, genres, ratings, duration, plot, releaseYear, averageRating);
		productions.add(newProduction);
		JOptionPane.showMessageDialog(parent, "New production added: " + newProduction);
		ExperienceStrategy strategy = new AddSystemStrategy();
		currentUser.performActionAndUpdateExperience(strategy);
		JOptionPane.showMessageDialog(parent, "Experince " +currentUser.experience);
	}
	public static void createNewProductionSeries(Component parent, List<Production> productions) {
		String title = JOptionPane.showInputDialog(parent, "Enter Production Title:");
		if (title == null || title.trim().isEmpty()) {
			JOptionPane.showMessageDialog(parent, "Production creation canceled.");
			return;
		}
		ProductionType productionType = ProductionType.Series;

		String directorName = JOptionPane.showInputDialog(parent, "Enter Production Director:");
		List<String> director = Collections.singletonList(directorName);

		int numActors = Integer.parseInt(JOptionPane.showInputDialog(parent, "How many actors would you like to enter?"));
		List<String> actors = new ArrayList<>();
		for (int i = 0; i < numActors; i++) {
			String actor = JOptionPane.showInputDialog(parent, "Enter actor " + (i + 1) + ":");
			actors.add(actor);
		}

		int numGenres = Integer.parseInt(JOptionPane.showInputDialog(parent, "How many genres would you like to enter?"));
		List<Genre> genres = new ArrayList<>();
		for (int i = 0; i < numGenres; i++) {
			String genreStr = JOptionPane.showInputDialog(parent, "Enter genre " + (i + 1) + ":");
			Genre genre = Genre.valueOf(genreStr);
			genres.add(genre);
		}

		List<Rating> ratings = new ArrayList<>();
		while (true) {
			String username = JOptionPane.showInputDialog(parent, "Enter rating username (or 'done' to finish):");
			if ("done".equalsIgnoreCase(username)) {
				break;
			}
			int ratingValue = Integer.parseInt(JOptionPane.showInputDialog(parent, "Enter rating value:"));
			String comment = JOptionPane.showInputDialog(parent, "Enter comment:");
			Rating rating = new Rating(username, ratingValue, comment);
			ratings.add(rating);
		}

		String plot = JOptionPane.showInputDialog(parent, "Enter Production Plot:");
		int releaseYear = Integer.parseInt(JOptionPane.showInputDialog(parent, "Enter Production Release year:"));
		int numberOfSeason = Integer.parseInt(JOptionPane.showInputDialog(parent, "Enter Production Number of season:"));

		Map<String, List<Episode>> seasons = new HashMap<>();
		for (int i = 0; i < numberOfSeason; i++) {
			String seasonName = JOptionPane.showInputDialog(parent, "Enter Season " + (i + 1) + " name or number:");

			int numEpisodes = Integer.parseInt(JOptionPane.showInputDialog(parent, "How many episodes are in Season " + seasonName + "?"));
			List<Episode> episodes = new ArrayList<>();
			for (int j = 0; j < numEpisodes; j++) {
				String episodeName = JOptionPane.showInputDialog(parent, "Enter Episode " + (j + 1) + " name:");
				String durationEpisode = JOptionPane.showInputDialog(parent, "Enter Episode " + (j + 1) + " duration (e.g., '42 minutes'):");
				episodes.add(new Episode(episodeName, durationEpisode));
			}
			seasons.put(seasonName, episodes);
		}
		Double averageRating = Production.calculateRating(ratings);
		Production newProduction = new Series(title, productionType, director, actors, genres, ratings, releaseYear, plot, averageRating, numberOfSeason, seasons);
		productions.add(newProduction);
		JOptionPane.showMessageDialog(parent, "New production added: " + newProduction);
		ExperienceStrategy strategy = new AddSystemStrategy();
		currentUser.performActionAndUpdateExperience(strategy);
		JOptionPane.showMessageDialog(parent, "Experince " +currentUser.experience);
	}

	private static void showCreateorDeleteAorP(Component parent) {
		String[] options = {
				"Remove Actors",
				"Remove Productions",
				"Add Actors",
				"Add Productions Movie",
				"Add productions Series"
		};

		String selectedOption = (String) JOptionPane.showInputDialog(
				parent,
				"Select how you want to view:",
				"Display Options",
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]
		);

		if (selectedOption != null) {
			for (int i = 0; i < options.length; i++) {
				if (selectedOption.equals(options[i])) {
					executeCreateorDeleteAorP(i + 1, parent);
					break;
				}
			}
		}
	}
	private static void executeCreateorDeleteAorP(int option, Component parent) {
		switch (option) {
			case 1:
				removeActorFromSystem(actors, parent);
				break;
			case 2:
				removeProductionFromSystem(productions, parent);
				break;
			case 3:
				createActorFromSystem(parent, actors);
				break;
			case 4:
				createNewProductionMovie(parent, productions);
				break;
			case 5:
				createNewProductionSeries(parent, productions);
				break;
			default:
				JOptionPane.showMessageDialog(parent, "Invalid option selected.");
				break;
		}
	}
	public static void removeFavoriteActor(User user, Component parent) {
		List<String> favorites = user.getFavoriteActors();
		if (favorites == null || favorites.isEmpty()) {
			JOptionPane.showMessageDialog(parent, "You don't have any favorite actors!");
			return;
		}

		String[] favoriteArray = favorites.toArray(new String[0]);
		String actorToRemove = (String) JOptionPane.showInputDialog(parent,
				"Select an actor to remove:",
				"Remove Favorite Actor",
				JOptionPane.QUESTION_MESSAGE,
				null,
				favoriteArray,
				favoriteArray[0]);

		if (actorToRemove != null) {
			favorites.remove(actorToRemove);
			JOptionPane.showMessageDialog(parent, "Removed: " + actorToRemove);

			JTextArea textArea = new JTextArea(String.join("\n", favorites));
			textArea.setEditable(false);
			JScrollPane scrollPane = new JScrollPane(textArea);
			scrollPane.setPreferredSize(new Dimension(250, 150));
			JOptionPane.showMessageDialog(parent, scrollPane, "Updated Favorite Actors", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(parent, "No actor was selected for removal!");
		}
	}
	public static void removeFavoriteProduction(User user, Component parent) {
		System.out.println("Your current favorite productions are:");
		List<String> favorites = user.getFavoriteProductions();
		if(favorites.isEmpty() || favorites == null) {
			JOptionPane.showMessageDialog(parent, "You don't have any favorite productions!");
			return;
		}
		String[] favoriteArray = favorites.toArray(new String[0]);
		String productionToRemove = (String) JOptionPane.showInputDialog(parent,
				"Select a production to remove:",
				"Remove Favorite Production",
				JOptionPane.QUESTION_MESSAGE,
				null,
				favoriteArray,
				favoriteArray[0]);

		if (productionToRemove != null) {
			favorites.remove(productionToRemove);
			JOptionPane.showMessageDialog(parent, "Removed: " + productionToRemove);

			JTextArea textArea = new JTextArea(String.join("\n", favorites));
			textArea.setEditable(false);
			JScrollPane scrollPane = new JScrollPane(textArea);
			scrollPane.setPreferredSize(new Dimension(250, 150));
			JOptionPane.showMessageDialog(parent, scrollPane, "Updated Favorite Productions", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(parent, "No production was selected for removal!");
		}
	}
	public static void addFavoriteProduction(User user, Component parent)
	{
		String name = JOptionPane.showInputDialog(parent, "Enter the name of the production you wish to add:");

		if (name == null || name.trim().isEmpty()) {
			JOptionPane.showMessageDialog(parent, "No production was added.");
			return;
		}

		List<String> favorites = user.getFavoriteProductions();
		if (favorites == null) {
			favorites = new ArrayList<>();
			user.setFavoriteProductions(favorites);
		}
		Production productionTitle = findProductionByName(productions, name);
		//daca nu o gasesc afisez mesajul si ies din functie
		if(productionTitle == null)
		{
			JOptionPane.showMessageDialog(parent,"The title doesn't exist in system!");
			return;
		}

		favorites.add(name);
		JOptionPane.showMessageDialog(parent, "Added: " + name);

		StringBuilder allProductions = new StringBuilder();
		for (String production : favorites) {
			allProductions.append(production).append("\n");
		}
		JTextArea textArea = new JTextArea(allProductions.toString());
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(300, 150));
		JOptionPane.showMessageDialog(parent, scrollPane, "Updated Favorite Productions", JOptionPane.INFORMATION_MESSAGE);
	}
	public static void addFavoriteActor(User user, Component parent) {
		String name = JOptionPane.showInputDialog(parent, "Enter the name of the actor you wish to add:");

		if (name == null || name.trim().isEmpty()) {
			JOptionPane.showMessageDialog(parent, "No actor was added.");
			return;
		}

		List<String> favorites = user.getFavoriteActors();
		if (favorites == null) {
			favorites = new ArrayList<>();
			user.setFavoriteActors(favorites);
		}
		Actor actorName = findActorByName(name);
		//daca nu gasesc numele in lista din sistem afisez un mesja si ies din functie
		if(actorName == null)
		{
			JOptionPane.showMessageDialog(parent,"The name doesn't exist in system!");
			return;
		}
		favorites.add(name);
		JOptionPane.showMessageDialog(parent, "Added: " + name);

		StringBuilder allActors = new StringBuilder();
		for (String actor : favorites) {
			allActors.append(actor).append("\n");
		}
		JTextArea textArea = new JTextArea(allActors.toString());
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(300, 150));
		JOptionPane.showMessageDialog(parent, scrollPane, "Updated Favorite Actors", JOptionPane.INFORMATION_MESSAGE);
	}
	public static void createReview(List<Production> productions, Component parent) {

		String productionTitle = JOptionPane.showInputDialog(parent, "Enter the title of the production you want to review:");

		Production production = findProductionByName(productions, productionTitle);

		if (production == null) {
			JOptionPane.showMessageDialog(parent, "Production not found!");
			return;
		}

		List<Rating> ratings = production.getRatings();

		while (true) {
			String username = JOptionPane.showInputDialog(parent, "Enter rating username (or 'done' to finish):");

			if ("done".equalsIgnoreCase(username)) {
				break;
			}

			String ratingValueStr = JOptionPane.showInputDialog(parent, "Enter rating value:");
			int ratingValue;
			try {
				ratingValue = Integer.parseInt(ratingValueStr);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(parent, "Invalid rating value. Please enter a number.");
				continue;
			}

			String comment = JOptionPane.showInputDialog(parent, "Enter comment:");

			Rating rating = new Rating(username, ratingValue, comment);
			ratings.add(rating);
		}

		StringBuilder allRatings = new StringBuilder("All ratings for " + productionTitle + ":\n");
		for (Rating rating : ratings) {
			allRatings.append(rating).append("\n");
		}
		JTextArea textArea = new JTextArea(allRatings.toString());
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(500, 500));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		JOptionPane.showMessageDialog(parent, scrollPane, "Ratings", JOptionPane.INFORMATION_MESSAGE);
		ExperienceStrategy strategy = new ReviewStrategy();
		currentUser.performActionAndUpdateExperience(strategy);
		JOptionPane.showMessageDialog(parent,"Experince " + currentUser.experience);
	}
	public static void viewNotifications(User user, Component parent) {
		StringBuilder builder = new StringBuilder();
		List<String> notifications = user.getNotifications();

		if (notifications == null || notifications.isEmpty()) {
			builder.append("No new notifications.").append('\n');
		} else {
			builder.append("You have the following notifications:").append('\n');
			for (String notification : notifications) {
				builder.append(" - ").append(notification).append('\n');
			}
		}

		JTextArea textArea = new JTextArea(builder.toString());
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(500, 500));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		JOptionPane.showMessageDialog(parent, scrollPane, "Notifications", JOptionPane.INFORMATION_MESSAGE);
	}
	private static void showFavorites(Component parent) {
		String[] options = {
				"Remove Actors",
				"Remove Productions",
				"Add Actors",
				"Add Productions"
		};

		String selectedOption = (String) JOptionPane.showInputDialog(
				parent,
				"Select how you want to view:",
				"Display Options",
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]
		);

		if (selectedOption != null) {
			for (int i = 0; i < options.length; i++) {
				if (selectedOption.equals(options[i])) {
					executeFavorites(i + 1, parent);
					break;
				}
			}
		}
	}
	private static void executeFavorites(int option, Component parent) {
		switch (option) {
			case 1:
				removeFavoriteActor(currentUser, parent);
				break;
			case 2:
				removeFavoriteProduction(currentUser, parent);
				break;
			case 3:
				addFavoriteActor(currentUser, parent);
				break;
			case 4:
				addFavoriteProduction(currentUser, parent);
				break;
			default:
				JOptionPane.showMessageDialog(parent, "Invalid option selected.");
				break;
		}
	}
	private static void showProductionDisplayOptions(Component parent) {
		String[] options = {
				"Display all productions",
				"Display productions by genre",
				"Display productions by year",
				"Display sorted productions by rating",
				"Display popular productions",
				"Display productions chronologically",
				"Display productions by actor",
				"Trailer"
		};

		String selectedOption = (String) JOptionPane.showInputDialog(
				parent,
				"Select how you want to view productions:",
				"Production Display Options",
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]
		);

		if (selectedOption != null) {
			for (int i = 0; i < options.length; i++) {
				if (selectedOption.equals(options[i])) {
					executeProductionDisplayOption(i + 1, parent);
					break;
				}
			}
		}
	}

	private static void showActorDisplayOptions(Component parent) {
		String[] options = {
				"Display actors in alphabetical order",
				"Display actor by movie/series",
				"Search actor by type",
				"Display actors only",
				"Search actor by name",
				"Display American actors"
		};

		String selectedOption = (String) JOptionPane.showInputDialog(
				parent,
				"Select how you want to view actors:",
				"Actor Display Options",
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]
		);

		if (selectedOption != null) {
			for (int i = 0; i < options.length; i++) {
				if (selectedOption.equals(options[i])) {
					executeActorDisplayOption(i + 1, parent);
					break;
				}
			}
		}
	}

	private static void showReview(Component parent) {
		String[] options = {
				"Remove Review",
				"Create review"
		};

		String selectedOption = (String) JOptionPane.showInputDialog(
				parent,
				"Select how do you want:",
				" Display Options",
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]
		);

		if (selectedOption != null) {
			for (int i = 0; i < options.length; i++) {
				if (selectedOption.equals(options[i])) {
					executeReview(i + 1, parent);
					break;
				}
			}
		}
	}
	private static void executeReview(int option, Component parent) {
		switch (option) {
			case 1:
				deleteReview(productions, parent);
				break;
			case 2:
				createReview(productions, parent);
				break;
			default:
				JOptionPane.showMessageDialog(parent, "Invalid option selected.");
				break;
		}
	}
	private static void executeProductionDisplayOption(int option, Component parent) {
		switch (option) {
			case 1:
				printProductions(productions, parent);
				break;
			case 2:
				String genre = JOptionPane.showInputDialog(parent, "Enter the genre:");
				if (genre != null) {
					filterProductionsByGenre(productions, genre, parent);
				}
				break;
			case 3:
				int year = Integer.parseInt(JOptionPane.showInputDialog(parent, "Enter the year:"));
				filterProductionsByYear(productions, year, parent);
				break;
			case 4:
				sortHighProductionsByRating(productions, parent);
				break;
			case 5:
				sortProductionsByRating(productions, parent);
				break;
			case 6:
				sortProductionsChronologically(productions, parent);
				break;
			case 7:
				String actor = JOptionPane.showInputDialog(parent, "Enter the actor:");
				filterProductionsByActor(productions, actor, parent);
				break;
			case 8:
				String title = JOptionPane.showInputDialog(parent, "Enter the title:");
				playTrailer(title, parent);
				break;
			default:
				JOptionPane.showMessageDialog(parent, "Invalid option selected.");
				break;
		}
	}

	private static void executeActorDisplayOption(int option, Component parent) {
		switch (option) {
			case 1:
				displaySortedActors(actors, parent);
				break;
			case 2:
				String movieOrSeriesName = JOptionPane.showInputDialog(parent, "Enter the movie or series title:");
				if (movieOrSeriesName != null) {
					searchActorByMovie(actors, movieOrSeriesName, parent);
				}
				break;
			case 3:
				String type = JOptionPane.showInputDialog(parent, "Enter the type:");
				searchActorByType(actors, type, parent);
				break;
			case 4:
				String wordType = JOptionPane.showInputDialog(parent, "Enter the word(actor):");
				filterActorsByBiography(actors, wordType, parent);
				break;
			case 5:
				String name = JOptionPane.showInputDialog(parent, "Enter the name:");
				searchActorByName(actors, name, parent);
				break;
			case 6:
				String biografyWord = JOptionPane.showInputDialog(parent, "Enter the word(American):");
				filterActorsByBiography(actors, biografyWord, parent);
				break;
			default:
				JOptionPane.showMessageDialog(parent, "Invalid option selected.");
				break;
		}
	}

	private static void printProductions(List<Production> productions, Component parent) {
		StringBuilder builder = new StringBuilder();
		for (Production production : productions) {
			builder.append(production.toString()).append("\n");
		}
		JTextArea textArea = new JTextArea(builder.toString());
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(500, 500));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		JOptionPane.showMessageDialog(parent, scrollPane, "All Productions", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void filterProductionsByGenre(List<Production> productions, String genre, Component parent) {
		StringBuilder builder = new StringBuilder();
		List<Production> filteredProductions = productions.stream()
				.filter(production -> production.getGenres().stream()
						.anyMatch(genre1 -> genre1.name().equalsIgnoreCase(genre)))
				.collect(Collectors.toList());

		for (Production production : filteredProductions) {
			builder.append(production).append("\n");
		}

		JTextArea textArea = new JTextArea(builder.toString());
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(500, 500));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		JOptionPane.showMessageDialog(parent, scrollPane, "Filtered Productions", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void filterProductionsByYear(List<Production> productions, int year, Component parent) {
		StringBuilder builder = new StringBuilder();
		List<Production> filteredProductions = productions.stream()
				.filter(production -> {
					if (production instanceof Series) {
						return ((Series) production).getReleaseYear() == year;
					} else if (production instanceof Movie) {
						return ((Movie) production).getReleaseYear() == year;
					}
					return false;
				})
				.toList();

		for (Production production : filteredProductions) {
			builder.append(production).append("\n");
		}

		JTextArea textArea = new JTextArea(builder.toString());
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(500, 500));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		JOptionPane.showMessageDialog(parent, scrollPane, "Filtered Productions", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void sortHighProductionsByRating(List<Production> productions, Component parent) {
		StringBuilder builder = new StringBuilder();
		List<Production> sortedProductions = productions.stream()
				.sorted(Comparator.comparingDouble(Production::getRating))
				.collect(Collectors.toList());

		for (Production production : sortedProductions) {
			builder.append(production).append("\n");
		}
		JTextArea textArea = new JTextArea(builder.toString());
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(500, 500));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		JOptionPane.showMessageDialog(parent, scrollPane, "Sorted Productions", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void sortProductionsByRating(List<Production> productions, Component parent) {
		StringBuilder builder = new StringBuilder();

		List<Production> sortedProductions = productions.stream()
				.sorted(Comparator.comparingDouble(Production::getRating).reversed())
				.toList();

		for (Production production : sortedProductions) {
			builder.append(production).append("\n");
		}
		JTextArea textArea = new JTextArea(builder.toString());
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(500, 500));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		JOptionPane.showMessageDialog(parent, scrollPane, "Sorted Productions", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void sortProductionsChronologically(List<Production> productions, Component parent) {
		StringBuilder builder = new StringBuilder();

		List<Production> sortedProductions = new ArrayList<>(productions);

		sortedProductions.sort(new Comparator<Production>() {
			@Override
			public int compare(Production p1, Production p2) {
				Integer year1 = getReleaseYear(p1);
				Integer year2 = getReleaseYear(p2);

				if (year1 == null) {
					return (year2 == null) ? 0 : 1;
				}
				if (year2 == null) {
					return -1;
				}

				return year1.compareTo(year2);
			}

			public Integer getReleaseYear(Production production) {
				if (production instanceof Series) {
					return ((Series) production).getReleaseYear();
				} else if (production instanceof Movie) {
					return ((Movie) production).getReleaseYear();
				}
				return null;
			}
		});

		for (Production production : sortedProductions) {
			builder.append(production).append("\n");
		}
		JTextArea textArea = new JTextArea(builder.toString());
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(500, 500));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		JOptionPane.showMessageDialog(parent, scrollPane, "Sorted Productions", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void filterProductionsByActor(List<Production> productions, String actor, Component parent) {
		StringBuilder builder = new StringBuilder();

		List<Production> filteredProductions = productions.stream()
				.filter(production -> production.getActors().stream()
						.anyMatch(actor1 -> actor1.equalsIgnoreCase(actor)))
				.toList();

		for (Production production : filteredProductions) {
			builder.append(production).append("\n");
		}
		JTextArea textArea = new JTextArea(builder.toString());
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(500, 500));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		JOptionPane.showMessageDialog(parent, scrollPane, "Filtered Productions", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void playTrailer(String title, Component parent) {
		String trailerUrl = "";
		String titleLowerCase = title.toLowerCase();
		switch (titleLowerCase) {
			case "the shawshank redemption":
				trailerUrl = "https://www.youtube.com/watch?v=6hB3S9bIaco";
				break;
			case "breaking bad":
				trailerUrl = "https://www.youtube.com/watch?v=HhesaQXLuRY";
				break;
			case "pulp fiction":
				trailerUrl = "https://www.youtube.com/watch?v=s7EdQ4FqbhY";
				break;
			case "the godfather":
				trailerUrl = "https://www.youtube.com/watch?v=sY1S34973zA";
				break;
			case "schindler's list":
				trailerUrl = "https://www.youtube.com/watch?v=JdRGC-w9syA";
				break;
			case "the lord of the rings: the return of the king":
				trailerUrl = "https://www.youtube.com/watch?v=r5X-hFf6Bwo";
				break;
			case "the pianist":
				trailerUrl = "https://www.youtube.com/watch?v=u_jE7-6Uv7E";
				break;
			case "the matrix":
				trailerUrl = "https://www.youtube.com/watch?v=m8e-FF8MsqU";
				break;
			case "sherlock":
				trailerUrl = "https://www.youtube.com/watch?v=qlcWFoNqZHc";
				break;
			case "the dark knight":
				trailerUrl = "https://www.youtube.com/watch?v=EXeTwQWrcwY";
				break;
			case "inception":
				trailerUrl = "https://www.youtube.com/watch?v=YoHD9XEInc0";
				break;
			case "stranger things":
				trailerUrl = "https://www.youtube.com/watch?v=b9EkMc79ZSU&ab_channel=Netflix";
				break;
			case "forrest gump":
				trailerUrl = "https://www.youtube.com/watch?v=uPIEn0M8su0";
				break;
			case "the green mile":
				trailerUrl = "https://www.youtube.com/watch?v=Ki4haFrqSrw";
				break;
			case "inglourious basterds":
				trailerUrl = "https://www.youtube.com/watch?v=KnrRy6kSFF0";
				break;
			case "parasite":
				trailerUrl = "https://www.youtube.com/watch?v=5xH0HfJHsaY";
				break;
			case "joker":
				trailerUrl = "https://www.youtube.com/watch?v=zAGVQLHvwOY";
				break;
			case "1917":
				trailerUrl = "https://www.youtube.com/watch?v=YqNYrYUiMfg";
				break;
			case "the irishman":
				trailerUrl = "https://www.youtube.com/watch?v=WHXxVmeGQUc";
				break;
			case "the mandalorian":
				trailerUrl = "https://www.youtube.com/watch?v=aOC8E8z_ifw";
				break;
			case "the queen's gambit":
				trailerUrl = "https://www.youtube.com/watch?v=oZn3qSgmLqI&ab_channel=RottenTomatoesTV";
				break;
			case "squid game":
				trailerUrl = "https://www.youtube.com/watch?v=oqxAJKy0ii4&ab_channel=Netflix";
				break;
			case "whiplash":
				trailerUrl = "https://www.youtube.com/watch?v=7d_jQycdQGo";
				break;
			case "the social network":
				trailerUrl = "https://www.youtube.com/watch?v=lB95KLmpLR4";
				break;
			case "etenal sunshine of the spotless mind":
				trailerUrl = "https://www.youtube.com/watch?v=1GiLxkDK8sI";
				break;
			case "la la land":
				trailerUrl = "https://www.youtube.com/watch?v=0pdqf4P9MB8";
				break;
			case "the grand budapest hotel":
				trailerUrl = "https://www.youtube.com/watch?v=1Fg5iWmQjwk";
				break;
			case "the shape of water":
				trailerUrl = "https://www.youtube.com/watch?v=XFYWazblaUA";
				break;
			case "black swan":
				trailerUrl = "https://www.youtube.com/watch?v=5jaI1XOB-bs";
				break;
			case "the revenant":
				trailerUrl = "https://www.youtube.com/watch?v=LoebZZ8K5N0";
				break;
			case "mad max: fury road":
				trailerUrl = "https://www.youtube.com/watch?v=hEJnMQG9ev8";
				break;
			case "a beautiful mind":
				trailerUrl = "https://www.youtube.com/watch?v=aS_d0Ayjw4o";
				break;
			case "the departed":
				trailerUrl = "https://www.youtube.com/watch?v=iojhqm0JTW4";
				break;
			case "vasile versaci, cel mai bun pregratar gateste orez cu lapte!":
				trailerUrl = "https://www.youtube.com/watch?v=3FLmCTATy9o&ab_channel=%C8%98oimu%C8%99anDani";
				break;
			default:
				System.out.println("No trailer found for " + title);
				break;
		}

		if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
			try {
				Desktop.getDesktop().browse(new URI(trailerUrl));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public static void searchMovie(List<Production> productions, String movieName,Component parent) {
		StringBuilder builder = new StringBuilder();
		if (movieName == null || movieName.trim().isEmpty()) {
			builder.append("Numele filmului nu poate fi null sau gol.").append("\n");
		} else {
			String lowerCaseMovieName = movieName.toLowerCase().trim();
			boolean found = false;

			for (Production production : productions) {
				if (production.getTitle().toLowerCase().contains(lowerCaseMovieName)) {
					builder.append(production).append("\n");
					found = true;
				}
			}

			if (!found) {
				builder.append("Nu s-au gasit productii pentru filmul: ").append(movieName);
			}
		}

		JTextArea textArea = new JTextArea(builder.toString());
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(500, 500));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		JOptionPane.showMessageDialog(parent, scrollPane, "Search Results", JOptionPane.INFORMATION_MESSAGE);
	}
	public static void displaySortedActors(List<Actor> actors, Component parent) {
		Collections.sort(actors);

		StringBuilder builder = new StringBuilder();
		for (Actor actor : actors) {
			builder.append(actor.toString()).append("\n");
		}
		JTextArea textArea = new JTextArea(builder.toString());
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(500, 500));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		JOptionPane.showMessageDialog(parent, scrollPane, "All actors", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void searchActorByName(List<Actor> actors, String nameToSearch, Component parent) {
		StringBuilder builder = new StringBuilder();
		boolean found = false;
		for (Actor actor : actors) {
			if (actor.getName().equalsIgnoreCase(nameToSearch)) {
				builder.append(actor.toString()).append("\n");
				found = true;
				break;
			}
		}
		if (!found) {
			builder.append("Actor not found: ").append(nameToSearch);
		}

		JTextArea textArea = new JTextArea(builder.toString());
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(500, 500));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		JOptionPane.showMessageDialog(parent, scrollPane, "Search Result", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void filterActorsByBiography(List<Actor> actors, String keyword, Component parent) {
		StringBuilder builder = new StringBuilder();
		if (keyword == null) {
			builder.append("The keyword didn't found: ").append(keyword);
		}

		String lowerCaseKeyword = keyword.toLowerCase();

		for (Actor actor : actors) {
			if (actor.getBiography() != null && actor.getBiography().toLowerCase().contains(lowerCaseKeyword)) {
				builder.append(actor.toString()).append("\n");
			}
		}
		JTextArea textArea = new JTextArea(builder.toString());
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new

				Dimension(500, 500));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		JOptionPane.showMessageDialog(parent, scrollPane, "Search Result", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void searchActorByType(List<Actor> actors, String key, Component parent) {
		StringBuilder builder = new StringBuilder();
		if (key == null) {
			builder.append("The key didn't found: ").append(key);
			return;
		}
		String lowerCaseType = key.toLowerCase();

		for (Actor actor : actors) {
			for (ProductionRole role : actor.getPerformances()) {
				if (role.getType().toLowerCase().contains(lowerCaseType)) {
					builder.append(actor.toString()).append("\n");
					break;
				}
			}
		}
		JTextArea textArea = new JTextArea(builder.toString());
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new

				Dimension(500, 500));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		JOptionPane.showMessageDialog(parent, scrollPane, "Search Result", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void searchActorByMovie(List<Actor> actors, String movieName, Component parent) {
		StringBuilder builder = new StringBuilder();
		if (movieName == null) {
			builder.append("The name didn't be empty :").append(movieName);
			return;
		}
		String lowerCaseMovieName = movieName.toLowerCase();
		boolean found = false;

		for (Actor actor : actors) {
			for (ProductionRole role : actor.getPerformances()) {
				if (role.getTitle().toLowerCase().contains(lowerCaseMovieName)) {
					builder.append(actor.toString()).append("\n");
					found = true;
				}
			}
		}

		if (!found) {
			builder.append("Not found the actors  :").append(movieName);
		}
		JTextArea textArea = new JTextArea(builder.toString());
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new

				Dimension(500, 500));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		JOptionPane.showMessageDialog(parent, scrollPane, "Search Result", JOptionPane.INFORMATION_MESSAGE);

	}
	public static void deleteReview(List<Production> productions, Component parent) {
		String title = JOptionPane.showInputDialog(parent, "Enter the title of the production:");
		Production production = findProductionByName(productions, title);

		if (production != null) {
			List<Rating> ratings = production.getRatings();
			if (!ratings.isEmpty()) {
				String[] ratingStrings = ratings.stream().map(Rating::toString).toArray(String[]::new);
				String selectedRating = (String) JOptionPane.showInputDialog(parent, "Choose a rating to delete:", "Delete Rating", JOptionPane.QUESTION_MESSAGE, null, ratingStrings, ratingStrings[0]);

				if (selectedRating != null) {
					int ratingIndex = List.of(ratingStrings).indexOf(selectedRating);
					ratings.remove(ratingIndex);
					JOptionPane.showMessageDialog(parent, "Rating removed. Updated ratings list: \n" + ratings.stream().map(Rating::toString).collect(Collectors.joining("\n")));
				} else {
					JOptionPane.showMessageDialog(parent, "No rating selected for deletion!");
				}
			} else {
				JOptionPane.showMessageDialog(parent, "No ratings available for this production!");
			}
		} else {
			JOptionPane.showMessageDialog(parent, "Production not found!");
		}
		production.averageRating = production.calculateRating(ratings);
		ExperienceStrategy strategy = new ReviewStrategy();
		currentUser.performActionAndUpdateExperience(strategy);
		JOptionPane.showMessageDialog(parent,"Experince:" + currentUser.experience);
	}
	public static Production findProductionByName(List<Production> productions, String productionName){
		for(Production production : FlowApp.productions)
			if(production.getTitle().equalsIgnoreCase(productionName))
				return production;
		return null;
	}
	public static void movieSeriesActorMenu(Component parent) {
		String[] options = {
				"Search Movie",
				"Search Series",
				"Search Actors"
		};

		String selectedOption = (String) JOptionPane.showInputDialog(
				parent,
				"Select an action:",
				"Movie/Series/Actor Menu",
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]
		);

		if (selectedOption != null) {
			switch (selectedOption) {
				case "Search Movie":
					String nameMovie = JOptionPane.showInputDialog(parent, "Enter the name of the movie:");
					searchMovie(productions, nameMovie, parent);
					break;
				case "Search Series":
					String nameSeries = JOptionPane.showInputDialog(parent, "Enter the name of the series:");
					searchMovie(productions, nameSeries, parent);
					break;
				case "Search Actors":
					String nameActor = JOptionPane.showInputDialog(parent, "Enter the name of the actor:");
					searchActorByName(actors, nameActor, parent);
					break;
				case "Return to main menu":
					break;
				default:
					JOptionPane.showMessageDialog(parent, "Invalid choice.");
					break;
			}
		}
	}

}
