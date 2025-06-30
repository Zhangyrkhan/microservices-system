package org.example.mapper;

import org.example.dto.*;
import org.example.entity.Company;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    Company toEntity(CompanyDto dto);
    CompanyDto toDto(Company company);

    @Mapping(target = "users", source = "users")
    CompanyResponseDto toResponseDto(Company company, List<UserDto> users);
}