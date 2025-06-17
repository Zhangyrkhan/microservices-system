package org.example.service;


import org.example.dto.UserDto;
import org.example.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserDto> getUser(Long id);
    void addUser(User user);
    List<User> getAllUsers();


}
