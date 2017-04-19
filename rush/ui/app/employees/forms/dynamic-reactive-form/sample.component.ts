import { Component, OnInit }       from '@angular/core';
import { Observable } from 'rxjs/Observable';

import { SampleService } from './sample.service';

import { Adresse } from '../../../generated/model/adresse';

import { ReferenceService } from './reference-list.service';

import { FieldBase } from './field-base';
import { ReferenceObject } from '../../../generated/model/reference-object';

import { fieldBuilder } from './field-builder.service';


@Component({
  selector: 'my-app',
  template: `
    <div>
      <h2>Dynamic Forms</h2>
      <h3>Generic form</h3>
      <dynamic-form [fields]="fields"></dynamic-form>

      <h3>Custom Forms</h3>
      <custom-form [fields]="fields"></custom-form>

    </div>
  `,
  providers:  [SampleService, ReferenceService]
})
export class SampleComponent implements OnInit {
  fields: FieldBase<any>[];
  pays: ReferenceObject[];
  adresse: Adresse;

  constructor(public service: SampleService, public referenceService: ReferenceService) {

  }

  ngOnInit() {

    /*Observable.forkJoin([ this.referenceService.getPays(), this.service.getAdresse()]).subscribe(results => {
      this.pays = results[0];
      this.adresse = results[1];
      this.updateFields();  
    });*/

    this.referenceService.getPays().subscribe( pays => { 
      this.pays = pays;
      this.updateFields();
    });
    this.service.getAdresse().subscribe( adresse => {
      this.adresse = adresse;
      this.updateFields();
    });

  }

  updateFields() {
    if (this.adresse) {
      this.fields = Object.keys(this.adresse)
                          .map( property => fieldBuilder(this.adresse, property));
    }

    if (this.fields && this.pays) {
      this.service.attachReferences(this.fields, 'payCode', this.pays);
    }
    
  }

}

