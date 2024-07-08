import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { MyProtocolDTO, ProtocolDTO } from 'app/entities/protocol.model';
import { DomSanitizer } from '@angular/platform-browser';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { Howl } from 'howler';
import { Genre } from '../../entities/genre.enum';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-my-detail',
  templateUrl: './mydetail.component.html',
  styleUrls: ['./mydetail.component.scss'],
})
export class MyDetailComponent implements OnInit {
  protocol!: MyProtocolDTO;
  account!: Account;
  duree!: number;
  step: number = 0;
  poseTime: number = 0;
  aspiration: number = 0;

  timer!: string;
  sound!: Howl;

  initialTime!: number;
  timeLeft!: number;
  timerInterval: any;
  isTimerRunning: boolean = false;

  constructor(private route: ActivatedRoute, private accountService: AccountService, private router: Router) {}

  ngOnInit(): void {
    this.sound = new Howl({
      src: ['../../../content/sound/mixkit-bell-notification-933.wav'], // Replace with the path to your sound file
    });

    this.protocol = this.route.snapshot.data['protocol'];

    this.accountService.identity().subscribe(account => {
      this.account = account as Account;
    });

    this.getAspirationAndPoseTime();

    this.getDuree();
    this.initTimer();
  }

  getDuree() {
    if (this.protocol.id === 7537 || this.protocol.id === 7539) this.duree = this.initialTime * 4;
    else if (this.protocol.id === 7555 || this.protocol.id === 7527 || this.protocol.id === 7494)
      this.duree = this.initialTime * 2 + this.getPoseTime() * 2;
    else if (this.protocol.id === 7530 || this.protocol.id === 7486) this.duree = this.initialTime + this.getPoseTime() * 2;
    else this.duree = this.initialTime + this.getPoseTime();
  }

  getAspirationAndPoseTime() {
    if (this.account && this.account.age && this.account.age >= 10 && this.account.age < 16 && this.protocol) {
      this.aspiration = 1;
      this.poseTime = (this.protocol.poseTime * 0.8) as number;
    } else if (
      this.account &&
      this.protocol &&
      this.account.age &&
      ((this.account.age >= 16 && this.account.age < 20) || (this.account.age >= 50 && this.account.age < 60))
    ) {
      this.aspiration = 2;
      this.poseTime = (this.protocol.poseTime * 0.9) as number;
    } else if (this.account && this.account.age && this.account.age >= 20 && this.account.age < 50 && this.protocol) {
      this.aspiration = 3;
      if (this.account.sex == Genre.FEMME) {
        this.poseTime = 6;
      } else this.poseTime = 8;
    } else if (this.account && this.account.age && this.account.age >= 60 && this.protocol) {
      this.aspiration = 1.5;
      this.poseTime = (this.protocol.poseTime * 0.8) as number;
    }

    this.initialTime = this.poseTime;
    this.timeLeft = this.initialTime * 60;
  }

  getPoseTime(): number {
    if (this.poseTime == 8) return 6;
    else if (this.poseTime == 6 && this.account.sex == Genre.FEMME) return 5;
    else return this.poseTime;
  }

  nextStep() {
    this.step++;
  }

  prevStep() {
    this.step--;
  }

  fin() {
    this.step = 0;
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

  goToTuto() {
    this.router.navigate(['/tutos']);
  }
}
