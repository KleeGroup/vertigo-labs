import { Injectable }       from '@angular/core';
import { DropdownField } from './field-dropdown';
import { FieldBase }     from './field-base';
import { TextboxField }  from './field-textbox';

import { fieldBuilder, attachOptions } from './field-builder.service';

import { Observable } from 'rxjs/Observable';

import { Adresse } from '../../../generated/model/adresse';
import { ReferenceObject } from '../../../generated/model/reference-object';

import { ReferenceService } from './reference-list.service';

@Injectable()
export class SampleService {

  constructor(public referenceService: ReferenceService) {
  }

  getAdresse(): Observable<Adresse>{
    let monAdresse = new Adresse(1, 'Complément Adresse', 'Numéro voie', '12345', 'Ville', 'FR');
    return Observable.of(monAdresse);
  }

  attachReferences(fields: FieldBase<any>[], fieldname: string, ref: ReferenceObject[]) {
    fields.filter( property => property.key === fieldname)
          .forEach( field => attachOptions(field as DropdownField, ref));
  }

}

