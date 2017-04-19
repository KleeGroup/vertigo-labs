
import { DtObject } from '../../../generated/model/dt-object';
import { FieldBase } from './field-base';
import { TextboxField } from './field-textbox';
import { DropdownField } from './field-dropdown';

import { getDomain, getRequired, getLabel, getType } from '../../../generated/model/entity-decorator';

import { ReferenceObject } from '../../../generated/model/reference-object'

export function fieldBuilder(object: DtObject, propertyName: string) : FieldBase<any> {
    let domainName: string = getDomain(object, propertyName);
    let required: boolean = getRequired(object, propertyName);
    let label: string = getLabel(object, propertyName);
    let type: string = getType(object, propertyName);

    let opt = { 
      value: object[propertyName],
      key: propertyName,
      label: label,
      required: required,
      type: type === 'ID' ? 'hidden' : undefined,
      domainName: domainName
    }

    //TODO à réécrire
    if (domainName === 'DO_CODE_POSTAL') {
        return new TextboxField(opt)
    } else if (domainName === 'DO_ID') {
        return new TextboxField(opt)
    } else if (domainName === 'DO_LABEL') {
        return new TextboxField(opt)
    } else if (domainName === 'DO_CODE') {
        if (type === 'FOREIGN_KEY') {
            return new DropdownField(opt);
        } else {
            return new TextboxField(opt);
        }
        
    }
    
    return null;
}

export function attachOptions(field: DropdownField, ref: ReferenceObject[]) {
    field.options = ref;
}
