package com.kosmos.test.hospital.repository;

import com.kosmos.test.hospital.model.Appointment;
import com.kosmos.test.hospital.model.DoctorsOffice;
import com.kosmos.test.hospital.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean existsByDoctorsOfficeAndAppointment(DoctorsOffice doctorsOffice, LocalDateTime schedule);

    boolean existsByDoctorAndAppointment(Doctor doctor, LocalDateTime schedule);

    @Query("SELECT COUNT(c) FROM Appointment c WHERE c.doctor = :doctor AND DATE(c.schedule) = :fecha")
    int countByDoctorAndDate(Doctor doctor, LocalDate fecha);

    @Query("SELECT COUNT(c) > 0 FROM Appointment c " +
            "WHERE c.patientName = :patientName " +
            "AND FUNCTION('DATE', c.schedule) = FUNCTION('DATE', :schedule) " +
            "AND c.schedule BETWEEN :start AND :end")
    boolean existsAppointmentConflictPatient(@Param("patientName") String patientName,
                                       @Param("schedule") LocalDateTime schedule,
                                       @Param("start") LocalDateTime start,
                                       @Param("end") LocalDateTime end);

    @Query("SELECT c FROM Appointment c WHERE DATE(c.schedule) = :date")
    List<Appointment> findByDate(LocalDate date);

    @Query("SELECT c FROM Appointment c WHERE DATE(c.schedule) = :date AND c.doctor.id = :doctorId")
    List<Appointment> findByFechaAndDoctor(LocalDate date, Long doctorId);

    @Query("SELECT c FROM Appointment c WHERE DATE(c.schedule) = :date AND c.doctorsOffice.id = :doctorsOfficeId")
    List<Appointment> findByFechaAndDoctorsOffice(LocalDate date, Long doctorsOfficeId);
}
