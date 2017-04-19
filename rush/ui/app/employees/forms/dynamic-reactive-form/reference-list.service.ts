import { Injectable }       from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { delay } from 'rxjs/operator/delay';

import { ReferenceObject } from '../../../generated/model/reference-object';

@Injectable()
export class ReferenceService {

   getPays(): Observable<ReferenceObject[]> {
    let ref: ReferenceObject[] = [new ReferenceObject('FR', 'France'),
                                  new ReferenceObject('EN', 'Angleterre'),
                                  new ReferenceObject('ES', 'Espagne')];
    
    return Observable.of(ref);
  }

}

