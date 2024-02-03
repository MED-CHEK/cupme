import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { DomSanitizer } from '@angular/platform-browser';
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

  constructor(private route: ActivatedRoute, private sanitizer: DomSanitizer) {}

  ngOnInit(): void {
    this.getProtoImage();
  }

  getProtoImage() {
    let path = this.protocol.picture.file;

    if (path == undefined) {
      this.imagePath = '../../../../content/images/Pictos/No-picture.svg';
    } else {
      this.imagePath = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' + path) as string;
    }
  }
}
