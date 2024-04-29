package com.cupme.service.mapper;

import com.cupme.domain.Appointment;
import com.cupme.service.dto.AppointmentDTO;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity {@link Appointment} and its DTO called {@link Appointment}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class AppointmentMapper {

    private final UserMapper userMapper;

    private final SessionMapper sessionMapper;
    private final OrderMapper orderMapper;

    public AppointmentMapper(UserMapper userMapper, SessionMapper sessionMapper, OrderMapper orderMapper) {
        this.userMapper = userMapper;
        this.sessionMapper = sessionMapper;
        this.orderMapper = orderMapper;
    }

    public List<AppointmentDTO> appointmentsToAppointmentDTOs(List<Appointment> appointments) {
        return appointments.stream().filter(Objects::nonNull).map(this::appointmentToAppointmentDTO).collect(Collectors.toList());
    }

    public AppointmentDTO appointmentToAppointmentDTO(Appointment appointment) {
        return new AppointmentDTO(appointment);
    }

    public List<Appointment> appointmentDTOsToAppointments(List<AppointmentDTO> appointmentDTOS) {
        return appointmentDTOS.stream().filter(Objects::nonNull).map(this::appointmentDTOToAppointment).collect(Collectors.toList());
    }

    public Appointment appointmentDTOToAppointment(AppointmentDTO appointmentDTO) {
        if (appointmentDTO == null) {
            return null;
        } else {
            Appointment appointment = new Appointment();
            appointment.setId(appointmentDTO.getId());
            appointment.setEmail(appointmentDTO.getEmail());
            appointment.setTelephone(appointmentDTO.getTelephone());
            appointment.setType(appointmentDTO.getType());
            appointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
            appointment.setAppointmentTime(appointmentDTO.getAppointmentTime());
            appointment.setSession(sessionMapper.sessionDTOToSession(appointmentDTO.getSession()));
            appointment.setOrder(orderMapper.orderDTOToOrder(appointmentDTO.getOrder()));

            return appointment;
        }
    }
}
