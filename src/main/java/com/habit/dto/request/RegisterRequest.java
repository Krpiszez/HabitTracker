package com.habit.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Provide user name")
    @Size(min = 4,max = 80)
    private String userName;
    @NotBlank(message = "Provide password")
    @Size(min = 4,max = 80)
    private String password;


}
