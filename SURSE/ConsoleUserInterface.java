package org.example;

import java.util.Scanner;
//clasa pentru a putea citi de la tastatura e-mailul si parola pentru fiecare user cand vrea sa se conecteze
public class ConsoleUserInterface implements UserInterface {
    @Override
    public void displayOutput(String message) {

        System.out.println(message);
    }
    @Override
    public String getInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
