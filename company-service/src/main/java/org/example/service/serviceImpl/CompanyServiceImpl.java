package org.example.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.example.client.UserClient;
import org.example.dto.CompanyDto;
import org.example.dto.CompanyResponseDto;
import org.example.dto.UserDto;
import org.example.entity.Company;
import org.example.mapper.CompanyMapper;
import org.example.repository.CompanyRepository;
import org.example.service.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Map;
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
    public List<CompanyDto> getCompanyById(List<Long> ids) {
        return repository.findAllById(ids)
                .stream()
                .map(mapper::toDto)
                .toList();
    }


    @Override
    public Page<CompanyResponseDto> getCompaniesWithUsers(Pageable pageable) {
        Page<Company> page = repository.findAll(pageable);

        List<Long> ids = page.getContent().stream()
                .map(Company::getId)
                .toList();

        List<UserDto> users = userClient.getUsersByCompanyIds(ids);

        Map<Long, List<UserDto>> byCompany = users.stream()
                .collect(Collectors.groupingBy(UserDto::getCompanyId));

        return page.map(c -> {
            List<UserDto> list = byCompany.getOrDefault(c.getId(), Collections.emptyList());
            return mapper.toResponseDto(c, list);
        });
    }

    @Override
    public void updateCompany(Long id, CompanyDto dto) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Company " + id + " not found");
        }
        Company c = mapper.toEntity(dto);
        c.setId(id);
        repository.save(c);
    }

    @Override
    public void deleteCompany(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Company " + id + " not found");
        }
        repository.deleteById(id);
    }
}