package org.example.dto.mapper;

import org.example.dto.CompanyDto;
import org.example.dto.CompanyResponseDto;
import org.example.dto.UserRequestDto;
import org.example.entity.Company;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface CompanyMapping {
    CompanyDto toDto(Company company);
    Company toEntity(CompanyDto companyDto);
    CompanyResponseDto toResponseDto(Company company, List<UserRequestDto> users);
}
