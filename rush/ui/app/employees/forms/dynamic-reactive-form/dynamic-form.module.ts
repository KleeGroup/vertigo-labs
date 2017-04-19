import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';


import { MaterialModule } from '@angular/material';

import { DynamicFieldComponent } from './dynamic-field.component';
import { DynamicFormComponent } from './dynamic-form.component';
import { CustomFormComponent } from './custom-form.component';

import { SampleComponent } from './sample.component';

import { SampleService } from './sample.service';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [
    DynamicFieldComponent,
    DynamicFormComponent,
    CustomFormComponent,
    SampleComponent
  ],
  providers: [SampleService]
})
export class DynamicFormModule { }

