package com.habit.controller;

import com.habit.domain.DailyTracking;
import com.habit.dto.LastTwoDaysTrack;
import com.habit.dto.RecordForUser;
import com.habit.dto.response.HTResponses;
import com.habit.dto.response.ResponseMessages;
import com.habit.service.DailyTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/track")
public class DailyTrackingController {

    @Autowired
    private DailyTrackingService dailyTrackingService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @PostMapping
    public ResponseEntity<HTResponses> trackHabit(@RequestParam("habitId") Long habitId,
                                                  @RequestParam("completed") Boolean completed){
        dailyTrackingService.trackHabit(habitId, completed);
        HTResponses response = new HTResponses(ResponseMessages.DAILY_TRACKING_CHECK_HABIT_MESSAGE, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @PostMapping("/yesterday")
    public ResponseEntity<HTResponses> trackYesterdaysHabit(@RequestParam("habitId") Long habitId,
                                                  @RequestParam("completed") Boolean completed){
        dailyTrackingService.trackYesterdaysHabit(habitId, completed);
        HTResponses response = new HTResponses(ResponseMessages.DAILY_TRACKING_CHECK_HABIT_MESSAGE, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @DeleteMapping
    public ResponseEntity<HTResponses> untrackHabit(@RequestParam("habitId") Long habitId){
        dailyTrackingService.untrackHabit(habitId);
        HTResponses response = new HTResponses(ResponseMessages.DAILY_TRACKING_CHECK_HABIT_MESSAGE, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @GetMapping
    public ResponseEntity<List<DailyTracking>> getAllTracks(){
        List<DailyTracking> dailyTrackings = dailyTrackingService.getAllTracks();
        return ResponseEntity.ok(dailyTrackings);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @GetMapping("/user")
    public ResponseEntity<List<RecordForUser>> getTrackForUser(){
        List<RecordForUser> records = dailyTrackingService.getTrackForUser();
        return ResponseEntity.ok(records);
    }

    @GetMapping("/auth/user")
    public ResponseEntity<List<LastTwoDaysTrack>> getLastTwoDays(){
        List<LastTwoDaysTrack> lastTwoDays = dailyTrackingService.getLastTwoDays();
        return ResponseEntity.ok(lastTwoDays);
    }
}
