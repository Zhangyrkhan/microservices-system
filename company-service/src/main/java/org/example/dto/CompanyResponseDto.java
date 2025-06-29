package org.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class CompanyResponseDto {
    private Long id;
    private String name;
    private double budget;
    private List<UserDto> users;
}