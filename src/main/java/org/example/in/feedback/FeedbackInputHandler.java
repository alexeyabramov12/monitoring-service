package org.example.in.feedback;

import org.example.dto.feedback.FeedbackDto;
import org.example.in.InputHandler;
import org.example.out.feedback.FeedbackOutputHandler;

import java.time.LocalDate;
import java.util.Scanner;

public class FeedbackInputHandler extends InputHandler {


    private final FeedbackOutputHandler outputHandler;
    private final Scanner scanner;

    public FeedbackInputHandler() {
        scanner = new Scanner(System.in);
        outputHandler = new FeedbackOutputHandler();
    }



    public FeedbackDto getFeedbackDto(String user) {
        FeedbackDto dto = new FeedbackDto();
        outputHandler.displayMassage("Введите вашу жалобу или предложение:");
        dto.setText(checkEmpty(scanner.nextLine()));
        dto.setDate(LocalDate.now());
        dto.setUser(user);
        return dto;
    }
}
