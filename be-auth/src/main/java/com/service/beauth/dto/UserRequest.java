package com.service.beauth.dto;

import com.service.beauth.constant.Constant;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "Name is mandatory, please fill it!")
    @NotNull(message = "Name is mandatory, please fill it!")
    @Pattern(regexp = Constant.Regex.ALPHANUMERIC_WITH_DOT_AND_SPACE, message = "Invalid format name")
    private String name;
    @NotBlank(message = "Phone number is mandatory, please fill it!")
    @NotNull(message = "Phone number is mandatory, please fill it!")
    @Pattern(regexp = Constant.Regex.NUMERIC, message = "Invalid format phone number")
    @Size(min = 8, max = 16, message = "Invalid length phone number")
    private String phoneNumber;
    private String address;
    @NotBlank(message = "Email is mandatory, please fill it!")
    @NotNull(message = "Email is mandatory, please fill it!")
    @Email(message = "Invalid format email")
    private String email;
    @NotBlank(message = "Gender is mandatory, please fill it!")
    @NotNull(message = "Gender is mandatory, please fill it!")
    @Pattern(regexp = Constant.Regex.ALPHABET, message = "Invalid format gender")
    private String gender;
    @NotBlank(message = "Username is mandatory, please fill it!")
    @NotNull(message = "Username mandatory, please fill it!")
    @Pattern(regexp = Constant.Regex.ALPHANUMERIC, message = "Invalid format username")
    private String username;
    @NotBlank(message = "Password is mandatory, please fill it!")
    @NotNull(message = "Password mandatory, please fill it!")
    private String password;
    @NotBlank(message = "Role is mandatory, please fill it!")
    @NotNull(message = "Role mandatory, please fill it!")
    private String role;
}
