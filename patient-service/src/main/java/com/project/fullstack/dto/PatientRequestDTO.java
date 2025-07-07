package com.project.fullstack.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PatientRequestDTO {
    @NotBlank(message = "Name cannot be null")
    @Size(max=100,message = "Name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Address cannot be null")
    private String address;

    @NotBlank(message = "Date of Birth is required")
    private String dateOfBirth;

    @NotBlank(message = "Registered Date is required")
    private String registeredDate;
}
