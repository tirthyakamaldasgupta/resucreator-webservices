package com.resucreator.webservices.dto.user;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNameChecker {
    @NotBlank
    private String userName;
}
