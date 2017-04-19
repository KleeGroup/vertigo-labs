import { Component }                            from '@angular/core';
import { FormBuilder, FormGroup, Validators  }  from '@angular/forms';

import { states, Address, Hero } from './data-model';


@Component({
  moduleId: module.id,
  selector: 'hero-detail',
  templateUrl: './hero-detail.component.html'
})
export class EmployeeFormReactiveComponent {
  heroForm: FormGroup;
  states = states;
  hero: Hero;

  constructor(private formBuilder: FormBuilder) {
    this.createForm();
  }

  createForm() {
    this.heroForm = this.formBuilder.group({
      name: ['', Validators.required],
      address: this.formBuilder.group( new Address()),
      power: '',
      sidekick: ''
    });
    
  }

  setValue() {
    this.heroForm.setValue({
      name: this.hero.name,
      address: this.hero.addresses[0] || new Address()
    })
  }
  
  patchValue() {
    this.heroForm.patchValue({
      name: this.hero.name
    });
  }
  

}