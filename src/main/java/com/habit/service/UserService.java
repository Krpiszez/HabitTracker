package com.habit.service;

import com.habit.domain.Habit;
import com.habit.domain.Role;
import com.habit.domain.RoleType;
import com.habit.domain.User;
import com.habit.dto.HabitDTO;
import com.habit.dto.UserDTO;
import com.habit.dto.request.PasswordUpdateRequest;
import com.habit.dto.request.RegisterRequest;
import com.habit.exception.BadRequestException;
import com.habit.exception.ConflictException;
import com.habit.exception.ResourceNotFoundException;
import com.habit.exception.errormessages.ErrorMessages;
import com.habit.repository.UserRepository;
import com.habit.security.SecurityUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleService roleService;
    private HabitService habitService;

    public UserService (UserRepository userRepository, RoleService roleService, @Lazy PasswordEncoder passwordEncoder, HabitService habitService) {
        this.userRepository=userRepository;
        this.roleService=roleService;
        this.passwordEncoder=passwordEncoder;
        this.habitService = habitService;
    }


    public User getUserByUserName(String username) {
        return userRepository.findByUserName(username).orElseThrow(() -> {
            throw new ResourceNotFoundException(String.format(ErrorMessages.USER_NOT_FOUND_BY_NAME_MESSAGE, username));
        });
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException(String.format(ErrorMessages.USER_NOT_FOUND_BY_ID_MESSAGE, id));
        });
    }


//    public void registerUser2(Long habitId, RegisterRequest registerRequest) {
//        if (userRepository.existsByUserName(registerRequest.getUserName())){
//            throw new ConflictException(String.format(ErrorMessages.USER_ALREADY_EXIST, registerRequest.getUserName()));
//        }
//        User user = new User();
//        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
//        Role role = roleService.findByType(RoleType.ROLE_CUSTOMER);
//        Set<Role> roles = new HashSet<>();
//        roles.add(role);
//        user.setUserName(registerRequest.getUserName());
//        user.setRoles(roles);
//        user.setPassword(encodedPassword);
//        Habit habit = habitService.getHabitById(habitId);
//        Set<Habit> habits = new HashSet<>();
//        habits.add(habit);
//        user.setHabits(habits);
//        userRepository.save(user);
//    }

    public void registerUser(RegisterRequest registerRequest) {
        if (userRepository.existsByUserName(registerRequest.getUserName())){
            throw new ConflictException(String.format(ErrorMessages.USER_ALREADY_EXIST, registerRequest.getUserName()));
        }
        User user = new User();
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        Role role = roleService.findByType(RoleType.ROLE_CUSTOMER);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setUserName(registerRequest.getUserName());
        user.setRoles(roles);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> dtoList = new ArrayList<>();
        for (User u: users){
            UserDTO userDTO = new UserDTO();
            userDTO.setUserName(u.getUserName());
            userDTO.setId(u.getId());
            userDTO.setHabits(mapHabitSetToHabitIdSet(u));
            userDTO.setRoles(mapRoleSetToStringSet(u.getRoles()));
            dtoList.add(userDTO);
        }
        return dtoList;
    }

    public UserDTO getUserDTOById(Long id) {
        User user = getUserById(id);
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(user.getUserName());
        userDTO.setId(user.getId());
        userDTO.setHabits(mapHabitSetToHabitIdSet(user));
        userDTO.setRoles(mapRoleSetToStringSet(user.getRoles()));
        return userDTO;
    }

    public UserDTO getPrincipal() {
        User currentUser = getCurrentUser();
        Set<Long> habitsIds = mapHabitSetToHabitIdSet(currentUser);
        Set<String> roleStr = mapRoleSetToStringSet(currentUser.getRoles());
        return new UserDTO(currentUser.getId(), currentUser.getUserName(), habitsIds, roleStr);
    }

    private static Set<Long> mapHabitSetToHabitIdSet(User user) {
        Set<Habit> habits = user.getHabits();
        return habits.stream().map(Habit::getId).collect(Collectors.toSet());
    }

    private static Set<String> mapRoleSetToStringSet(Set<Role> roles) {
        Set<String>roleStr = new HashSet<>();
        roles.forEach(r->roleStr.add(r.getType().getName()));
        return roleStr;
    }

    public User getCurrentUser(){
        String userName = SecurityUtils.getCurrentUserLogin().orElseThrow(()->
                new ResourceNotFoundException(ErrorMessages.PRINCIPAL_NOT_FOUND_MESSAGE));
        return getUserByUserName(userName);
    }

    public void updateCurrentUserPassword(PasswordUpdateRequest passwordUpdateRequest) {
        User user = getCurrentUser();

        if(user.getBuiltIn()){
            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }

        if(!passwordEncoder.matches(passwordUpdateRequest.getOldPassword(), user.getPassword())){
            throw new BadRequestException(ErrorMessages.PASSWORD_NOT_MATCHED);
        }

        String hashedPassword = passwordEncoder.encode(passwordUpdateRequest.getNewPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);

    }

    public void addHabitToCurrentUser(Long habitId) {
        User user = getCurrentUser();
        Habit habit = habitService.getHabitById(habitId);
        Set<Habit> userHabits = user.getHabits();
        userHabits.add(habit);
        user.setHabits(userHabits);
        userRepository.save(user);
    }

    public List<HabitDTO> getHabitsOfCurrentUser() {
        User user = getCurrentUser();
        Set<Habit> userHabits = user.getHabits();
        List<Habit> hl = new ArrayList<>(userHabits);
        List<HabitDTO> dtoList = new ArrayList<>();
        for (Habit h: hl){
            HabitDTO habitDTO = new HabitDTO();
            habitDTO.setName(h.getName());
            habitDTO.setId(h.getId());
            dtoList.add(habitDTO);
        }
        return dtoList;
    }

    public void addHabitToCurrentUserByHabitName(String habitName) {
        User user = getCurrentUser();
        Habit habit = habitService.getHabitByName(habitName);
        user.getHabits().add(habit);
        userRepository.save(user);
    }
}
