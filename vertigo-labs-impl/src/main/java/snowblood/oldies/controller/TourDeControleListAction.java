package snowblood.oldies.controller;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.lang.Option;
import io.vertigo.struts2.core.ContextList;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import fr.justice.isis.controller.AbstractIsisActionSupport;
import fr.justice.isis.controller.navigation.Menu;
import fr.justice.isis.controller.navigation.NavigationItem;
import fr.justice.isis.domain.tourdecontrole.Jobdefinition;
import fr.justice.isis.domain.tourdecontrole.Jobexecution;
import fr.justice.isis.job.JobManager;
import fr.justice.isis.model.job.CodeJobEtat;
import fr.justice.isis.services.tourdecontrole.TdcAffichage;
import fr.justice.isis.services.tourdecontrole.TourDeControleServices;

/**
 * Controller lié à la page d'accueil de la tour de contrôle.
 *
 * @author fdangelotillon
 */

public class TourDeControleListAction extends AbstractIsisActionSupport {

	private static final long serialVersionUID = 7830349470163311738L;

	private final ContextList<TdcAffichage> listJobsRef = new ContextList<>("listJobs", this);

	@Inject
	private JobManager jobManager;

	@Inject
	private TourDeControleServices tdcServices;

	@Override
	protected void initContext() {

		listJobsRef.publish(buildTdcAffichage());
	}

	private DtList<TdcAffichage> buildTdcAffichage() {
		final Map<String, Jobdefinition> jobs = jobManager.retrieveJobs();
		final DtList<TdcAffichage> result = new DtList<>(TdcAffichage.class);

		for (final Jobdefinition jobdef : jobs.values()) {

			final TdcAffichage aff = new TdcAffichage();
			aff.setJodId(jobdef.getJodId());
			aff.setService(jobdef.getLibelle());
			aff.setDescription(jobdef.getDescription());
			aff.setActif(jobdef.getActivation());
			aff.setFrequence(jobdef.getFrequence());
			aff.setLibelle(jobdef.getLibelle());

			final Jobexecution jobex = tdcServices.getLastJobexecutionParJobdefinition(jobdef.getJodId());
			final Option<DtList<Jobexecution>> listExecutionEnCours = tdcServices.getJobexecutionEncours(jobdef.getJodId());
			final boolean enCours = listExecutionEnCours.isDefined() ? !listExecutionEnCours.get().isEmpty() : false;
			aff.setEtat(enCours ? CodeJobEtat.EN_COURS.getCode() : jobex == null ? null : jobex.getJetCd());
			aff.setDernierRun(jobex == null ? null : jobex.getFin());
			// TODO : Prochain run
			result.add(aff);
		}

		return result;
	}

	@Override
	public String getPageName() {
		return null;
	}

	@Override
	public List<NavigationItem> getPageParents() {
		return null;
	}

	@Override
	public Menu getActiveMenu() {
		return Menu.TOURDECONTROLE;
	}
}
