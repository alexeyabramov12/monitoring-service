package org.example.model.reading;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BaseReading {
     private LocalDate date;
     private String indication;
     private String login;
}
