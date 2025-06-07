package org.example.service;

import org.example.client.UserClient;
import org.example.dto.CompanyDto;
import org.example.dto.UserDto;
import org.example.entity.Company;
import org.example.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserClient userClient;

    public CompanyService(CompanyRepository companyRepository, UserClient userClient) {
        this.companyRepository = companyRepository;
        this.userClient = userClient;
    }

    public List<CompanyDto> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public CompanyDto getCompanyById(Long id) {
        return companyRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Company not found"));
    }

    public CompanyDto createCompany(Company company) {
        return toDto(companyRepository.save(company));
    }

    public CompanyDto updateCompany(Long id, Company updated) {
        Company company = companyRepository.findById(id).orElseThrow(()-> new RuntimeException("Company not found"));
        company.setName(updated.getName());
        company.setBudget(updated.getBudget());
        company.setEmployeeIds(updated.getEmployeeIds());
        return toDto(companyRepository.save(company));
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    public void addEmployeeToCompany(Long companyId, Long userId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        if (!company.getEmployeeIds().contains(userId)) {
            company.getEmployeeIds().add(userId);
            companyRepository.save(company);
        }
    }

    private CompanyDto toDto(Company company) {
        CompanyDto dto = new CompanyDto();
        dto.setId(company.getId());
        dto.setName(company.getName());
        dto.setBudget(company.getBudget());

        List<UserDto> users = company.getEmployeeIds().stream()
                .map(userClient::getUserById)
                .collect(Collectors.toList());

        dto.setEmployees(users);
        return dto;
    }
}
