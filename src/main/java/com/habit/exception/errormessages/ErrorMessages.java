package com.habit.exception.errormessages;

public abstract class ErrorMessages {

    public static final String USER_NOT_FOUND_BY_NAME_MESSAGE = "User with name: %s can not be found!";
    public static final String USER_NOT_FOUND_BY_ID_MESSAGE = "User with id: %d can not be found!";
    public static final String JWT_TOKEN_MESSAGE = "Jwt token validation error : %s!";
    public static final String USER_ALREADY_EXIST = "User with user name: %s already exist!";
    public static final String ROLE_NOT_FOUND_EXCEPTION = "Role with type: %s can not be found!";
    public static final String HABIT_NOT_FOUND_BY_ID_MESSAGE = "Habit with id: %d can not be found!";
    public static final String HABIT_NOT_FOUND_BY_NAME_MESSAGE = "Habit with name: %s can not be found!";
    public static final String PRINCIPAL_NOT_FOUND_MESSAGE = "User not found";
    public static final String NOT_PERMITTED_METHOD_MESSAGE = "You don't have any permission to change this data";
    public static final String PASSWORD_NOT_MATCHED = "Your passwords are not matched";

    public static final String RECORD_NOT_FOUND = "Record can not be found!";
}
