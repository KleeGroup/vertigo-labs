import { ValidatorFn, Validators } from '@angular/forms';
import { DomainBase } from './do-domain-base';

export class DoYesNo extends DomainBase {
    //TODO
    validators = [Validators.nullValidator]
}


