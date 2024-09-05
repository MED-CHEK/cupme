import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ProtocolCartDTO } from '../../entities/protocol.model';

@Component({
  selector: 'jhi-proto',
  templateUrl: './proto.component.html',
  styleUrls: ['./proto.component.scss'],
})
export class ProtoComponent implements OnInit {
  @Input()
  protocol!: ProtocolCartDTO;

  imagePath!: string;
  rating = 4.2;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    /*  this.route.queryParams.pipe(mergeMap(params => this.detailService.get(params.key))).subscribe({
      next: () => (this.success = true),
      error: () => (this.error = true),
    }); */
  }
}
