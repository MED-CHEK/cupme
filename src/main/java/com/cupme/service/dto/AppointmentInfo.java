package com.cupme.service.dto;

import com.cupme.domain.Appointment;
import com.cupme.domain.enumeration.MediaType;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String email;
    private String telephone;
    private MediaType type;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

    public AppointmentInfo() {}

    public AppointmentInfo(Long id, String email, String telephone, MediaType type, LocalDate appointmentDate, LocalTime appointmentTime) {
        this.id = id;
        this.email = email;
        this.telephone = telephone;
        this.type = type;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
    }

    public AppointmentInfo(Appointment appointment) {
        this.id = appointment.getId();
        this.email = appointment.getEmail();
        this.telephone = appointment.getTelephone();
        this.type = appointment.getType();
        this.appointmentDate = appointment.getAppointmentDate();
        this.appointmentTime = appointment.getAppointmentTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public MediaType getType() {
        return type;
    }

    public void setType(MediaType type) {
        this.type = type;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    @Override
    public String toString() {
        return (
            "AppointmentInfo{" +
            "id=" +
            id +
            ", email='" +
            email +
            '\'' +
            ", telephone='" +
            telephone +
            '\'' +
            ", type=" +
            type +
            ", appointmentDate=" +
            appointmentDate +
            ", appointmentTime=" +
            appointmentTime +
            '}'
        );
    }
}
