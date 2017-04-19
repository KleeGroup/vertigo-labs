
import { Directive, Input, TemplateRef, ViewContainerRef } from '@angular/core';


@Directive({ 
    selector: '[autolabel]' 
})
export class AutolabelDirective {
    
    constructor(
        private templateRef: TemplateRef<any>,
        private viewContainer: ViewContainerRef) { 
  }

  autolabel() {
    console.log("autolabel");
    this.viewContainer.createEmbeddedView(this.templateRef);
  }

}

