import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function createCustomDateValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;

    if (!value) {
      return null;
    }

    if (!value.day && !value.month && !value.year) {
      return { dateFormat: true };
    }

    return null;
  };
}
