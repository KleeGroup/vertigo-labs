import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { TestRsInputComponent } from './fields/samples/test-rs-input.component';

import { SampleComponent } from './employees/forms/dynamic-reactive-form/sample.component';


const appRoutes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'test', component: TestRsInputComponent},
  { path: 'DynamicForms', component: SampleComponent},
  
];

@NgModule({
  imports: [
    RouterModule.forRoot(
      appRoutes
    )
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { } 