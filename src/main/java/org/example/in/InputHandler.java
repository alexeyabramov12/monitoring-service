package org.example.in;


import java.util.Scanner;

public class InputHandler {

    private final Scanner scanner = new Scanner(System.in);


    public String getInput() {
        return scanner.nextLine().trim();
    }

    public String checkEmpty(String input) {
        if (input.isEmpty()) throw new RuntimeException("Введена пустая строка");
        else return input.trim();
    }

}
