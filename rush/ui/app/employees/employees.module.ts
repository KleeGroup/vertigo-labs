import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';


import { MaterialModule } from '@angular/material';

import { EmployeeListComponent } from './employee-list.component'
import { EmployeeDetailComponent } from './employee-detail.component'
import { EmployeeFormComponent } from './forms/template/employee-form.component'
import { EmployeeFormReactiveComponent } from './forms/reactive/hero-detail.component';

import { EmployeeService } from 'app/generated/services/employees.service';

import { RouteReuseStrategy } from "@angular/router";

import { EmployeeRoutingModule } from './employee-routing.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
    EmployeeRoutingModule
  ],
  declarations: [
    EmployeeListComponent,
    EmployeeDetailComponent,
    EmployeeFormComponent,
    EmployeeFormReactiveComponent
  ],
  providers: [EmployeeService]
})
export class EmployeesModule { }