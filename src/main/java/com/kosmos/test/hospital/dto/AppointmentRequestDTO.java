package com.kosmos.test.hospital.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentRequestDTO {

    private Long doctorId;
    private Long doctorsOfficeId;
    private LocalDateTime schedule;
    private String patientName;
}
