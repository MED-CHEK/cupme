import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { ProductCartDTO, ProductDTO, ProductDTOPics } from 'app/entities/product.model';

@Injectable({ providedIn: 'root' })
export class ProductManagementService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/products');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(product: ProductDTOPics): Observable<ProductDTOPics> {
    return this.http.post<ProductDTOPics>(this.resourceUrl, product);
  }

  update(product: ProductDTOPics): Observable<ProductDTOPics> {
    return this.http.put<ProductDTOPics>(this.resourceUrl, product);
  }

  find(id: string): Observable<ProductDTO> {
    return this.http.get<ProductDTO>(`${this.resourceUrl}/${id}`);
  }

  query(): Observable<ProductCartDTO[]> {
    return this.http.get<ProductCartDTO[]>(this.resourceUrl);
  }

  delete(id: number): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/${id}`);
  }

  authorities(): Observable<string[]> {
    return this.http.get<string[]>(this.applicationConfigService.getEndpointFor('api/authorities'));
  }
}
