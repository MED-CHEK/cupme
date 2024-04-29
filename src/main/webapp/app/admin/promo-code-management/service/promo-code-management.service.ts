import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { PromoCodeDTO } from 'app/entities/promo-code.model';
import { Pagination } from 'app/core/request/request.model';
import { createRequestOption } from 'app/core/request/request-util';

@Injectable({ providedIn: 'root' })
export class PromoCodeManagementService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/promoCodes');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(protocol: PromoCodeDTO): Observable<PromoCodeDTO> {
    return this.http.post<PromoCodeDTO>(this.resourceUrl, protocol);
  }

  update(protocol: PromoCodeDTO): Observable<PromoCodeDTO> {
    return this.http.put<PromoCodeDTO>(this.resourceUrl, protocol);
  }

  find(id: string): Observable<PromoCodeDTO> {
    return this.http.get<PromoCodeDTO>(`${this.resourceUrl}/${id}`);
  }

  findByCode(code: string): Observable<PromoCodeDTO> {
    return this.http.get<PromoCodeDTO>(`${this.resourceUrl}/code/${code}`);
  }

  query(req?: Pagination): Observable<HttpResponse<PromoCodeDTO[]>> {
    const options = createRequestOption(req);
    return this.http.get<PromoCodeDTO[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/${id}`);
  }
}
