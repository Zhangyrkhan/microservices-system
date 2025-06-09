package org.example.client;

import org.example.dto.CompanyDto;
import org.example.dto.SimpleCompanyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "company-service")
public interface CompanyClient {

    @GetMapping("/companies/{id}")
    SimpleCompanyDto getCompanyById(@PathVariable("id") Long id);

}
