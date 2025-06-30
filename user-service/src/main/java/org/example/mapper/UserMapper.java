package org.example.mapper;

import org.example.dto.CompanyDto;
import org.example.dto.UserDto;
import org.example.dto.UserResponseDto;
import org.example.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);
    User toEntity(UserDto dto);

    @Mapping(target = "id",          source = "user.id")
    @Mapping(target = "firstName",   source = "user.firstName")
    @Mapping(target = "lastName",    source = "user.lastName")
    @Mapping(target = "phoneNumber", source = "user.phoneNumber")
    @Mapping(target = "company",     source = "companyDto")
    UserResponseDto toResponseDto(User user, CompanyDto companyDto);
}