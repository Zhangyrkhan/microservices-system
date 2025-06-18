package org.example.service.serviceImpl;

import lombok.AllArgsConstructor;
import org.example.dto.UserDto;
import org.example.dto.mapping.UserMapping;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
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
    public void addUser(UserDto userdto) {
        User user = userMapping.toEntity(userdto);
        userRepository.save(user);
    }

    @Override
    public Page<UserDto> getAllUsers(Pageable pageable){
        return userRepository.findAll(pageable)
                .map(userMapping::toDto);
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long id){
        if(!userRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public void updateUser(Long id, UserDto updatedUserDto) {
        if(!userRepository.existsById(id)){
            throw new RuntimeException("User with id: " + id + " not found");
        }
        User user = userMapping.toEntity(updatedUserDto);
        user.setId(id);
        userRepository.save(user);
    }
}
