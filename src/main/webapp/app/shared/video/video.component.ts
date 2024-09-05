import { Component } from '@angular/core';

@Component({
  selector: 'app-video',
  template: `
    <video width="100%" autoplay autoplay loop poster="../../../content/images/main.png" controls>
      <source src="../../../content/videos/PUB_CUPME.mp4" type="video/mp4" />
      Your browser does not support the video tag.
    </video>
  `,
  styles: [],
})
export class VideoComponent {}
