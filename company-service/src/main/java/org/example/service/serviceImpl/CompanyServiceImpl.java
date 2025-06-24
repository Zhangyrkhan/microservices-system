package org.example.service.serviceImpl;

import lombok.AllArgsConstructor;
import org.example.dto.CompanyDto;
import org.example.dto.mapper.CompanyMapping;
import org.example.entity.Company;
import org.example.repository.CompanyRepository;
import org.example.service.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;
    private CompanyMapping companyMapping;

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
