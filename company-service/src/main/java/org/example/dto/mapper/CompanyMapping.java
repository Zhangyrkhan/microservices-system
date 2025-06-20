package org.example.dto.mapper;

import org.example.dto.CompanyDto;
import org.example.entity.Company;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CompanyMapping {
    CompanyDto toDto(Company company);
    Company toEntity(CompanyDto companyDto);
}
