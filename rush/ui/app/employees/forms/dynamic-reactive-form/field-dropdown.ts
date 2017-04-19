import { FieldBase } from './field-base';

export class DropdownField extends FieldBase<string> {
  controlType = 'dropdown';
  public options: {id: string, label: string}[] = [];

  constructor(options: {} = {}) {
    super(options);
    this.options = options['options'] || [];
  }
}