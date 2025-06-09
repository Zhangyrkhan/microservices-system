package org.example.service;

import org.example.client.CompanyClient;
import org.example.dto.CompanyDto;
import org.example.dto.SimpleCompanyDto;
import org.example.dto.SimpleUserDto;
import org.example.dto.UserDto;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CompanyClient companyClient;

    public UserService(UserRepository userRepository, CompanyClient companyClient) {
        this.userRepository = userRepository;
        this.companyClient = companyClient;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(()-> new RuntimeException("User not found"));
    }

    public List<UserDto> getUsersByCompany(Long companyId) {
        return userRepository.findByCompanyId(companyId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public UserDto createUser(User user) {
        User saved = userRepository.save(user);
        return toDto(saved);
    }
    public UserDto updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found"));
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setPhone(updatedUser.getPhone());
        user.setCompanyId(updatedUser.getCompanyId());
        return toDto(userRepository.save(user));
    }
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<SimpleUserDto> getSimpleUsersByCompany(Long companyId) {
        return userRepository.findByCompanyId(companyId)
                .stream()
                .map(user -> {
                    SimpleUserDto dto = new SimpleUserDto();
                    dto.setId(user.getId());
                    dto.setFirstName(user.getFirstName());
                    dto.setLastName(user.getLastName());
                    dto.setPhone(user.getPhone());
                    return dto;
                }).collect(Collectors.toList());
    }


    private UserDto toDto(User user) {
        SimpleCompanyDto fullCompany = companyClient.getCompanyById(user.getCompanyId());

        SimpleCompanyDto company = new SimpleCompanyDto();
        company.setId(fullCompany.getId());
        company.setName(fullCompany.getName());
        company.setBudget(fullCompany.getBudget());


        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhone(user.getPhone());
        dto.setCompany(company);
        return dto;
    }

}
