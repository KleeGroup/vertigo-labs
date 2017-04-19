
import { Domain, Required, Label, Type } from './entity-decorator';
import { DtObject } from './dt-object';


export class Adresse implements DtObject {
  
  @Domain('DO_ID')
  @Required(true)
  @Label("Pk adresse")
  @Type("ID")
  public adrId: number;
  
  @Domain('DO_LABEL')
  @Required(false)
  @Label("Complément d'adresse")
  public complementAdresse: string;

  @Domain('DO_LABEL')
  @Required(false)
  @Label("Numéro de voie")
  public numeroVoie: string;

  @Domain('DO_CODE_POSTAL')
  @Required(false)
  @Label("Code postal")
  public codePostal: string;

  @Domain('DO_LABEL')
  @Required(false)
  @Label("Ville")
  public ville: string;
  
  @Domain('DO_CODE')
  @Required(true)
  @Label("Pays")
  @Type("FOREIGN_KEY")
  public payCode: string;
  
  constructor(adrId: number,
              complementAdresse: string, 
              numeroVoie: string,
              codePostal: string,
              ville: string,
              payCode: string) {
      this.adrId = adrId;
      this.complementAdresse = complementAdresse;
      this.numeroVoie = numeroVoie;
      this.codePostal = codePostal;
      this.ville = ville;
      this.payCode = payCode;
  }

}


