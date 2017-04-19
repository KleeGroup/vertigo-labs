import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { MaterialModule } from '@angular/material';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';

import { HomeModule } from './home/home.module';
import { EmployeesModule } from './employees/employees.module';

import { DynamicFormModule } from './employees/forms/dynamic-reactive-form/dynamic-form.module';

import { RsInputComponent } from './fields/components/rs-input.component';
import { TestRsInputComponent } from './fields/samples/test-rs-input.component';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    MaterialModule.forRoot(),
    HomeModule,
    EmployeesModule,
    DynamicFormModule,
    AppRoutingModule
  ],
  declarations: [
    AppComponent,
    RsInputComponent,
    TestRsInputComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
  // Diagnostic only: inspect router configuration
  constructor(router: Router) {
    console.log('Routes: ', JSON.stringify(router.config, undefined, 2));
  }
}