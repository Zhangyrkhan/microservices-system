package org.example.mapper;

import org.example.dto.CompanyDto;
import org.example.dto.CompanyResponseDto;
import org.example.dto.UserDto;
import org.example.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    Company toEntity(CompanyDto dto);
    CompanyDto toDto(Company entity);

    @Mapping(target = "users", source = "users")
    CompanyResponseDto toResponseDto(Company entity, List<UserDto> users);
}