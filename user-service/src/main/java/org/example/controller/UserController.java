package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.*;
import org.example.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;  // интерфейс

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid UserDto dto) {
        userService.addUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Page<UserDto>> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                userService.getAllUsers(PageRequest.of(page, size))
        );
    }

    @GetMapping("/with-company")
    public ResponseEntity<Page<UserResponseDto>> getWithCompany(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                userService.getUsersWithCompany(PageRequest.of(page, size))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable(name = "id") Long id,
            @RequestBody @Valid UserDto dto
    ) {
        userService.updateUser(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Feign-endpoint for company-service
    @PostMapping("/by-company")
    public ResponseEntity<List<UserDto>> findByCompanyIds(
            @RequestBody List<Long> companyIds
    ) {
        return ResponseEntity.ok(userService.findUsersByCompanyIds(companyIds));
    }
}