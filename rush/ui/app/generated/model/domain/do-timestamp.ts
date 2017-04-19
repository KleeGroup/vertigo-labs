import { ValidatorFn, Validators } from '@angular/forms';
import { DomainBase } from './do-domain-base';

export class DoTimestamp extends DomainBase {
    validators = [Validators.pattern('\d{1,2}/\d{2}/[1-2]{1}\d{3} [0-2]{1}[0-9]{1}:[0-6]{1}[0-9]{1}:[0-6]{1}[0-9]{1}')]
}


