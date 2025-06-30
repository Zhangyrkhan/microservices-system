package org.example.service;

import org.example.dto.*;
import org.springframework.data.domain.*;
import java.util.List;

public interface CompanyService {
    void addCompany(CompanyDto dto);
    Page<CompanyDto> getAllCompanies(Pageable pageable);
    Page<CompanyResponseDto> getCompaniesWithUsers(Pageable pageable);
    void updateCompany(Long id, CompanyDto dto);
    void deleteCompany(Long id);
    List<CompanyDto> getCompanyById(List<Long> ids);
}