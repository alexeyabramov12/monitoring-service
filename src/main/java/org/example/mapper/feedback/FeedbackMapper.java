package org.example.mapper.feedback;

import org.example.dto.feedback.FeedbackDto;
import org.example.model.feedback.Feedback;


public class FeedbackMapper {

    public Feedback DtoToFeedback(FeedbackDto dto) {
        if (dto == null) {
            return null;
        }

        return Feedback.builder()
                .user(dto.getUser())
                .date(dto.getDate())
                .text(dto.getText())
                .build();
    }

    public FeedbackDto feedbackDto(Feedback feedback) {
        if (feedback == null) {
            return null;
        }

        return FeedbackDto.builder()
                .user(feedback.getUser())
                .date(feedback.getDate())
                .text(feedback.getText())
                .build();
    }
}
