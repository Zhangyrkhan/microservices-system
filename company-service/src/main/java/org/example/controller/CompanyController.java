package org.example.controller;


import lombok.RequiredArgsConstructor;
import org.example.dto.CompanyDto;
import org.example.dto.SimpleCompanyDto;
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
    private  final CompanyRepository companyRepository;

    public CompanyController(CompanyService companyService, CompanyRepository companyRepository) {
        this.companyService = companyService;
        this.companyRepository = companyRepository;
    }

    @GetMapping
    public List<CompanyDto> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/{id}")
    public CompanyDto getCompany(@PathVariable("id") Long id) {
        return companyService.getCompanyById(id);
    }

    @GetMapping("/simple/{id}")
    public SimpleCompanyDto getSimpleCompanyById(@PathVariable("id") Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        SimpleCompanyDto dto = new SimpleCompanyDto();
        dto.setId(company.getId());
        dto.setName(company.getName());
        dto.setBudget(company.getBudget());
        return dto;
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

