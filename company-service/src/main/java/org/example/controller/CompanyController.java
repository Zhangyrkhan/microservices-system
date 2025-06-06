package org.example.controller;


import lombok.RequiredArgsConstructor;
import org.example.dto.CompanyDto;
import org.example.repository.CompanyRepository;
import org.example.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyDto> createCompany(@RequestBody CompanyDto companyDto){
            return ResponseEntity.ok(companyService.createCompany(companyDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getCompany(@PathVariable("id") Long id){
        return ResponseEntity.ok(companyService.getCompany(id));
    }

    @GetMapping
    public ResponseEntity<List<CompanyDto>> getCompanies(){
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDto> updateCompany(@PathVariable("id") Long id, @RequestBody CompanyDto companyDto){
        return ResponseEntity.ok(companyService.updateCompany(id, companyDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CompanyDto> deleteCompany(@PathVariable("id") Long id){
        companyService.deleteCompany(id);
        return ResponseEntity.ok().build();
    }
}
