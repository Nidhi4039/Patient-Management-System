package com.project.fullstack.service;

import billing.BillingRequest;
import com.project.fullstack.dto.PatientRequestDTO;
import com.project.fullstack.dto.PatientResponseDTO;
import com.project.fullstack.exception.EmailAlreadyExistsException;
import com.project.fullstack.exception.PatientNotFoundException;
import com.project.fullstack.grpc.BillingServiceGrpcClient;
import com.project.fullstack.kafka.KafkaProducer;
import com.project.fullstack.mapper.PatientMapper;
import com.project.fullstack.model.Patient;
import com.project.fullstack.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private  final BillingServiceGrpcClient billingServiceGrpcClient;

    private final KafkaProducer kafkaProducer;
    public PatientService(PatientRepository patientRepository, BillingServiceGrpcClient billingServiceGrpcClient, KafkaProducer kafkaProducer) {
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient= billingServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
    }

    public List<PatientResponseDTO> getAllPatients() {
        List<Patient> patients=patientRepository.findAll();
        return patients.stream().map(PatientMapper::toDTO).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
    if(patientRepository.existsByEmail((patientRequestDTO.getEmail())))
    {
        throw new EmailAlreadyExistsException("Email already exists: " + patientRequestDTO.getEmail());
    }

     Patient newPatient=patientRepository.save(PatientMapper.toPatient(patientRequestDTO));
    billingServiceGrpcClient.createBillingAccount(newPatient.getId(),newPatient.getName(),newPatient.getEmail());

    kafkaProducer.sendEvent(newPatient);
     return PatientMapper.toDTO(newPatient);
    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findById(id).orElseThrow(
                ()->new PatientNotFoundException("No patient found with id: ",id)
        );
        if(patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(),id))
        {
            throw new EmailAlreadyExistsException("Email already exists: " + patientRequestDTO.getEmail());
        }
        patient = PatientMapper.toUpdatePatient(patient,patientRequestDTO);
        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toDTO(updatedPatient);
    }

    public void deletePatient(UUID id)
    {
        Patient patient=patientRepository.findById(id).orElseThrow(()->new PatientNotFoundException("No patient found with id: ", id));
        patientRepository.deleteById(id);
    }
}
