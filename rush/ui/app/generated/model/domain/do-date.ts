import { ValidatorFn, Validators } from '@angular/forms';
import { DomainBase } from './do-domain-base';

export class DoDate extends DomainBase {
    validators = [Validators.pattern('[0-9]{1,2}/[0-9]{2}/[1-2]{1}[0-9]{3}')]
}

