package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.*;
import org.example.service.CompanyService;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid CompanyDto dto) {
        companyService.addCompany(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Page<CompanyDto>> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<CompanyDto> result = companyService.getAllCompanies(PageRequest.of(page, size));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/with-users")
    public ResponseEntity<Page<CompanyResponseDto>> getWithUsers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<CompanyResponseDto> result =
                companyService.getCompaniesWithUsers(PageRequest.of(page, size));
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable(name = "id") Long id,
            @RequestBody @Valid CompanyDto dto
    ) {
        companyService.updateCompany(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }

    // Feign-endpoint для user-service
    @PostMapping("/by-id")
    public ResponseEntity<List<CompanyDto>> findByIds(@RequestBody List<Long> ids) {
        List<CompanyDto> list = companyService.getCompanyById(ids);
        return ResponseEntity.ok(list);
    }
}