import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApplicationConfigService } from '../core/config/application-config.service';
import { CartItemDisplayDTO } from '../entities/cartItem.model';
import { LocalStorageService } from 'ngx-webstorage';
import { EventEmitter } from '@angular/core';
import { Observable, from } from 'rxjs';
import { ProtocolCartDTO, ProtocolDetailDTO } from 'app/entities/protocol.model';
@Injectable({
  providedIn: 'root',
})
export class StoreService {
  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  getProtocol(id: number): Observable<ProtocolDetailDTO> {
    return this.http.get<ProtocolDetailDTO>(this.applicationConfigService.getEndpointFor('api/protocol/' + id));
  }

  getProtocols(): Observable<ProtocolCartDTO[]> {
    return this.http.get<ProtocolCartDTO[]>(this.applicationConfigService.getEndpointFor('api/protocol'));
  }
}
