import { DomainBase } from './do-domain-base';

import { DoCode }  from './do-code';
import { DoCodePostal } from './do-code-postal';
import { DoDate } from './do-date';
import { DoEmail } from './do-email';
import { DoId } from './do-id';
import { DoLabel } from './do-label';
import { DoTimestamp } from './do-timestamp';
import { DoYesNo } from './do-yes-no';

let Domains: { [key: string]: DomainBase} = {};
Domains['DO_CODE'] = new DoCode();
Domains['DO_CODE_POSTAL'] = new DoCodePostal();
Domains['DO_DATE'] = new DoDate();
Domains['DO_EMAIL'] = new DoEmail();
Domains['DO_ID'] = new DoId();
Domains['DO_LABEL'] = new DoLabel();
Domains['DO_TIMESTAMP'] = new DoTimestamp();
Domains['DO_YES_NO'] = new DoYesNo();

export { Domains };

