package org.example.service;

import org.example.dto.CompanyDto;
import org.example.dto.CompanyResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyService {
    void addCompany(CompanyDto dto);
    Page<CompanyDto> getAllCompanies(Pageable pageable);
    Page<CompanyResponseDto> getCompaniesWithUsers(Pageable pageable);
    List<CompanyDto> getCompanyById(List<Long> ids);
    void updateCompany(Long id, CompanyDto dto);
    void deleteCompany(Long id);
}