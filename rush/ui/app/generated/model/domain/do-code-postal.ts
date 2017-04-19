import { ValidatorFn, Validators } from '@angular/forms';
import { DomainBase } from './do-domain-base';

export class DoCodePostal extends DomainBase {
    validators = [Validators.pattern('[0-9]{4,5}')]
}


