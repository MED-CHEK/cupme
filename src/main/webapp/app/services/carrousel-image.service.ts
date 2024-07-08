import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class CarrouselImageService {
  private imagesUrl = '../../content/images/carousel_sports/images.json';

  constructor(private http: HttpClient) {}

  getImages(): Observable<string[]> {
    return this.http.get<string[]>(this.imagesUrl);
  }
}
