package org.example.client;

import org.example.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "user-service", path = "/users")
public interface UserClient {

    @PostMapping("/by-company")
    List<UserDto> getUsersByCompanyIds(@RequestBody List<Long> companyIds);
}