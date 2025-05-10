package com.kosmos.test.hospital.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private DoctorsOffice doctorsOffice;
    @ManyToOne
    private Doctor doctor;
    private LocalDateTime schedule;
    private String patientName;

    public Long getId() {
        return id;
    }

    public DoctorsOffice getDoctorsOffice() {
        return doctorsOffice;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalDateTime getSchedule() {
        return schedule;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDoctorsOffice(DoctorsOffice doctorsOffice) {
        this.doctorsOffice = doctorsOffice;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setSchedule(LocalDateTime schedule) {
        this.schedule = schedule;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
}

