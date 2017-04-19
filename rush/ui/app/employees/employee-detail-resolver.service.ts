import { Injectable } from '@angular/core';
import {
  Router, Resolve, RouterStateSnapshot,
  ActivatedRouteSnapshot
} from '@angular/router';

import { Employee } from 'app/generated/model/employee';
import { EmployeeService } from 'app/generated/services/employees.service';

@Injectable()
export class EmployeeDetailResolver implements Resolve<Employee> {
  constructor(private employeeService: EmployeeService, private router: Router) { }
  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<Employee> {
    let id = route.params['id'];
    return this.employeeService.getEmployee(id).then(employee => {
      return employee;
    });
  }
}