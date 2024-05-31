package com.marceloHsousa.demo_part_api.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordDto {

    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}