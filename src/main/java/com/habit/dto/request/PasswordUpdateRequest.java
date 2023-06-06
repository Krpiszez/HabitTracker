package com.habit.dto.request;

import com.habit.domain.Habit;
import com.habit.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordUpdateRequest {

    @NotBlank(message = "Provide password")
    @Size(min = 4,max = 80)
    private String oldPassword;
    @NotBlank(message = "Provide password")
    @Size(min = 4,max = 80)
    private String newPassword;


}
