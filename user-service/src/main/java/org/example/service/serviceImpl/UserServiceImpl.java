package org.example.service.serviceImpl;

import lombok.AllArgsConstructor;
import org.example.dto.UserDto;
import org.example.dto.mapping.UserMapping;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapping userMapping;

    @Override
    public Optional<UserDto> getUser(Long id){
        return userRepository.findById(id)
                .map(userMapping::toDto);
    }

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
