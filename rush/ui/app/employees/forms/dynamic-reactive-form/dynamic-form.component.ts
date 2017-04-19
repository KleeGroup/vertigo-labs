import { Component, Input, OnInit }  from '@angular/core';
import { FormGroup }                 from '@angular/forms';
import { FieldBase }              from './field-base';
import { FormGroupService }    from './form-group.service';

@Component({
  moduleId: module.id,
  selector: 'dynamic-form',
  templateUrl: './dynamic-form.component.html',
  providers: [ FormGroupService ]
})
export class DynamicFormComponent implements OnInit {
  @Input() 
  fields: FieldBase<any>[] = [];

  form: FormGroup;

  payLoad = '';
  
  constructor(private fgs: FormGroupService) {  }

  ngOnInit() {
    this.form = this.fgs.toFormGroup(this.fields);
  }

  onSubmit() {
    this.payLoad = JSON.stringify(this.form.value);
  }

}
