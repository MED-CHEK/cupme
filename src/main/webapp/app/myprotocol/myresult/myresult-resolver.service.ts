import { Injectable } from '@angular/core';
import { Router, RouterStateSnapshot, ActivatedRouteSnapshot } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap, take } from 'rxjs/operators';
import { MyProtocolService } from '../myprotocol.service';
import { ProtocolDTO } from 'app/entities/protocol.model';

@Injectable({
  providedIn: 'root',
})
export class MyProtocolResolverService {
  constructor(private myprotocolService: MyProtocolService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ProtocolDTO> | Observable<never> {
    const protocolIdStr = route.paramMap.get('id');
    if (!protocolIdStr) {
      return EMPTY;
    }
    const protocolId = +protocolIdStr;
    return this.myprotocolService.getProtocol(protocolId).pipe(
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
