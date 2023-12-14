import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { UserDTO } from 'app/entities/user/user.model';

@Injectable({ providedIn: 'root' })
export class RegisterService {
  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  save(userDTO: UserDTO): Observable<{}> {
    return this.http.post(this.applicationConfigService.getEndpointFor('api/register'), userDTO);
  }
}
