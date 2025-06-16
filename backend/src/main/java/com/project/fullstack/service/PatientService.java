package com.project.fullstack.service;

import com.project.fullstack.dto.PatientRequestDTO;
import com.project.fullstack.dto.PatientResponseDTO;
import com.project.fullstack.mapper.PatientMapper;
import com.project.fullstack.model.Patient;
import com.project.fullstack.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getAllPatients() {
        List<Patient> patients=patientRepository.findAll();
        return patients.stream().map(PatientMapper::toDTO).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {

     Patient newPatient=patientRepository.save(PatientMapper.toPatient(patientRequestDTO));
     return PatientMapper.toDTO(newPatient);

    }
}
