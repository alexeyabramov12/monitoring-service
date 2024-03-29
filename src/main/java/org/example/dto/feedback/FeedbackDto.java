package org.example.dto.feedback;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDto {

    private String user;
    private LocalDate date;
    private String text;
}
