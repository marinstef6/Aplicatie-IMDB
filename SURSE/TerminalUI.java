package org.example;

import java.util.Scanner;

//clasa Terminal
public class TerminalUI implements UserInterface {

    //metode pentru a citi de la tastaura e-mail si parola in terminal
    private Scanner scanner;

    public TerminalUI() {
        scanner = new Scanner(System.in);
    }

    @Override
    public void displayOutput(String message) {
        System.out.println(message);
    }

    public String getEmail() {
        System.out.print("Enter email: ");
        return scanner.nextLine();
    }

    public String getPassword() {
        System.out.print("Enter password: ");
        return scanner.nextLine();
    }

    @Override
    public String getInput() {
        return scanner.nextLine();
    }
}
