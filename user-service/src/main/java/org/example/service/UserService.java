package org.example.service;

import org.example.dto.UserDto;
import org.example.dto.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    void addUser(UserDto dto);
    Page<UserDto> getAllUsers(Pageable pageable);
    Page<UserResponseDto> getUsersWithCompany(Pageable pageable);
    List<UserDto> findUsersByCompanyIds(List<Long> companyIds);
    void updateUser(Long id, UserDto dto);
    void deleteUser(Long id);
}