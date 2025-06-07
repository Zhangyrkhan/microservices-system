package org.example.client;

import org.example.dto.CompanyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "company-service")
public interface CompanyClient {

    @GetMapping("/companies/{id}")
    CompanyDto getCompanyById(@PathVariable("id") Long id);

    @PutMapping("/companies/{companyId}/addEmployee/{userId}")
    void addEmployeeToCompany(@PathVariable("companyId") Long companyId, @PathVariable("userId") Long userId);
}
