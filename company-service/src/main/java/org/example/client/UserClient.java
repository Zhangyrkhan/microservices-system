package org.example.client;


import org.example.dto.SimpleUserDto;
import org.example.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/users/byCompany/simple/{companyId}")
    List<SimpleUserDto> getSimpleUsersByCompanyId(@PathVariable("companyId") Long companyId);
}

