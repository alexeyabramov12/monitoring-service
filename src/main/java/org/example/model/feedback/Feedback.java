package org.example.model.feedback;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

    private String user;
    private LocalDate date;
    private String text;

}
