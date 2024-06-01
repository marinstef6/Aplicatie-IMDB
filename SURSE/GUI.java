package org.example;

import javax.swing.*;

//functie pentru a putea citi e-mail si parola penrtru user in aplicatie
public class GUI implements UserInterface {

    @Override
    public void displayOutput(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    @Override
    public String getInput() {
        return JOptionPane.showInputDialog("Enter your input:");
    }

}
