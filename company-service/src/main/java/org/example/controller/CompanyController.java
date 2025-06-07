package org.example.controller;


import lombok.RequiredArgsConstructor;
import org.example.dto.CompanyDto;
import org.example.entity.Company;
import org.example.repository.CompanyRepository;
import org.example.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public List<CompanyDto> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/{id}")
    public CompanyDto getCompany(@PathVariable("id") Long id) {
        return companyService.getCompanyById(id);
    }
    @PutMapping("/{companyId}/addEmployee/{userId}")
    public void addEmployeeToCompany(@PathVariable Long companyId, @PathVariable Long userId) {
        companyService.addEmployeeToCompany(companyId, userId);
    }


    @PostMapping
    public CompanyDto createCompany(@RequestBody Company company) {
        return companyService.createCompany(company);
    }

    @PutMapping("/{id}")
    public CompanyDto updateCompany(@PathVariable("id") Long id, @RequestBody Company company) {
        return companyService.updateCompany(id, company);
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable("id") Long id) {
        companyService.deleteCompany(id);
    }
}

