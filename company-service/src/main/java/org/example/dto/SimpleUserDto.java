package org.example.dto;

import lombok.Data;

@Data
public class SimpleUserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
}