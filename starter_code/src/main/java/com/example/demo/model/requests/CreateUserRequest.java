package com.example.demo.model.requests;

import com.example.demo.util.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class CreateUserRequest {

    @JsonProperty
    @NotEmpty(message = "app.login.request.username.require")
    private String username;
    @JsonProperty
    @NotEmpty(message = "app.login.request.username.require")
    @Pattern(
            regexp = Constants.PASSWORD_SECURITY_PATTERN,
            message = "{app.login.request.password.security.require}")
    private String password;
}
