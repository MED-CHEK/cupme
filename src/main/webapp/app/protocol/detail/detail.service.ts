import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

import { ApplicationConfigService } from 'app/core/config/application-config.service';

@Injectable({ providedIn: 'root' })
export class DetailService {
  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}
}
