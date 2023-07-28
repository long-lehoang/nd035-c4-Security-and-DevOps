package com.example.demo.model.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Credential {
    private String username;
    private String password;
}
