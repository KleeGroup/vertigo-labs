
import { Domain, Required, Label, Type } from './entity-decorator';
import { DtObject } from './dt-object';


export class Pays implements DtObject {
  
  @Domain('DO_CODE')
  @Required(true)
  @Label("Identifiant Pays")
  @Type("ID")
  public payCode: string;
  
  @Domain('DO_LABEL')
  @Required(true)
  @Label("Libellé")
  public libelle: string;

  @Domain('DO_CODE')
  @Required(false)
  @Label("Code pays ISO 2")
  public codeIsoAlpha2: string;
  
  constructor(payCode: string,
              libelle: string, 
              codeIsoAlpha2: string) {
      this.payCode = payCode;
      this.libelle = libelle;
      this.codeIsoAlpha2 = codeIsoAlpha2;
  }

} 