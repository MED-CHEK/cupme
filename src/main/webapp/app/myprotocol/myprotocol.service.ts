import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { ProtocolDTO } from 'app/entities/protocol.model';
import { Observable } from 'rxjs';
import { ProtocolCartDTO } from '../entities/protocol.model';

@Injectable({ providedIn: 'root' })
export class MyProtocolService {
  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  getProtocol(id: number): Observable<ProtocolDTO> {
    return this.http.get<ProtocolDTO>(this.applicationConfigService.getEndpointFor('api/myprotocol/' + id));
  }

  getProtocols(): Observable<ProtocolCartDTO[]> {
    return this.http.get<ProtocolCartDTO[]>(this.applicationConfigService.getEndpointFor('api/protocol'));
  }

  createProtocol(protocol: ProtocolDTO): Observable<ProtocolDTO> {
    return this.http.post<ProtocolDTO>(this.applicationConfigService.getEndpointFor('api/protocol'), protocol);
  }

  updateProtocol(protocol: ProtocolDTO): Observable<ProtocolDTO> {
    return this.http.put<ProtocolDTO>(this.applicationConfigService.getEndpointFor('api/protocol'), protocol);
  }

  deleteProtocol(id: number): Observable<{}> {
    return this.http.delete(this.applicationConfigService.getEndpointFor('api/protocol/' + id));
  }
}
