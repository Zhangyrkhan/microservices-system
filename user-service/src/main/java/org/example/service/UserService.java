package org.example.service;


import org.example.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;


public interface UserService {

    void addUser(UserDto userDto);
    Page<UserDto> getAllUsers(Pageable pageable);
    void updateUser(Long id, UserDto userDto);
    ResponseEntity<Void> deleteUser(Long id);


}
