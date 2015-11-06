package snowblood.services.tourdecontrole;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.lang.Option;
import snowblood.gen.domain.Jobdefinition;
import snowblood.gen.domain.Jobexecution;
import snowblood.gen.services.TdcDetailCritere;

/**
 * Services pour la tour de contrôle.
 *
 * @author fdangelotillon
 */
public interface TourDeControleServices {

	/**
	 * Pour un Jobdefinition donné, renvoie tous les Jobexecution correspondant, classé par dates de fin décroissantes.
	 *
	 * @param jodId ID du JobDefinition.
	 * @return la liste de Jobexecution.
	 */
	DtList<Jobexecution> getJobexecutionParJobdefinition(Long jodId);

	/**
	 * Renvoie la liste des Jobexecution liés au Jobdefinition donné, en accord avec le critère (dates et état).
	 *
	 * @param jodId l'ID du Jobdefinition.
	 * @param critere le formulaire .
	 * @return La liste de jobexecution.
	 */
	Option<DtList<Jobexecution>> getListeJobexecutionFiltree(Long jodId, TdcDetailCritere critere);

	/**
	 * Récupère un jobdefinition.
	 *
	 * @param jodId Identifiant du jobdefinition.
	 * @return Le Jobdefinition.
	 */
	Jobdefinition getJobdefinition(Long jodId);

	/**
	 * Enregistre le jobdefinition.
	 *
	 * @param jobdef Jobdefinition à enregistrer.
	 */
	void saveJobdefinition(Jobdefinition jobdef);

	/**
	 * Enregistre le jobexecution.
	 *
	 * @param jobex Jobexecution à enregistrer.
	 */
	void saveJobexecution(Jobexecution jobex);

	/**
	 * Renvoie l'ensemble des exécutions d'un job ayant pour état 'En cours'.
	 *
	 * @param jodId Identifiant du job.
	 * @return Le jobexecution.
	 */
	Option<DtList<Jobexecution>> getJobexecutionEncours(Long jodId);

	/**
	 * Renvoie la liste des responsabilites et dossiers PFE associés au job indiqué.
	 *
	 * @param jodId l'ID du Jobdefinition.
	 * @return la liste de dossierPfe.
	 */
	//spé DtList<DossierPfe> getDossierPFEparJodId(Long jodId);

	/**
	 * Renvoie l'exécution du job.
	 *
	 * @param exeId id d'exécution
	 * @return le jobexecution
	 */
	Jobexecution getJobexecution(Long exeId);

	/**
	 * Renvoie le dernier jobexecution pour un jobdefinition donné.
	 *
	 * @param jodId id du jobdefinition.
	 * @return le jobexecution demandé.
	 */
	Jobexecution getLastJobexecutionParJobdefinition(Long jodId);

	/**
	 * Exécute le batch de maintenance de la tour de contrôle.
	 */
	void batchMaintenance();

}
