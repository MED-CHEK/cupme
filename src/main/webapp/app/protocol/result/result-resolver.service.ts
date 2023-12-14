import { Injectable } from '@angular/core';
import { Router, RouterStateSnapshot, ActivatedRouteSnapshot } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap, take } from 'rxjs/operators';
import { ProtocolService } from '../protocol.service';
import { ProtocolDTO } from 'app/entities/protocol.model';
import { ProtocolDetailDTO } from '../../entities/protocol.model';

@Injectable({
  providedIn: 'root',
})
export class ProtocolResolverService {
  constructor(private protocolService: ProtocolService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ProtocolDetailDTO> | Observable<never> {
    const protocolIdStr = route.paramMap.get('id');
    if (!protocolIdStr) {
      return EMPTY;
    }
    const protocolId = +protocolIdStr;
    return this.protocolService.getProtocol(protocolId).pipe(
      take(1),
      mergeMap(protocolDto => {
        if (protocolDto) {
          return of(protocolDto);
        } else {
          // asset non trouvé
          return EMPTY;
        }
      })
    );
  }
}
