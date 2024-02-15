package org.example.service.feedback;

import org.example.dto.feedback.FeedbackDto;
import org.example.exception.feedback.FeedbackException;
import org.example.mapper.feedback.FeedbackMapper;
import org.example.model.feedback.Feedback;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class FeedbackServiceImplTest {


    private FeedbackServiceImpl feedbackService;
    private FeedbackMapper mapper;
    private FeedbackDto dto1;
    private FeedbackDto dto2;
    private List<Feedback> data;
    private Map<LocalDate, List<FeedbackDto>> expect;


    @BeforeEach
    public void setUp() {
        feedbackService = new FeedbackServiceImpl();
        dto1 = FeedbackDto.builder()
                .date(LocalDate.now())
                .text("TEXT")
                .user("ALEX")
                .build();
        dto2 = FeedbackDto.builder()
                .date(LocalDate.now())
                .text("TEXT")
                .user("ALEX")
                .build();
        data = new ArrayList<>();
        mapper = new FeedbackMapper();

    }

    @Test
    @DisplayName("Test get all feedbacks by date with one dto")
    public void ifOneDto_getAllFeedbacksByDate_thenData() {
        feedbackService.addFeedback(dto1);
        data.add(mapper.DtoToFeedback(dto1));
        expect = new TreeMap<>(data.stream()
                .map(mapper::feedbackDto)
                .collect(Collectors.groupingBy(FeedbackDto::getDate)));

        assertEquals(expect, feedbackService.getAllFeedbacksByDate());
    }

    @Test
    @DisplayName("Test get all feedbacks by date with two dto")
    public void ifTwoDto_getAllFeedbacksByDate_thenData() {
        feedbackService.addFeedback(dto1);
        feedbackService.addFeedback(dto2);
        data.add(mapper.DtoToFeedback(dto1));
        data.add(mapper.DtoToFeedback(dto2));
        expect = new TreeMap<>(data.stream()
                .map(mapper::feedbackDto)
                .collect(Collectors.groupingBy(FeedbackDto::getDate)));

        assertEquals(expect, feedbackService.getAllFeedbacksByDate());
    }

    @Test
    @DisplayName("Test get all feedbacks by date with no adding")
    public void ifNoAdding_getAllFeedbacksByDate_thenException() {
        assertThrows(FeedbackException.class, () -> feedbackService.getAllFeedbacksByDate());
    }

    @Test
    @DisplayName("Test get all feedbacks by month By date with one dto")
    public void ifOneDto_getAllFeedbacksByMonthByDate_thenDataByMonth() {
        feedbackService.addFeedback(dto1);
        data.add(mapper.DtoToFeedback(dto1));
        expect = new TreeMap<>(data.stream()
                .map(mapper::feedbackDto)
                .collect(Collectors.groupingBy(FeedbackDto::getDate)));

        assertEquals(expect, feedbackService.getAllFeedbacksByMonthByDate(dto1.getDate().getMonth().getValue()));
    }

    @Test
    @DisplayName("Test get all feedbacks by month By date with two dto")
    public void ifTwoDto_getAllFeedbacksByMonthByDate_thenDataByMonth() {
        feedbackService.addFeedback(dto1);
        feedbackService.addFeedback(dto2);
        data.add(mapper.DtoToFeedback(dto1));
        data.add(mapper.DtoToFeedback(dto2));
        expect = new TreeMap<>(data.stream()
                .map(mapper::feedbackDto)
                .collect(Collectors.groupingBy(FeedbackDto::getDate)));

        assertEquals(expect, feedbackService.getAllFeedbacksByMonthByDate(dto1.getDate().getMonth().getValue()));
    }

    @Test
    @DisplayName("Test get all feedbacks by month By date with two dto")
    public void ifTwoDtoWithDifferentMonth_getAllFeedbacksByMonthByDate_thenDataByMonth() {
        dto2.setDate(dto2.getDate().plusMonths(1));
        feedbackService.addFeedback(dto1);
        feedbackService.addFeedback(dto2);
        data.add(mapper.DtoToFeedback(dto1));
        expect = new TreeMap<>(data.stream()
                .map(mapper::feedbackDto)
                .collect(Collectors.groupingBy(FeedbackDto::getDate)));

        assertEquals(expect, feedbackService.getAllFeedbacksByMonthByDate(dto1.getDate().getMonth().getValue()));
    }

    @Test
    @DisplayName("Test get all Feedbacks by month by date with no adding")
    public void ifNoAdding_getAllFeedbacksByMonthByDate_thenException() {
        assertThrows(FeedbackException.class, () -> feedbackService.getAllFeedbacksByMonthByDate(1));
    }

}
