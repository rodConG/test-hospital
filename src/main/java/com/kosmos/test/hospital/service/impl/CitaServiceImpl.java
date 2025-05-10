package com.kosmos.test.hospital.service.impl;

import com.kosmos.test.hospital.dto.AppointmentRequestDTO;
import com.kosmos.test.hospital.exception.AppointmentException;
import com.kosmos.test.hospital.model.Appointment;
import com.kosmos.test.hospital.model.Doctor;
import com.kosmos.test.hospital.model.DoctorsOffice;
import com.kosmos.test.hospital.repository.AppointmentRepository;
import com.kosmos.test.hospital.repository.DoctorRepository;
import com.kosmos.test.hospital.repository.DoctorsOfficeRepository;
import com.kosmos.test.hospital.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorsOfficeRepository doctorsOfficeRepository;

    public Appointment newAppointment(AppointmentRequestDTO appointment) {
        Appointment appointmentEntity = validAppointment(appointment, null);
        return appointmentRepository.save(appointmentEntity);
    }

    public Appointment editAppointment(Long id, AppointmentRequestDTO dataUpdate) {

        Optional<Appointment> optionalCita = appointmentRepository.findById(id);

        if (optionalCita.isEmpty()) {
            throw new AppointmentException("The appointment does not exist.");
        }

        Appointment appointmentExist = optionalCita.get();

        if (!appointmentExist.getSchedule().equals(dataUpdate.getSchedule()) ||
                !appointmentExist.getDoctor().getId().equals(dataUpdate.getDoctorId()) ||
                !appointmentExist.getDoctorsOffice().getId().equals(dataUpdate.getDoctorsOfficeId()) ||
                !appointmentExist.getPatientName().equals(dataUpdate.getPatientName())) {

            validAppointment(dataUpdate, id);
        }

        Optional<Doctor> doctorOptional = doctorRepository.findById(dataUpdate.getDoctorId());
        Optional<DoctorsOffice> doctorsOfficeOptional = doctorsOfficeRepository.findById(dataUpdate.getDoctorId());

        if (doctorOptional.isEmpty()) {
            throw new AppointmentException("The doctor not exists.");
        }
        if (doctorsOfficeOptional.isEmpty()) {
            throw new AppointmentException("The doctor's Office not exists.");
        }

        appointmentExist.setDoctor(doctorOptional.get());
        appointmentExist.setDoctorsOffice(doctorsOfficeOptional.get());
        appointmentExist.setSchedule(dataUpdate.getSchedule());
        appointmentExist.setPatientName(dataUpdate.getPatientName());

        return appointmentRepository.save(appointmentExist);
    }

    public void cancelAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentException("The appointment does not exist."));

        if (appointment.getSchedule().isBefore(LocalDateTime.now())) {
            throw new AppointmentException("You cannot cancel a past appointment..");
        }

        appointmentRepository.deleteById(id);
    }

    private Appointment validAppointment(AppointmentRequestDTO appointment, Long actualAppointmentId) {
        LocalDateTime schedule = appointment.getSchedule();
        LocalDate date = schedule.toLocalDate();

        Optional<Doctor> doctorOptional = doctorRepository.findById(appointment.getDoctorId());
        Optional<DoctorsOffice> doctorsOfficeOptional = doctorsOfficeRepository.findById(appointment.getDoctorId());

        if (doctorOptional.isEmpty()) {
            throw new AppointmentException("The doctor not exists.");
        }

        if (doctorsOfficeOptional.isEmpty()) {
            throw new AppointmentException("The doctor's Office not exists.");
        }

        if (appointmentRepository.existsByDoctorsOfficeAndAppointment(doctorsOfficeOptional.get(), schedule)
                && !isSameAppointment(doctorsOfficeOptional.get().getId(), schedule, actualAppointmentId)) {
            throw new AppointmentException("The doctor's office is already busy at that time.");
        }

        if (appointmentRepository.existsByDoctorAndAppointment(doctorOptional.get(), schedule)
                && !isSameAppointment(doctorOptional.get().getId(), schedule, actualAppointmentId)) {
            throw new AppointmentException("The doctor already has an appointment at that time.");
        }

        // Calcular rango de tiempo (+/- 2 horas)
        LocalDateTime inicio = appointment.getSchedule().minusHours(2);
        LocalDateTime fin = appointment.getSchedule().plusHours(2);

        if (appointmentRepository.existsAppointmentConflictPatient(appointment.getPatientName(), appointment.getSchedule(), inicio, fin)) {
            throw new AppointmentException("The patient already has another appointment within 2 hours on the same day.");
        }

        int totalDoctorsAppointment = appointmentRepository.countByDoctorAndDate(doctorOptional.get(), date);
        if (actualAppointmentId == null || totalDoctorsAppointment >= 8) {
            throw new AppointmentException("The doctor already has 8 appointments that day.");
        }

        Appointment appointmentEntity = new Appointment();
        appointmentEntity.setSchedule(appointment.getSchedule());
        appointmentEntity.setPatientName(appointment.getPatientName());
        appointmentEntity.setDoctor(doctorOptional.get());
        appointmentEntity.setDoctorsOffice(doctorsOfficeOptional.get());

        return appointmentEntity;

    }

    private boolean isSameAppointment(Long idEntity, LocalDateTime schedule, Long actualAppointmentId) {
        if (actualAppointmentId == null) return false;
        Optional<Appointment> appointment = appointmentRepository.findById(actualAppointmentId);
        return appointment.isPresent() && appointment.get().getSchedule().equals(schedule) &&
                (appointment.get().getDoctor().getId().equals(idEntity) ||
                        appointment.get().getDoctorsOffice().getId().equals(idEntity));
    }

    public List<Appointment> consultAppoimentByDate(LocalDate date) {
        return appointmentRepository.findByDate(date);
    }

    public List<Appointment> consultAppoimentByDoctor(LocalDate date, Long doctorId) {
        return appointmentRepository.findByFechaAndDoctor(date, doctorId);
    }

    public List<Appointment> consultAppoimentByDoctorsOffice(LocalDate date, Long doctorsOfficeId) {
        return appointmentRepository.findByFechaAndDoctorsOffice(date, doctorsOfficeId);
    }


}

