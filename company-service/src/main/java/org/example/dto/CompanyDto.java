package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class CompanyDto {
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Positive(message = "Budget must be positive")
    private double budget;
}