package com.habit.dto;

import com.habit.domain.Habit;
import com.habit.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LastTwoDaysTrack {

    private Long id;

    private User user;

    private Habit habit;

    private LocalDate date;

    private boolean completed;

}
