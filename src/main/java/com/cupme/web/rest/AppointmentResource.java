package com.cupme.web.rest;

import com.cupme.security.AuthoritiesConstants;
import com.cupme.service.AppointmentService;
import com.cupme.service.dto.AppointmentDTO;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AppointmentResource {

    private final Logger log = LoggerFactory.getLogger(AppointmentResource.class);

    private final AppointmentService appointmentService;

    public AppointmentResource(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * {@code GET /appointments} : get all appointments with only the public informations - calling this are allowed for anyone.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all appointments.
     */
    @GetMapping("/appointments")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        log.debug("REST request to get all Appointment");

        final List<AppointmentDTO> appointments = appointmentService.getAppointments();
        return ResponseEntity.ok().body(appointments);
    }

    /**
     * {@code GET /appointments/:id} : get the "id" appointment.
     * @param id the id of the appointmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appointmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/appointments/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<AppointmentDTO> getAppointment(long id) {
        log.debug("REST request to get Appointment : {}", id);

        final AppointmentDTO appointment = appointmentService.getAppointment(id);
        return ResponseEntity.ok().body(appointment);
    }

    /**
     * {@code POST  /appointments} : Create a new appointment.
     *
     * @param appointmentDTO the appointmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appointmentDTO, or with status {@code 400 (Bad Request)} if the appointment has already an ID.
     */
    @PostMapping("/appointments")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<AppointmentDTO> createAppointment(@Valid @RequestBody AppointmentDTO appointmentDTO) {
        log.debug("REST request to save Appointment : {}", appointmentDTO);

        if (appointmentDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }

        final AppointmentDTO result = appointmentService.createAppointment(appointmentDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PUT  /appointments} : Updates an existing appointment.
     *
     * @param appointmentDTO the appointmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appointmentDTO,
     * or with status {@code 400 (Bad Request)} if the appointmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appointmentDTO couldn't be updated.
     */
    @PutMapping("/appointments")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<AppointmentDTO> updateAppointment(@Valid @RequestBody AppointmentDTO appointmentDTO) {
        log.debug("REST request to update Appointment : {}", appointmentDTO);

        if (appointmentDTO.getId() == null) {
            return createAppointment(appointmentDTO);
        }

        final AppointmentDTO result = appointmentService.updateAppointment(appointmentDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code DELETE  /appointments/:id} : delete the "id" appointment.
     *
     * @param id the id of the appointmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/appointments/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<Void> deleteAppointment(@PathVariable long id) {
        log.debug("REST request to delete Appointment : {}", id);

        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
}
