package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.UserDto;
import org.example.service.serviceImpl.UserServiceImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@RequestBody @Valid UserDto userDto) {
        userServiceImpl.addUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/all")
    public ResponseEntity<Page<UserDto>> getAllUsers(
            @RequestParam(name = "page",defaultValue = "0")int page,
            @RequestParam(name = "size", defaultValue = "10")int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserDto> users = userServiceImpl.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable("id") Long id, @RequestBody @Valid UserDto userDto) {
        userServiceImpl.updateUser(id, userDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        return userServiceImpl.deleteUser(id);
    }

}
