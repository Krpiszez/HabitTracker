package com.habit.controller;

import com.habit.domain.Habit;
import com.habit.dto.HabitDTO;
import com.habit.dto.request.HabitRequest;
import com.habit.dto.response.HTResponses;
import com.habit.dto.response.ResponseMessages;
import com.habit.service.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habit")
public class HabitController {

    @Autowired
    private HabitService habitService;

    @PostMapping
    public ResponseEntity<HTResponses> createHabit(@RequestBody HabitRequest habitRequest){
        habitService.createHabit(habitRequest);
        HTResponses response = new HTResponses(ResponseMessages.HABIT_CREATED_RESPONSE, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<HabitDTO>> getAllHabits(){
        List<HabitDTO> habits = habitService.getAllHabits();
        return ResponseEntity.ok(habits);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabitDTO> getHabitById(@PathVariable("id") Long id){
        HabitDTO habitDTO = habitService.getHabitByIdDTO(id);
        return ResponseEntity.ok(habitDTO);
    }





}
