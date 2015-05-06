package snowblood.controller.tourdecontrole;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.lang.MessageText;
import io.vertigo.lang.Option;
import io.vertigo.lang.VUserException;
import io.vertigo.struts2.core.ContextForm;
import io.vertigo.struts2.core.ContextList;
import io.vertigo.struts2.core.ContextMdl;
import io.vertigo.struts2.core.ContextRef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import fr.justice.isis.controller.AbstractIsisActionSupport;
import fr.justice.isis.controller.navigation.Menu;
import fr.justice.isis.controller.navigation.NavigationItem;
import fr.justice.isis.domain.tourdecontrole.DossierPfe;
import fr.justice.isis.domain.tourdecontrole.JobMode;
import fr.justice.isis.domain.tourdecontrole.JobRejet;
import fr.justice.isis.domain.tourdecontrole.JobSens;
import fr.justice.isis.domain.tourdecontrole.Jobdefinition;
import fr.justice.isis.domain.tourdecontrole.Jobexecution;
import fr.justice.isis.job.datamodel.JobExecution;
import fr.justice.isis.security.Role;
import fr.justice.isis.services.job.Resources;
import fr.justice.isis.services.parametrage.ResponsabiliteServices;
import fr.justice.isis.services.tourdecontrole.TdcRespoPfe;
import fr.justice.isis.services.tourdecontrole.TourDeControleServices;
import fr.justice.isis.util.SecurityUtil;

public class TourDeControleParametrageAction extends AbstractIsisActionSupport {

	@Inject
	private TourDeControleServices tdcServices;
	@Inject
	private ResponsabiliteServices respoServices;

	private final ContextRef<Long> jobdefIdRef = new ContextRef<>("jodId", Long.class, this);
	private final ContextForm<Jobdefinition> jobdefRef = new ContextForm<>("jobdef", this);
	private final ContextRef<String> typeOngletRef = new ContextRef<>("typeOnglet", String.class, this);
	private final ContextRef<String> enteteRef = new ContextRef<>("entete", String.class, this);
	private final ContextRef<Boolean> execEnCoursRef = new ContextRef<>("execEnCours", Boolean.class, this);

	private final ContextMdl<JobRejet> jobRejetListRef = new ContextMdl<>("jobRejetList", this);
	private final ContextMdl<JobMode> jobModeListRef = new ContextMdl<>("jobModeList", this);
	private final ContextMdl<JobSens> jobSensListRef = new ContextMdl<>("jobSensList", this);

	private final ContextList<TdcRespoPfe> respoRef = new ContextList<>("respo", this);

	private static final String TYPE_ONGLET = "PARAMETRAGE";

	/**
	 *
	 */
	private static final long serialVersionUID = -4190454220338683915L;

	protected void initContext(@Named("jodId") final String jodId) {

		SecurityUtil.hasRole(Role.R_ADMIN_TECH);
		typeOngletRef.set(TYPE_ONGLET);
		jobdefIdRef.set(Long.valueOf(jodId));
		jobdefRef.publish(tdcServices.getJobdefinition(Long.valueOf(jodId)));
		execEnCoursRef.set(execEnCours());

		final DtList<DossierPfe> doss = tdcServices.getDossierPFEparJodId(Long.valueOf(jodId));
		respoRef.publish(respoServices.buildRespoPfe(doss));

		enteteRef.set("Paramétrage - " + jobdefRef.readDto().getLibelle());
		toModeReadOnly();

		jobRejetListRef.publish(JobRejet.class, null);
		jobModeListRef.publish(JobMode.class, null);
		jobSensListRef.publish(JobSens.class, null);

	}

	@Override
	public String getPageName() {
		return "Paramétrage";
	}

	@Override
	public List<NavigationItem> getPageParents() {
		final List<NavigationItem> listNavItem = new ArrayList<>();
		listNavItem.add(new NavigationItem("TourDeControleDetail", "Liste des exécutions", "TourDeControleDetail.do?jodId="
				+ jobdefIdRef.get().toString()));
		return listNavItem;
	}

	@Override
	public Menu getActiveMenu() {
		return Menu.TOURDECONTROLE;
	}

	/**
	 * Redirection struts bouton annuler.
	 *
	 * @return redirection struts.
	 */
	public String annuler() {
		initContext(jobdefIdRef.get().toString());
		return NONE;
	}

	/**
	 * Contrôle si le service n'est pas en cours d'exécution
	 *
	 * @return
	 */
	private boolean execEnCours() {
		final Option<DtList<Jobexecution>> listExecutionEnCours = tdcServices.getJobexecutionEncours(jobdefIdRef.get());
		return listExecutionEnCours.isDefined() ? !listExecutionEnCours.get().isEmpty() : false;
	}

	/**
	 * Enregistre le Jobdefinition
	 *
	 * @return redirection struts
	 */
	public String enregistrer() {

		tdcServices.saveJobdefinition(jobdefRef.readDto());
		toModeReadOnly();
		return NONE;
	}

	public String lancementManuel() {
		final Jobdefinition j = jobdefRef.readDto();
		if ((!execEnCours() || j.getMultiExecutions()) && j.getManuelAutorise()) {
			tdcServices.getJobManager().scheduleNow(j.getCode(), null);
		} else {
			throw new VUserException(new MessageText(Resources.OPERATION_IMPOSSIBLE));
		}
		return NONE;
	}

	public String lancementComplet() {
		final Jobdefinition j = jobdefRef.readDto();
		if ((!execEnCours() || j.getMultiExecutions()) && j.getCompletPossible()) {
			final Map<String, String> params = new HashMap<>();
			params.put(JobExecution.PARAM_FORCE_COMPLET, Boolean.TRUE.toString());
			tdcServices.getJobManager().scheduleNow(j.getCode(), params);
		} else {
			throw new VUserException(new MessageText(Resources.OPERATION_IMPOSSIBLE));
		}
		return NONE;
	}

	public String lancementABlanc() {
		final Jobdefinition j = jobdefRef.readDto();
		if ((!execEnCours() || j.getMultiExecutions()) && j.getTestable()) {
			final Map<String, String> params = new HashMap<>();
			params.put(JobExecution.PARAM_A_BLANC, Boolean.TRUE.toString());
			tdcServices.getJobManager().scheduleNow(j.getCode(), params);
		} else {
			throw new VUserException(new MessageText(Resources.OPERATION_IMPOSSIBLE));
		}
		return NONE;
	}

	public String modifier() {
		toModeEdit();
		return NONE;
	}

}
