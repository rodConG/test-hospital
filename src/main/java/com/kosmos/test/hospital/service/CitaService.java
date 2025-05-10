package com.kosmos.test.hospital.service;

import com.kosmos.test.hospital.dto.AppointmentRequestDTO;
import com.kosmos.test.hospital.model.Appointment;

import java.time.LocalDate;
import java.util.List;

public interface CitaService {
    Appointment newAppointment(AppointmentRequestDTO appointment);
    Appointment editAppointment(Long id, AppointmentRequestDTO dataUpdate);
    void cancelAppointment(Long id);
    List<Appointment> consultAppoimentByDate(LocalDate date);
    List<Appointment> consultAppoimentByDoctor(LocalDate date, Long doctorId);
    List<Appointment> consultAppoimentByDoctorsOffice(LocalDate fecha, Long doctorsOfficeId);
}
