package com.cupme.domain;

import com.cupme.domain.enumeration.AdressType;
import com.cupme.domain.enumeration.MediaType;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedDate;

/**
 * A cart item.
 */
@Entity
@Table(name = "appointment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(length = 254, unique = true)
    private String email;

    @Column(name = "phone_number", length = 254, unique = true)
    private String telephone;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private MediaType type;

    @Column(name = "appointment_date")
    private LocalDate appointmentDate;

    @Column(name = "appointment_time")
    private LocalTime appointmentTime;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public Appointment() {}

    public Appointment(
        Long id,
        String email,
        String telephone,
        MediaType type,
        LocalDate appointmentDate,
        LocalTime appointmentTime,
        Session session,
        Order order
    ) {
        this.id = id;
        this.email = email;
        this.telephone = telephone;
        this.type = type;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.session = session;
        this.order = order;
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

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment)) return false;
        Appointment that = (Appointment) o;
        return (
            Objects.equals(getId(), that.getId()) &&
            Objects.equals(getEmail(), that.getEmail()) &&
            Objects.equals(getTelephone(), that.getTelephone()) &&
            getType() == that.getType() &&
            Objects.equals(getAppointmentDate(), that.getAppointmentDate()) &&
            Objects.equals(getAppointmentTime(), that.getAppointmentTime()) &&
            Objects.equals(getSession(), that.getSession()) &&
            Objects.equals(getOrder(), that.getOrder())
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            getId(),
            getEmail(),
            getTelephone(),
            getType(),
            getAppointmentDate(),
            getAppointmentTime(),
            getSession(),
            getOrder()
        );
    }

    @Override
    public String toString() {
        return (
            "Appointment{" +
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
            ", session=" +
            session +
            ", order=" +
            order +
            '}'
        );
    }
}
