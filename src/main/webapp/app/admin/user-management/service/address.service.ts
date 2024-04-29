import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { Address } from 'app/entities/address.model';

@Injectable({ providedIn: 'root' })
export class AddressService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/addresses');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(user: Address): Observable<Address> {
    return this.http.post<Address>(this.resourceUrl, user);
  }

  update(user: Address): Observable<Address> {
    return this.http.put<Address>(this.resourceUrl, user);
  }
}
