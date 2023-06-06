package com.habit.controller;


import com.habit.dto.HabitDTO;
import com.habit.dto.UserDTO;
import com.habit.dto.request.PasswordUpdateRequest;
import com.habit.dto.response.HTResponses;
import com.habit.dto.response.ResponseMessages;
import com.habit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<UserDTO> getUser(){
        UserDTO userDTO = userService.getPrincipal();
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/auth/all")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        UserDTO userDTO = userService.getUserDTOById(id);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/habits")
    public ResponseEntity<List<HabitDTO>> getHabitsOfCurrentUser(){
        List<HabitDTO> habits = userService.getHabitsOfCurrentUser();
        return ResponseEntity.ok(habits);
    }

    @PatchMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<HTResponses> updateCurrentUserPassword(@RequestBody PasswordUpdateRequest passwordUpdateRequest){
        userService.updateCurrentUserPassword(passwordUpdateRequest);
        HTResponses response = new HTResponses(ResponseMessages.PASSWORD_UPDATED_MESSAGE, true);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/habit/{habitId}")
    public ResponseEntity<HTResponses> habitAddToUser(@PathVariable Long habitId){
        userService.addHabitToCurrentUser(habitId);
        HTResponses response = new HTResponses(ResponseMessages.HABIT_ADDED_MESSAGE, true);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/habit")
    public ResponseEntity<HTResponses> habitAddToUserByHabitName(@RequestParam String habitName){
        userService.addHabitToCurrentUserByHabitName(habitName);
        HTResponses response = new HTResponses(ResponseMessages.HABIT_ADDED_MESSAGE, true);
        return ResponseEntity.ok(response);
    }




}
