package com.example.userservice.dto;

import jakarta.persistence.Column;
import lombok.Data;


@Data
public class UserDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String address;

    private String email;
}
