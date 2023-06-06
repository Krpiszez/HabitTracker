package com.habit.repository;

import com.habit.domain.Habit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HabitRepository extends JpaRepository<Habit, Long> {

    Optional<Habit> findByName(String habitName);

    boolean existsByName(String name);
}
