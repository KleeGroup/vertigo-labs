import { ValidatorFn, Validators } from '@angular/forms';
import { DomainBase } from './do-domain-base';

export class DoEmail extends DomainBase {
    validators = [Validators.pattern('\w+@\w+\.\w+')]
}
