import { ValidatorFn, Validators } from '@angular/forms';
import { DomainBase } from './do-domain-base';

export class DoId extends DomainBase {
    validators = [Validators.nullValidator]
}



