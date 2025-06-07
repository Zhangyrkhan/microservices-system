package org.example.dto;

import lombok.Data;

import java.util.List;


@Data
public class CompanyDto {
    private Long id;
    private String name;
    private double budget;
    private List<UserDto> employees;
    //private employeeId;
}
