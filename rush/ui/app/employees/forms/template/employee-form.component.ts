
import { Component } from '@angular/core';

import { Employee } from '../../../generated/model/employee';

@Component({
  moduleId: module.id,
  selector: 'hero-from',
  templateUrl: './employee-form.component.html'
})
export class EmployeeFormComponent {

  powers = ['Really Smart', 'Super Flexible',
            'Super Hot', 'Weather Changer'];

  model = new Employee(10, 'Super Angular 2', 'https://angular.io/resources/images/logos/angular2/angular.svg');

  submitted = false;

  onSubmit() { 
    this.submitted = true;
  }

  // TODO: Remove this when we're done
  get diagnostic() { 
      return JSON.stringify(this);
  }

  newHero() {
    this.model = new Employee(null, '', '');
  }


}