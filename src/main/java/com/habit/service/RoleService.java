package com.habit.service;

import com.habit.domain.Role;
import com.habit.domain.RoleType;
import com.habit.exception.ResourceNotFoundException;
import com.habit.exception.errormessages.ErrorMessages;
import com.habit.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;


    public Role findByType(RoleType roleType){
        return roleRepository.findByType(roleType)
                .orElseThrow(()-> new ResourceNotFoundException(String.format(ErrorMessages.ROLE_NOT_FOUND_EXCEPTION, roleType)));
    }
}
