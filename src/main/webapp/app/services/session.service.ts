import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { SessionDTO } from 'app/entities/session.model';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class SessionService {
  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  query(): Observable<SessionDTO[]> {
    return this.http.get<SessionDTO[]>(this.applicationConfigService.getEndpointFor('api/sessions'));
  }
}
