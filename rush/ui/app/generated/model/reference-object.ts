
import { Domain, Required, Label, Type } from './entity-decorator';
import { DtObject } from './dt-object';


export class ReferenceObject implements DtObject {
  
  @Domain('DO_CODE')
  @Required(true)
  @Label("Identifiant")
  @Type("ID")
  public id: string;
  
  @Domain('DO_LABEL')
  @Required(true)
  @Label("Libellé")
  public label: string;

  constructor(id: string,
              label: string) {
      this.id = id;
      this.label = label;
  }

} 