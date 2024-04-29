package com.cupme.service;

import com.cupme.repository.AppointmentRepository;
import com.cupme.service.dto.AppointmentDTO;
import com.cupme.service.mapper.AppointmentMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing appointments.
 */
@Service
@Transactional
public class AppointmentService {

    private final Logger log = LoggerFactory.getLogger(AppointmentService.class);

    private final AppointmentRepository appointmentRepository;

    private final AppointmentMapper appointmentMapper;
    private final CacheManager cacheManager;

    public AppointmentService(AppointmentRepository appointmentRepository, AppointmentMapper appointmentMapper, CacheManager cacheManager) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
        this.cacheManager = cacheManager;
    }

    public List<AppointmentDTO> getAppointments() {
        return appointmentMapper.appointmentsToAppointmentDTOs(appointmentRepository.findAll());
    }

    public AppointmentDTO getAppointment(long id) {
        return appointmentMapper.appointmentToAppointmentDTO(appointmentRepository.findById(id).get());
    }

    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {
        return appointmentMapper.appointmentToAppointmentDTO(
            appointmentRepository.save(appointmentMapper.appointmentDTOToAppointment(appointmentDTO))
        );
    }

    public AppointmentDTO updateAppointment(AppointmentDTO appointmentDTO) {
        return appointmentMapper.appointmentToAppointmentDTO(
            appointmentRepository.save(appointmentMapper.appointmentDTOToAppointment(appointmentDTO))
        );
    }

    public void deleteAppointment(long id) {
        appointmentRepository.deleteById(id);
    }
}
