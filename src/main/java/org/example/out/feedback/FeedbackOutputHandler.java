package org.example.out.feedback;

import org.example.dto.feedback.FeedbackDto;
import org.example.out.OutputHandler;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;

public class FeedbackOutputHandler extends OutputHandler {

    public void displaySuccessfulAddingReading() {
        String message = "Ваш отзыв принят";
        System.out.println(message);
    }

    public void displayFeedbackMenu() {
        System.out.println("Для просмотра всех жалоб и предложений нажмите -> 1");
        System.out.println("Для просмотра всех жалоб и предложений за конкретный месяц нажмите -> 2");
        System.out.println("Для выхода нвжмите -> 0");
    }

    public void displayAllFeedbacks(Map<LocalDate, List<FeedbackDto>> allFeedbacksByDate) {
        System.out.println("История всех жалоб и предложений:");
        for (Map.Entry<LocalDate, List<FeedbackDto>> entry : allFeedbacksByDate.entrySet()) {
            LocalDate date = entry.getKey();
            System.out.println("Месяц: ".concat(date.getMonth().toString()).concat(" Дата: ").concat(date.toString()));
            System.out.println(getDataForDisplay(entry.getValue()));
        }
    }

    public void displayAllFeedbacksByMonth(int month, Map<LocalDate, List<FeedbackDto>> allFeedbacksByMonthByDate) {
        System.out.println("История показаний всех пользователей за месяц: ".concat(Month.of(month).toString()));
        displayAllFeedbacks(allFeedbacksByMonthByDate);
    }

    private String getDataForDisplay(List<FeedbackDto> data) {
        StringBuilder builder = new StringBuilder();
        for (FeedbackDto dto : data) {
            builder.append("\t\t\tПользователь: \"");
            builder.append(dto.getUser());
            builder.append("\" \n\t\t\tТекст сообщения: ");
            builder.append(dto.getText());
        }
        return builder.toString();
    }

}
