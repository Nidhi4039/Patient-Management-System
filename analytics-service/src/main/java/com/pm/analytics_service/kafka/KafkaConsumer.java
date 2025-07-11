package com.pm.analytics_service.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);
    @KafkaListener(topics = "patient", groupId = "analytics-service")
    public void cunsumeEvent(byte[] event)
    {
        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);
            log.info("Received event: [ PatientID: {}, Name: {}, Email: {}, Event Type: {} ]",
                    patientEvent.getPatientId(),
                    patientEvent.getName(),
                    patientEvent.getEmail(),
                    patientEvent.getEventType()
            );
        } catch (InvalidProtocolBufferException e) {
            log.error("error deserializing event {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
