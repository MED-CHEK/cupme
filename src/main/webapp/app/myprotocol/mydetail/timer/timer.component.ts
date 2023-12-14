import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Howl } from 'howler';

@Component({
  selector: 'jhi-timer',
  templateUrl: './timer.component.html',
  styleUrls: ['./timer.component.scss'],
})
export class TimerComponent implements OnInit {
  @Input() poseTime: number = 0;

  timer!: string;
  sound!: Howl;

  initialTime!: number;
  timeLeft!: number;
  timerInterval: any;
  isTimerRunning: boolean = false;

  constructor() {}

  ngOnInit(): void {
    this.sound = new Howl({
      src: ['../../../content/sound/mixkit-bell-notification-933.wav'], // Replace with the path to your sound file
    });

    this.initialTime = this.poseTime;
    this.timeLeft = this.initialTime * 60;
    this.initTimer();
  }

  initTimer() {
    let seconds: number = this.timeLeft;
    let textSec: any = '0';
    let statSec: number = seconds % 60;

    const prefix = this.initialTime < 10 ? '0' : '';

    if (statSec < 10) {
      textSec = '0' + statSec;
    } else textSec = statSec;

    this.timer = `${prefix}${Math.floor(seconds / 60)}:${textSec}`;
  }

  toggleTimer() {
    if (this.isTimerRunning) {
      this.stopTimer();
    } else {
      this.startTimer();
    }
  }

  startTimer() {
    let seconds: number = this.timeLeft;
    let textSec: any = '0';
    let statSec: number = seconds % 60;

    const prefix = this.initialTime < 10 ? '0' : '';

    this.timerInterval = setInterval(() => {
      seconds--;
      if (statSec != 0) statSec--;
      else statSec = 59;

      if (statSec < 10) {
        textSec = '0' + statSec;
      } else textSec = statSec;

      this.timer = `${prefix}${Math.floor(seconds / 60)}:${textSec}`;
      this.timeLeft--;
      if (this.timeLeft === 0) {
        this.playSound();
        this.resetTimer();
      }
    }, 1000);
    this.isTimerRunning = true;
  }

  stopTimer() {
    clearInterval(this.timerInterval);
    this.isTimerRunning = false;
  }

  resetTimer() {
    clearInterval(this.timerInterval);
    this.timeLeft = this.initialTime * 60;
    this.initTimer();
    this.isTimerRunning = false;
  }

  playSound() {
    this.sound.play();
  }
}
