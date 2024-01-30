package org.example;

import org.example.in.InputHandler;
import org.example.model.auth.Role;
import org.example.model.auth.User;
import org.example.out.OutputHandler;
import org.example.service.auth.manager.AuthManagerService;
import org.example.service.feedback.manager.FeedbackManagerService;
import org.example.service.reading.manager.ReadingManagerService;


public class Main {

    private static final InputHandler inputHandler = new InputHandler();
    private static final OutputHandler outputHandler = new OutputHandler();
    private static final AuthManagerService authManagerService = new AuthManagerService();
    private static final ReadingManagerService readingManagerService = new ReadingManagerService();
    private static final FeedbackManagerService feedbackManagerService = new FeedbackManagerService();
    private static User user;
    private static final String WRONG_COMMAND = "Недопустимая команда";


    public static void main(String[] args) {

        while (true) {
            registrationOrAuthenticateUser();
            while (user != null) {
                if (user.getRole().equals(Role.USER)) {
                    mainMenuForUser();
                } else mainMenuForAdmin();
            }
        }
    }

    private static void registrationOrAuthenticateUser() {
        outputHandler.displayHallo();
        String input = inputHandler.getInput();

        try {
            switch (input) {
                case "1" -> user = authManagerService.login();
                case "2" -> authManagerService.register();
                default -> outputHandler.displayMassage(WRONG_COMMAND);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        inputHandler.getInput();
    }

    private static void mainMenuForUser() {
        outputHandler.displayMainMenuForUser(user.getFirstName(), user.getLastName());
        String input = inputHandler.getInput();
        try {
            switch (input) {
                case "0" -> user = null;
                case "1" -> readingManagerService.addReadings(user);
                case "2" -> readingManagerService.actualReadingsByUser(user);
                case "3" -> readingManagerService.readingsByMonth(user);
                case "4" -> readingManagerService.allReadingsByUser(user);
                case "5" -> feedbackManagerService.addFeedback(user.getLogin());
                default -> outputHandler.displayMassage(WRONG_COMMAND);
            }
        } catch (Exception exception) {
            outputHandler.displayMassage(exception.getMessage());
        }
        inputHandler.getInput();
    }

    private static void mainMenuForAdmin() {
        outputHandler.displayMainMenuForAdmin(user.getFirstName(), user.getLastName());
        String input = inputHandler.getInput();
        try {
            switch (input) {
                case "0" -> user = null;
                case "1" -> readingManagerService.addReadings(authManagerService.getUserByLogin());
                case "2" -> readingManagerService.allActualReadings();
                case "3" -> readingManagerService.allReadingsByMonth();
                case "4" -> readingManagerService.allReadings();
                case "5" -> feedbackManagerService.allFeedbacks();
                 default -> outputHandler.displayMassage(WRONG_COMMAND);
            }
        } catch (Exception exception) {
            outputHandler.displayMassage(exception.getMessage() + "\n");
        }
        inputHandler.getInput();
    }

}