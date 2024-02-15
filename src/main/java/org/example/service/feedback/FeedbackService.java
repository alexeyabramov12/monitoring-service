package org.example.service.feedback;

import org.example.dto.feedback.FeedbackDto;
import org.example.exception.feedback.FeedbackException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface FeedbackService {

    void addFeedback(FeedbackDto dto);
    Map<LocalDate, List<FeedbackDto>> getAllFeedbacksByDate() throws FeedbackException;
    Map<LocalDate, List<FeedbackDto>> getAllFeedbacksByMonthByDate(int month) throws FeedbackException;
}
