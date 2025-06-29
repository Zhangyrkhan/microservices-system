package org.example.client;


import org.example.dto.UserRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserClient {
    @PostMapping("by-company")
    List<UserRequestDto> getUsersByCompanyId(@RequestBody List<Long> companyIds);
}

