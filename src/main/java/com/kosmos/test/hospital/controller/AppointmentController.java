package com.kosmos.test.hospital.controller;

import com.kosmos.test.hospital.dto.AppointmentRequestDTO;
import com.kosmos.test.hospital.exception.AppointmentException;
import com.kosmos.test.hospital.model.Appointment;
import com.kosmos.test.hospital.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private CitaService citaService;

    @PostMapping("/new")
    public ResponseEntity<?> newAppointments(@RequestBody AppointmentRequestDTO appointment) {
        try {
            Appointment newAppointment = citaService.newAppointment(appointment);
            return ResponseEntity.status(HttpStatus.CREATED).body(newAppointment);
        } catch (AppointmentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editAppointments(@PathVariable Long id, @RequestBody AppointmentRequestDTO appointment) {
        try {
            Appointment appointmentEdit = citaService.editAppointment(id, appointment);
            return ResponseEntity.ok(appointmentEdit);
        } catch (AppointmentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAppointments(@PathVariable Long id) {
        try {
            citaService.cancelAppointment(id);
            return ResponseEntity.ok(Map.of("mensaje", "Appointment successfully cancelled\n."));
        } catch (AppointmentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/consult")
    public ResponseEntity<List<Appointment>> consultAppointments(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) Long doctorsOfficeId) {

        List<Appointment> resultados;

        if (doctorId != null) {
            resultados = citaService.consultAppoimentByDoctor(date, doctorId);
        } else if (doctorsOfficeId != null) {
            resultados = citaService.consultAppoimentByDoctorsOffice(date, doctorsOfficeId);
        } else {
            resultados = citaService.consultAppoimentByDate(date);
        }

        return ResponseEntity.ok(resultados);
    }
}


