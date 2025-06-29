package org.example.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.example.client.CompanyClient;
import org.example.dto.CompanyDto;
import org.example.dto.UserDto;
import org.example.dto.UserResponseDto;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CompanyClient companyClient;

    @Override
    public void addUser(UserDto dto) {
        userRepository.save(userMapper.toEntity(dto));
    }

    @Override
    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toDto);
    }

    @Override
    public Page<UserResponseDto> getUsersWithCompany(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);
        List<Long> companyIds = page.getContent().stream()
                .map(User::getCompanyId)
                .distinct()
                .toList();

        List<CompanyDto> companies = companyClient.getCompaniesByIds(companyIds);
        Map<Long, CompanyDto> byId = companies.stream()
                .collect(Collectors.toMap(CompanyDto::getId, Function.identity()));

        return page.map(u -> {
            CompanyDto c = byId.get(u.getCompanyId());
            return userMapper.toResponseDto(u, c);
        });
    }

    @Override
    public List<UserDto> findUsersByCompanyIds(List<Long> companyIds) {
        return userRepository.findAllByCompanyIdIn(companyIds).stream()
                .map(userMapper::toDto)
                .toList();
    }


    @Override
    public void updateUser(Long id, UserDto dto) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User " + id + " not found");
        }
        User u = userMapper.toEntity(dto);
        u.setId(id);
        userRepository.save(u);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User " + id + " not found");
        }
        userRepository.deleteById(id);
    }
}