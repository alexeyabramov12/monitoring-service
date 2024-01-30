package org.example.dto.reading;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
public class BaseReadingDto {
    private LocalDate date;
    private String indication;
    private String login;
}
