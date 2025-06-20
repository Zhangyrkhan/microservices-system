package org.example.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.CompanyDto;
import org.example.service.serviceImpl.CompanyServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyServiceImpl companyServiceImpl;


    @PostMapping("/create")
    public ResponseEntity<Void> createCompany(@RequestBody @Valid CompanyDto companyDto) {
        companyServiceImpl.addCompany(companyDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/all")
    public ResponseEntity<Page<CompanyDto>> getAllCompanies(
            @RequestParam(name = "page",defaultValue = "0")int page,
            @RequestParam(name = "size", defaultValue = "10")int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CompanyDto> companies = companyServiceImpl.getAllCompanies(pageable);
        return ResponseEntity.ok(companies);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateCompany(@PathVariable("id") Long id, @RequestBody @Valid CompanyDto companyDto) {
        companyServiceImpl.updateCompany(id, companyDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        return companyServiceImpl.deleteCompany(id);
    }
}

