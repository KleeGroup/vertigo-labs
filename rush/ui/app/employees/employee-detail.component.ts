import { Component, OnInit, HostBinding } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Employee } from 'app/generated/model/employee';

@Component({
  template: `
  <div *ngIf="employee">
    <div>
      <label>Id: </label>{{ employee.emp_id }}
    </div>
  </div>
  `
})
export class EmployeeDetailComponent implements OnInit {
  employee: Employee;

  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    this.route.data
      .subscribe((data: { employee: Employee }) => {
        this.employee = data.employee;
      });
  }


}
