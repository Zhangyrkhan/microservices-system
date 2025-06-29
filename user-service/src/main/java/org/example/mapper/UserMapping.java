package org.example.mapper;

import org.example.dto.CompanyDto;
import org.example.dto.UserDto;
import org.example.dto.UserResponseDto;
import org.example.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapping {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);

    @Mapping(target = "company", source = "companyDto")
    UserResponseDto toResponseDto(User user, CompanyDto companyDto);

}
