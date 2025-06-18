package com.project.fullstack.mapper;

import com.project.fullstack.dto.PatientRequestDTO;
import com.project.fullstack.dto.PatientResponseDTO;
import com.project.fullstack.model.Patient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class PatientMapper {
    public static PatientResponseDTO toDTO(Patient patient)
    {
        PatientResponseDTO patientResponseDTO = new PatientResponseDTO();
        patientResponseDTO.setId(patient.getId().toString());
        patientResponseDTO.setName(patient.getName());
        patientResponseDTO.setEmail(patient.getEmail());
        patientResponseDTO.setAddress(patient.getAddress());
        patientResponseDTO.setDateOfBirth(patient.getDateOfBirth().toString());
        return patientResponseDTO;
    }

    public static Patient toPatient(PatientRequestDTO patientRequestDTO)
    {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Patient patient = new Patient();
        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth(),df));
        patient.setRegisteredDate(LocalDate.parse(patientRequestDTO.getRegisteredDate(),df));
        return patient;
    }

    public static Patient toUpdatePatient(Patient patient, PatientRequestDTO patientRequestDTO)
    {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth(),df));
        return patient;
    }

}
