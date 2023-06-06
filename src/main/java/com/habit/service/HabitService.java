package com.habit.service;

import com.habit.domain.Habit;
import com.habit.domain.User;
import com.habit.dto.HabitDTO;
import com.habit.dto.UserDTO;
import com.habit.dto.request.HabitRequest;
import com.habit.exception.ConflictException;
import com.habit.exception.ResourceNotFoundException;
import com.habit.exception.errormessages.ErrorMessages;
import com.habit.repository.HabitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HabitService {

    @Autowired
    private HabitRepository habitRepository;
    public void createHabit(HabitRequest habitRequest) {
        Habit habit = new Habit();
        boolean exist = habitRepository.existsByName(habitRequest.getName());
        if (exist){
            throw new ConflictException("Habit already exist!");
        }
        habit.setName(habitRequest.getName());
        habitRepository.save(habit);
    }

    public List<HabitDTO> getAllHabits() {
        List<Habit> habits = habitRepository.findAll();
        List<HabitDTO> dtoList = new ArrayList<>();
        for (Habit h: habits){
            HabitDTO habitDTO = new HabitDTO();
            habitDTO.setName(h.getName());
            habitDTO.setId(h.getId());
            dtoList.add(habitDTO);
        }
        return dtoList;
    }

    public Habit getHabitById(Long id){
        return habitRepository.findById(id).orElseThrow(()->{
            throw new ResourceNotFoundException(String.format(ErrorMessages.HABIT_NOT_FOUND_BY_ID_MESSAGE,id));
        });
    }

    public HabitDTO getHabitByIdDTO(Long id) {
        Habit habit = getHabitById(id);
        HabitDTO habitDTO = new HabitDTO();
        habitDTO.setName(habit.getName());
        habitDTO.setId(habit.getId());
        return habitDTO;
    }

    public Habit getHabitByName(String habitName) {
        return habitRepository.findByName(habitName).orElseThrow(()->{
            throw new ResourceNotFoundException(String.format(ErrorMessages.HABIT_NOT_FOUND_BY_NAME_MESSAGE,habitName));
        });
    }
}
