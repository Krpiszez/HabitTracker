package com.habit.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Enter username!")
    @Size(min = 4, max = 20, message = "Username: '${validatedValue}' must be between {min} and {max} chars long!")
    private String userName;
    @NotBlank(message = "Enter a password!")
    private String password;
}
