import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { AppointmentDTO } from 'app/entities/session.model';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AppointmentService {
  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  query(): Observable<AppointmentDTO[]> {
    return this.http.get<AppointmentDTO[]>(this.applicationConfigService.getEndpointFor('api/appointments'));
  }
}
