package com.habit.service;

import com.habit.domain.DailyTracking;
import com.habit.domain.Habit;
import com.habit.domain.User;
import com.habit.dto.LastTwoDaysTrack;
import com.habit.dto.RecordForUser;
import com.habit.exception.ResourceNotFoundException;
import com.habit.exception.errormessages.ErrorMessages;
import com.habit.repository.DailyTrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DailyTrackingService {

    @Autowired
    private DailyTrackingRepository dailyTrackingRepository;

    @Autowired
    private HabitService habitService;

    @Autowired
    private UserService userService;

    public void trackHabit(Long habitId, Boolean completed) {

        DailyTracking trackedHabit = new DailyTracking();
        trackedHabit.setHabit(habitService.getHabitById(habitId));
        trackedHabit.setUser(userService.getCurrentUser());
        trackedHabit.setDate(LocalDate.now());
        trackedHabit.setCompleted(completed);

        dailyTrackingRepository.save(trackedHabit);
    }

    public void trackYesterdaysHabit(Long habitId, Boolean completed) {

        DailyTracking trackedHabit = new DailyTracking();
        trackedHabit.setHabit(habitService.getHabitById(habitId));
        trackedHabit.setUser(userService.getCurrentUser());
        trackedHabit.setDate(LocalDate.now().minusDays(1));
        trackedHabit.setCompleted(completed);

        dailyTrackingRepository.save(trackedHabit);
    }

    public List<DailyTracking> getAllTracks() {
        return dailyTrackingRepository.findAll();
    }

    public List<RecordForUser> getTrackForUser() {
        User user = userService.getCurrentUser();
        List<DailyTracking> records = dailyTrackingRepository.findAllByUserId(user.getId());
        List<RecordForUser> trackedRecords = new ArrayList<>();
        for (DailyTracking r: records){
            RecordForUser trackedRecord = new RecordForUser();
            trackedRecord.setUser(user);
            trackedRecord.setHabit(r.getHabit());
            trackedRecord.setDate(r.getDate());
            trackedRecord.setCompleted(r.isCompleted());
            trackedRecord.setId(r.getId());
            trackedRecords.add(trackedRecord);
        }
        return trackedRecords;
    }

    public Habit getHabit(Long id){
        return habitService.getHabitById(id);
    }

    public void untrackHabit(Long habitId) {
        DailyTracking dailyTracking = getTrackByHabitId(habitId);
        dailyTrackingRepository.delete(dailyTracking);
    }

    public void untrackYesterdaysHabit(Long habitId) {
        DailyTracking dailyTracking = getYesterdaysTrackByHabitId(habitId);
        dailyTrackingRepository.delete(dailyTracking);
    }
    public DailyTracking getTrackById(Long id){
        return dailyTrackingRepository.findById(id).orElseThrow(
                ()->{throw new ResourceNotFoundException("Track Not Found!");}
        );
    }
    public DailyTracking getTrackByHabitId(Long habitId){
        LocalDate date = LocalDate.now();
        return dailyTrackingRepository.findByHabitIdAndDate(habitId, date).orElseThrow(
                ()->{throw new ResourceNotFoundException("Track Not Found!");}
        );
    }

    public DailyTracking getYesterdaysTrackByHabitId(Long habitId){
        LocalDate date = LocalDate.now().minusDays(1);
        return dailyTrackingRepository.findByHabitIdAndDate(habitId, date).orElseThrow(
                ()->{throw new ResourceNotFoundException("Track Not Found!");}
        );
    }

    public List<LastTwoDaysTrack> getLastTwoDays() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = LocalDate.now().minusDays(1);
        List<DailyTracking> lastTwoDays = dailyTrackingRepository.findAllByDate(today, yesterday);
        List<LastTwoDaysTrack> lastTwoDaysTracks = new ArrayList<>();
        for (DailyTracking d: lastTwoDays){
            if (d.getUser().getUserName().equalsIgnoreCase("mustafa") || d.getUser().getUserName().equalsIgnoreCase("faruk") ||
                    d.getUser().getUserName().equalsIgnoreCase("mehmet") ||d.getUser().getUserName().equalsIgnoreCase("enes") ||
                    d.getUser().getUserName().equalsIgnoreCase("omer") ||d.getUser().getUserName().equalsIgnoreCase("ahmet")) {
                LastTwoDaysTrack el = new LastTwoDaysTrack(d.getId(), d.getUser(), d.getHabit(), d.getDate(), d.isCompleted());
                lastTwoDaysTracks.add(el);
            }
        }
        return lastTwoDaysTracks;
    }



}
