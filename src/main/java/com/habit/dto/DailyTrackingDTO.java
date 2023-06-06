package com.habit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyTrackingDTO {

    private Long id;
    private HabitDTO habit;
    private UserDTO user;
    private LocalDate date;
    private boolean completed;

}

