package org.example.repository.feedback;

import org.example.model.feedback.Feedback;

import java.util.ArrayList;
import java.util.List;

public class FeedbackRepository {

    private final List<Feedback> data;


    public FeedbackRepository() {
        data = new ArrayList<>();
    }


    public ArrayList<Feedback> getAll() {
        return new ArrayList<>(data);
    }

    public void add(Feedback feedback) {
        data.add(feedback);
    }


    public boolean empty() {
        return data.isEmpty();
    }
}
