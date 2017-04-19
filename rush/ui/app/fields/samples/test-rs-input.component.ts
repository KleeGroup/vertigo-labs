import { Component, Input } from '@angular/core';

import { Employee } from '../../generated/model/employee';

@Component({
  moduleId: module.id,
  templateUrl: './test-rs-input.component.html'
})
export class TestRsInputComponent {
    model: Employee;

    constructor() {
        this.model = new Employee(1, 'test', 'url');
    }
}