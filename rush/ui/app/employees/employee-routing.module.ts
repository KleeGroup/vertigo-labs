import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { EmployeeDetailResolver } from './employee-detail-resolver.service';

import { EmployeeListComponent } from './employee-list.component';
import { EmployeeDetailComponent } from './employee-detail.component';

import { EmployeeFormComponent } from './forms/template/employee-form.component';
import { EmployeeFormReactiveComponent } from './forms/reactive/hero-detail.component';

const employeesListRoutes: Routes = [
  {
    path: 'employees',
    component: EmployeeListComponent

  },
  {
    path: 'employees/:id',
    component: EmployeeDetailComponent,
    resolve: {
      employee: EmployeeDetailResolver
    }
  },
  {
    path: 'form',
    component: EmployeeFormComponent
  },
  {
    path: 'form-reactive',
    component: EmployeeFormReactiveComponent
  },
];

@NgModule({
  imports: [
    RouterModule.forChild(employeesListRoutes)
  ],
  exports: [
    RouterModule
  ],
  providers: [
    EmployeeDetailResolver
  ]
})
export class EmployeeRoutingModule { }
