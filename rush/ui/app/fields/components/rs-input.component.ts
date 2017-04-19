
import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  moduleId: module.id,
  selector: 'rs-input',
  templateUrl: './rs-input.component.html',
  styleUrls: ['./rs-input.component.css']
})
export class RsInputComponent {
    @Input()
    fieldLabel: string;
    
    @Input()
    fieldName: string;

    @Input()
    rsNgModel: any;

    @Output() 
    rsNgModelChange: any = new EventEmitter();              

    constructor() {

    }

    updateData(event: any) {
      this.rsNgModel = event;
      this.rsNgModelChange.emit(event);
    }

}
