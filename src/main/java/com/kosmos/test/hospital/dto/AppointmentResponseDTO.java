package com.kosmos.test.hospital.dto;

import com.kosmos.test.hospital.model.Appointment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentResponseDTO {

    private Long id;
    private String patientName;
    private LocalDateTime schedule;
    private DoctorDTO doctor;
    private ConsultorioDTO doctorsOffice;

    public static AppointmentResponseDTO fromEntity(Appointment appointment) {
        AppointmentResponseDTO dto = new AppointmentResponseDTO();
        dto.setId(appointment.getId());
        dto.setPatientName(appointment.getPatientName());
        dto.setSchedule(appointment.getSchedule());

        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setId(appointment.getDoctor().getId());
        doctorDTO.setName(appointment.getDoctor().getName() + " " + appointment.getDoctor().getPaternalSurname());

        ConsultorioDTO consultorioDTO = new ConsultorioDTO();
        consultorioDTO.setId(appointment.getDoctorsOffice().getId());
        consultorioDTO.setNumber(appointment.getDoctorsOffice().getNumberDoctorsOffice());
        consultorioDTO.setFloor(appointment.getDoctorsOffice().getFloor());

        dto.setDoctor(doctorDTO);
        dto.setDoctorsOffice(consultorioDTO);
        return dto;
    }
}
