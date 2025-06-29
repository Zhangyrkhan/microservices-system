package org.example.service;

import org.example.dto.CompanyDto;
import org.example.dto.CompanyResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CompanyService {

    void addCompany (CompanyDto companyDto);
    Page<CompanyDto> getAllCompanies(Pageable pageable);
    Page<CompanyResponseDto> getCompaniesResponseDto(Pageable pageable);
    void updateCompany(Long id, CompanyDto companyDto);
    ResponseEntity<Void> deleteCompany(Long id);
    List<CompanyDto> getCompanyById(List<Long> id);
}
