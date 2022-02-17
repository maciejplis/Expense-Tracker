import {Pipe, PipeTransform} from '@angular/core';
import {ValidationErrors} from "@angular/forms";

export type ErrorMessages = { [key: string]: string }

@Pipe({
  name: 'mapErrorMessages'
})
export class ErrorMessagesConverterPipe implements PipeTransform {

  transform(validationErrors: ValidationErrors | null, messages: ErrorMessages): string[] {
    return validationErrors != null ? Object.keys(validationErrors).map(key => messages[key]) : [];
  }
}
