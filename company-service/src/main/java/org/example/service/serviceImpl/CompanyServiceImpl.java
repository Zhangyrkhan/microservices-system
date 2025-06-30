package org.example.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.example.client.UserClient;
import org.example.dto.*;
import org.example.entity.Company;
import org.example.exception.NotFoundException;
import org.example.mapper.CompanyMapper;
import org.example.repository.CompanyRepository;
import org.example.service.CompanyService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository repository;
    private final CompanyMapper mapper;
    private final UserClient userClient;

    @Override
    public void addCompany(CompanyDto dto) {
        repository.save(mapper.toEntity(dto));
    }

    @Override
    public Page<CompanyDto> getAllCompanies(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toDto);
    }

    @Override
    public Page<CompanyResponseDto> getCompaniesWithUsers(Pageable pageable) {
        Page<Company> page = repository.findAll(pageable);

        List<Long> companyIds = page.getContent().stream()
                .map(Company::getId)
                .toList();

        List<UserDto> users = userClient.getUsersByCompanyIds(companyIds);

        var grouped = users.stream()
                .collect(Collectors.groupingBy(UserDto::getCompanyId));

        return page.map(c -> {
            List<UserDto> list = grouped.getOrDefault(c.getId(), Collections.emptyList());
            return mapper.toResponseDto(c, list);
        });
    }

    @Override
    public void updateCompany(Long id, CompanyDto dto) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Company " + id + " not found");
        }
        Company c = mapper.toEntity(dto);
        c.setId(id);
        repository.save(c);
    }

    @Override
    public void deleteCompany(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Company " + id + " not found");
        }
        repository.deleteById(id);
    }

    @Override
    public List<CompanyDto> getCompanyById(List<Long> ids) {
        return repository.findAllById(ids)
                .stream()
                .map(mapper::toDto)
                .toList();
    }
}