import { Component } from '@angular/core';

@Component({
  selector: 'jhi-tutos',
  templateUrl: './tutos.component.html',
  styleUrls: ['./tutos.component.scss'],
})
export class TutosComponent {
  videos = [
    {
      id: 1,
      title: 'Pose et dépose des ventouses',
      thumbnail: '../../content/videos/main-images/Pose.jpeg',
    },
    {
      id: 2,
      title: 'Décalage des ventouses',
      thumbnail: '../../content/videos/main-images/Décalage.jpeg',
    },
    {
      id: 3,
      title: "Pour plus d'autonomie",
      thumbnail: '../../content/videos/main-images/autonomie.jpeg',
    },
  ];
  selectedVideo: { id: number; title: string; thumbnail: string } | null = null;

  constructor() {}

  selectVideo(video: { id: number; title: string; thumbnail: string }) {
    this.selectedVideo = video;
  }
}
