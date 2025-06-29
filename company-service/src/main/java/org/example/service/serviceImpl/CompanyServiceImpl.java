package org.example.service.serviceImpl;

import lombok.AllArgsConstructor;
import org.example.client.UserClient;
import org.example.dto.CompanyDto;
import org.example.dto.CompanyResponseDto;
import org.example.dto.UserRequestDto;
import org.example.dto.mapper.CompanyMapping;
import org.example.entity.Company;
import org.example.repository.CompanyRepository;
import org.example.service.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapping companyMapping;
    private final UserClient userClient;

    @Override
    public void addCompany(CompanyDto companyDto) {
        Company company = companyMapping.toEntity(companyDto);
        companyRepository.save(company);
    }

    @Override
    public Page<CompanyDto> getAllCompanies(Pageable pageable) {
        return companyRepository.findAll(pageable)
                .map(companyMapping::toDto);
    }

    @Override
    public Page<CompanyResponseDto> getCompaniesResponseDto(Pageable pageable) {
        Page<Company> page = companyRepository.findAll(pageable);
        List<Long> companyIds = page.getContent()
                .stream()
                .map(Company::getId)
                .toList();

        List<UserRequestDto> allUsers = userClient.getUsersByCompanyId(companyIds);
        Map<Long, List<UserRequestDto>> usersByCompany = allUsers.stream()
                .collect(Collectors.groupingBy(UserRequestDto::getCompanyId));

        return page.map(company -> {
            List<UserRequestDto> users =
                    usersByCompany.getOrDefault(company.getId(), Collections.emptyList());
            return companyMapping.toResponseDto(company, users);
        });
    }


    @Override
    public void updateCompany(Long id, CompanyDto updatedCompanyDto){
        Company company = companyMapping.toEntity(updatedCompanyDto);
        company.setId(id);
        companyRepository.save(company);
    }

    @Override
    public ResponseEntity<Void> deleteCompany(Long id){
        if(!companyRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        companyRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public List<CompanyDto> getCompanyById(List<Long> id) {
        return companyRepository.findAllById(id)
                .stream()
                .map(companyMapping::toDto)
                .toList();
    }
}
