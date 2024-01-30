package org.example.service.feedback;

import org.example.dto.feedback.FeedbackDto;
import org.example.exception.feedback.FeedbackException;
import org.example.mapper.feedback.FeedbackMapper;
import org.example.model.feedback.Feedback;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class FeedbackServiceImpl implements FeedbackService{


    private final List<Feedback> data;
    private final FeedbackMapper mapper;

    public FeedbackServiceImpl() {
        data = new ArrayList<>();
        mapper = new FeedbackMapper();
    }

    @Override
    public void addFeedback(FeedbackDto dto) {
        data.add(mapper.DtoToFeedback(dto));
    }

    @Override
    public Map<LocalDate, List<FeedbackDto>> getAllFeedbacksByDate() {
        return new TreeMap<>(data.stream()
                .map(mapper::feedbackDto)
                .collect(Collectors.groupingBy(FeedbackDto::getDate)));
    }

    @Override
    public Map<LocalDate, List<FeedbackDto>> getAllFeedbacksByMonthByDate(int month) {
        String errorMessage = "Жалоб и предложений за данный месяц не найдено";
        List<Feedback> dataByMonth = new ArrayList<>();
        for (Feedback feedback : data) {
            if (feedback.getDate().getMonth().getValue() == month) {
                dataByMonth.add(feedback);
            }
        }
        if (dataByMonth.isEmpty()) {
            throw new FeedbackException(errorMessage);
        }

        return new TreeMap<>(dataByMonth.stream()
                .map(mapper::feedbackDto)
                .collect(Collectors.groupingBy(FeedbackDto::getDate)));
    }
}
