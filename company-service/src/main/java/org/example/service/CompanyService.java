package org.example.service;

import org.example.dto.CompanyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CompanyService {

    void addCompany (CompanyDto companyDto);
    Page<CompanyDto> getAllCompanies(Pageable pageable);
    void updateCompany(Long id, CompanyDto companyDto);
    ResponseEntity<Void> deleteCompany(Long id);
}
