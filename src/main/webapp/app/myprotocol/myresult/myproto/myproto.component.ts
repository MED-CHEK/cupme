import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ProtocolCartDTO } from '../../../entities/protocol.model';

@Component({
  selector: 'jhi-my-proto',
  templateUrl: './myproto.component.html',
  styleUrls: ['./myproto.component.scss'],
})
export class MyProtoComponent implements OnInit {
  @Input()
  protocol!: ProtocolCartDTO;

  imagePath!: string;
  rating = 4.2;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.imagePath = this.protocol.picture.file ?? '../../../../content/images/Pictos/No-picture.svg';
  }
}
