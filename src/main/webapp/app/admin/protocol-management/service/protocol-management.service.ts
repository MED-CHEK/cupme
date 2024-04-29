import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { ProtocolCartDTO, ProtocolDTO, ProtocolDetailDTO } from 'app/entities/protocol.model';

@Injectable({ providedIn: 'root' })
export class ProtocolManagementService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/protocol');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(protocol: ProtocolDTO): Observable<ProtocolDTO> {
    return this.http.post<ProtocolDTO>(this.resourceUrl, protocol);
  }

  update(protocol: ProtocolDTO): Observable<ProtocolDTO> {
    return this.http.put<ProtocolDTO>(this.resourceUrl, protocol);
  }

  find(id: string): Observable<ProtocolDetailDTO> {
    return this.http.get<ProtocolDetailDTO>(`${this.resourceUrl}/${id}`);
  }

  query(): Observable<ProtocolCartDTO[]> {
    return this.http.get<ProtocolCartDTO[]>(this.resourceUrl);
  }

  delete(id: number): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/${id}`);
  }

  authorities(): Observable<string[]> {
    return this.http.get<string[]>(this.applicationConfigService.getEndpointFor('api/authorities'));
  }
}
