import { Component, forwardRef, HostBinding, Injectable } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { NgbCalendar, NgbDateAdapter, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';

@Injectable()
export class CustomAdapter extends NgbDateAdapter<NgbDateStruct> {
  readonly DELIMITER = '-';

  fromModel(value: NgbDateStruct | null): NgbDateStruct | null {
    if (value) {
      return value;
    }
    return null;
  }

  toModel(date: NgbDateStruct | null): NgbDateStruct | null {
    return date;
  }
}

@Component({
  selector: 'sec-date',
  templateUrl: './date.component.html',
  styleUrls: ['./date.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => DateComponent),
      multi: true,
    },
    { provide: NgbDateAdapter, useClass: CustomAdapter },
  ],
})
export class DateComponent implements ControlValueAccessor {
  model!: NgbDateStruct;

  @HostBinding('className')
  componentClass: string = 'sec-date';

  constructor(private ngbCalendar: NgbCalendar, private dateAdapter: NgbDateAdapter<NgbDateStruct>) {}

  setValue(date: NgbDateStruct) {
    this.model = date;
    this.propagateChange(this.model);
  }

  writeValue(obj: any): void {
    if (obj !== undefined) {
      this.model = obj;
    }
  }

  propagateChange = (_: any) => {};

  registerOnChange(fn: any) {
    this.propagateChange = fn;
  }

  registerOnTouched() {}

  onDateChange() {
    this.propagateChange(this.model);
  }
}
