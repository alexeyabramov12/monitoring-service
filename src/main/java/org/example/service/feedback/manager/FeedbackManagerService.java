package org.example.service.feedback.manager;

import org.example.in.feedback.FeedbackInputHandler;
import org.example.out.feedback.FeedbackOutputHandler;
import org.example.service.feedback.FeedbackService;
import org.example.service.feedback.FeedbackServiceImpl;
import org.example.utils.ValidationMonth;

public class FeedbackManagerService {

    private final FeedbackService feedbackService;
    private final FeedbackInputHandler inputHandler;
    private final FeedbackOutputHandler outputHandler;
    private static final String WRONG_COMMAND = "Недопустимая команда";


    public FeedbackManagerService() {
        feedbackService = new FeedbackServiceImpl();
        inputHandler = new FeedbackInputHandler();
        outputHandler = new FeedbackOutputHandler();
    }


    public void addFeedback(String user) {
        feedbackService.addFeedback(inputHandler.getFeedbackDto(user));
        outputHandler.displaySuccessfulAddingReading();
    }

    public void allFeedbacks() {
        boolean run = true;
        while (run) {
            outputHandler.displayFeedbackMenu();
            String input = inputHandler.checkEmpty(inputHandler.getInput());
            switch (input) {
                case "0" ->  run = false;
                case "1" -> showAllFeedback();
                case "2" -> showAllFeedbackByMonth();
                default -> outputHandler.displayMassage(WRONG_COMMAND);
            }
        }
    }

    private void showAllFeedback() {
        outputHandler.displayAllFeedbacks(feedbackService.getAllFeedbacksByDate());
    }

    private void showAllFeedbackByMonth() {
        outputHandler.displayMassage("Введите месяц числом от 1 до 12");
        int month = ValidationMonth.checkMonth(inputHandler.getInput());
        outputHandler.displayAllFeedbacksByMonth(month, feedbackService.getAllFeedbacksByMonthByDate(month));

    }


}
