package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.CompanyDto;
import org.example.entity.Company;
import org.example.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public CompanyDto createCompany(CompanyDto companyDto){
        Company company = toEntity(companyDto);
        Company savedCompany = companyRepository.save(company);
        return toDto(savedCompany);
    }

    public CompanyDto getCompany(Long id){
        return toDto(companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found")));
    }

    public List<CompanyDto> getAllCompanies(){
        return companyRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public CompanyDto updateCompany(Long id, CompanyDto companyDto){
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        company.setName(companyDto.getName());
        company.setBudget(companyDto.getBudget());
        return toDto(companyRepository.save(company));
    }

    public void deleteCompany(Long id){
        companyRepository.deleteById(id);
    }

    private CompanyDto toDto(Company company){
        CompanyDto dto = new CompanyDto();
        dto.setId(company.getId());
        dto.setName(company.getName());
        dto.setBudget(company.getBudget());
        return dto;
    }

    private Company toEntity(CompanyDto dto){
        Company user = new Company();
        user.setName(dto.getName());
        user.setBudget(dto.getBudget());
        return user;
    }
}
