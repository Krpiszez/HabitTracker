package com.habit.repository;

import com.habit.domain.DailyTracking;
import com.habit.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyTrackingRepository extends JpaRepository<DailyTracking, Long> {

    @Query("select d from DailyTracking d join d.user u where u.id=:userId")
    List<DailyTracking> findAllByUserId(@Param("userId") Long userId);

    Optional<DailyTracking> findByHabitIdAndDate(Long habitId, LocalDate date);

    @Query("select d from DailyTracking d where d.date=:today or d.date=:yesterday")
    List<DailyTracking> findAllByDate(@Param("today") LocalDate today, @Param("yesterday") LocalDate yesterday);
}
