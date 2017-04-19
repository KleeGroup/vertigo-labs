
import { Injectable }   from '@angular/core';
import { FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';

import { Domains } from '../../../generated/model/domain/index';
import { DomainBase } from '../../../generated/model/domain/do-domain-base';

import { FieldBase } from './field-base';

@Injectable()
export class FormGroupService {
  constructor() { }

  toFormGroup(fields: FieldBase<any>[] ) {
    let group: any = {};

    fields.forEach(field => {
      let domain: DomainBase = Domains[field.domainName];
      let validators = field.required ? domain.validators.concat(Validators.required) : domain.validators;

      group[field.key] = new FormControl(field.value || '', validators);
    });
    return new FormGroup(group);
  }
}