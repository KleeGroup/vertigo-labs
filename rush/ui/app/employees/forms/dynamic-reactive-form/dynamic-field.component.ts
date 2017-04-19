import { Component, Input } from '@angular/core';
import { FormGroup }        from '@angular/forms';
import { FieldBase }     from './field-base';

@Component({
  moduleId: module.id,
  selector: 'df-field',
  templateUrl: './dynamic-field.component.html'
})
export class DynamicFieldComponent {
  @Input() 
  field: FieldBase<any>;

  @Input() 
  form: FormGroup;
  
  get isValid() { 
      return this.form.controls[this.field.key].valid; 
  }
  
}
