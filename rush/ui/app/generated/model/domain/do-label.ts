import { ValidatorFn, Validators } from '@angular/forms';
import { DomainBase } from './do-domain-base';

export class DoLabel extends DomainBase {
    validators = [Validators.maxLength(100)]
}


