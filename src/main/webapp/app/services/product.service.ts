import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { ProductCartDTO } from 'app/entities/product.model';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ProductService {
  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  getProducts(): Observable<ProductCartDTO[]> {
    return this.http.get<ProductCartDTO[]>(this.applicationConfigService.getEndpointFor('api/products'));
  }
}
