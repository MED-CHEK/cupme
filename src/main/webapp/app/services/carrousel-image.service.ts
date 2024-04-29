import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class CarrouselImageService {
  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  getCarrouselImages(): Observable<string[]> {
    return this.http.get<string[]>(this.applicationConfigService.getEndpointFor('api/carrousel-images'));
  }
}
