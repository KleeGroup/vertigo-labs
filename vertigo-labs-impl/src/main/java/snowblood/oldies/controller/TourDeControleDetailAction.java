package snowblood.oldies.controller;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.file.model.KFile;
import io.vertigo.struts2.core.ContextForm;
import io.vertigo.struts2.core.ContextList;
import io.vertigo.struts2.core.ContextMdl;
import io.vertigo.struts2.core.ContextRef;
import io.vertigo.struts2.core.KFileResponseBuilder;
import io.vertigo.struts2.core.UiList;
import io.vertigo.struts2.core.UiObjectValidator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;

import snowblood.services.file.FileServices;
import fr.justice.isis.controller.AbstractIsisActionSupport;
import fr.justice.isis.controller.navigation.Menu;
import fr.justice.isis.controller.navigation.NavigationItem;
import fr.justice.isis.domain.tourdecontrole.JobEtat;
import fr.justice.isis.domain.tourdecontrole.Jobdefinition;
import fr.justice.isis.domain.tourdecontrole.Jobexecution;
import fr.justice.isis.security.Role;
import fr.justice.isis.services.tourdecontrole.TdcDetailAffichage;
import fr.justice.isis.services.tourdecontrole.TdcDetailCritere;
import fr.justice.isis.services.tourdecontrole.TourDeControleServices;
import fr.justice.isis.util.SecurityUtil;

/**
 * Controller lié à la page détaillant les exécutions d'un service donné
 *
 * @author fdangelotillon
 */

public class TourDeControleDetailAction extends AbstractIsisActionSupport {

	/**
	 *
	 */
	private static final long serialVersionUID = 895539319036011023L;

	private final ContextRef<Long> jobdefIdRef = new ContextRef<>("jodId", Long.class, this);
	private final ContextList<TdcDetailAffichage> detailsRef = new ContextList<>("details", this);
	private final ContextForm<TdcDetailCritere> critereRef = new ContextForm<>("critere", this);
	private final ContextMdl<JobEtat> jobEtatMdl = new ContextMdl<>("jobEtat", this);
	private final ContextRef<String> typeOngletRef = new ContextRef<>("typeOnglet", String.class, this);
	private final ContextForm<Jobdefinition> jobdefRef = new ContextForm<>("jobdef", this);
	private final ContextRef<String> enteteRef = new ContextRef<>("entete", String.class, this);

	private static final int NB_JOURS_RECHERCHE_DEFAUT = 2;

	private static final long NB_MILLIS_DS_H = 1000 * 60 * 60;
	private static final long NB_MILLIS_DS_JOUR = 1000 * 60 * 60 * 24;

	private static final String TYPE_ONGLET = "DETAIL";

	@Inject
	private TourDeControleServices tdcServices;
	@Inject
	private FileServices fileServices;

	protected void initContext(@Named("jodId") final String jodId) {

		// TODO sécurité
		SecurityUtil.hasRole(Role.R_ADMIN_TECH);
		typeOngletRef.set(TYPE_ONGLET);
		final Long id = Long.valueOf(jodId);
		jobdefIdRef.set(id);
		jobdefRef.publish(tdcServices.getJobdefinition(id));
		jobEtatMdl.publish(JobEtat.class, null);
		createCriterion();
		detailsRef.publish(convert(tdcServices.getJobexecutionParJobdefinition(id)));
		enteteRef.set("Exécutions - " + jobdefRef.readDto().getLibelle());
		toModeEdit();
	}

	/**
	 * Convertit une liste de Jobexecution en une liste affichable sur la page
	 *
	 * @param jobex
	 * @return
	 */
	private DtList<TdcDetailAffichage> convert(final DtList<Jobexecution> jobex) {

		final DtList<TdcDetailAffichage> affList = new DtList<>(TdcDetailAffichage.class);

		for (final Jobexecution j : jobex) {
			final TdcDetailAffichage aff = new TdcDetailAffichage();
			final DateFormat formatterDate = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, new Locale("FR", "fr"));
			aff.setDateDebut(j.getDebut());
			aff.setDateFin(j.getFin());
			Long diff = 0L;
			if (j.getFin() == null) {
				aff.setDateFinFormatte(StringUtils.EMPTY);
			} else {
				aff.setDateFinFormatte(formatterDate.format(aff.getDateFin()));
				diff = j.getFin().getTime();
			}
			if (j.getDebut() == null) {
				aff.setDateDebutFormatte(StringUtils.EMPTY);
				diff = 0L;
			} else {
				aff.setDateDebutFormatte(formatterDate.format(aff.getDateDebut()));
				if (diff != 0L) {
					diff -= j.getDebut().getTime();
				}
			}
			String duree = null;
			if (diff < NB_MILLIS_DS_H) {
				final SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
				duree = formatter.format(diff);
			} else if (diff < NB_MILLIS_DS_JOUR) {
				final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
				duree = formatter.format(diff);
			} else {
				final SimpleDateFormat formatter = new SimpleDateFormat("DD 'j' HH:mm:ss");
				duree = formatter.format(diff);
			}
			aff.setDuree(duree);
			aff.setEtat(j.getJetCd());
			aff.setLancement(j.getJmoCd());
			aff.setServeur(j.getServeur());
			aff.setJoeId(j.getJoeId());
			aff.setLogs(j.getLogs());
			aff.setData(j.getData());
			aff.setRapport(formatRapport(j.getRapport()));
			affList.add(aff);

		}

		return affList;
	}

	/**
	 * Initialisation du critère
	 */
	private void createCriterion() {

		final TdcDetailCritere critere = new TdcDetailCritere();
		final Calendar ajd = Calendar.getInstance();
		ajd.setLenient(true);
		critere.setDateFin(ajd.getTime());
		ajd.add(Calendar.DAY_OF_YEAR, -NB_JOURS_RECHERCHE_DEFAUT);
		critere.setDateDebut(ajd.getTime());
		critereRef.publish(critere);

	}

	public String filtrer() {

		final TdcDetailCritere critere = critereRef.readDto();

		detailsRef.publish(convert(tdcServices.getListeJobexecutionFiltree(jobdefIdRef.get(), critere).get()));

		return NONE;
	}

	public void telechargerLog() {
		final UiList<TdcDetailAffichage> listJobex = detailsRef.getUiList();
		final TdcDetailAffichage tdcDet = listJobex.get(getRowIndex()).validate(new UiObjectValidator<TdcDetailAffichage>(), getUiMessageStack());
		final Long logId = Long.parseLong(tdcServices.getJobexecution(tdcDet.getJoeId()).getLogs());

		final KFile kLog = fileServices.getFileContent(logId);
		final KFileResponseBuilder responseBuilder = createKFileResponseBuilder();
		responseBuilder.send(kLog);
	}

	public void telechargerData() {
		final UiList<TdcDetailAffichage> listJobex = detailsRef.getUiList();
		final TdcDetailAffichage tdcDet = listJobex.get(getRowIndex()).validate(new UiObjectValidator<TdcDetailAffichage>(), getUiMessageStack());
		final Long dataId = Long.parseLong(tdcServices.getJobexecution(tdcDet.getJoeId()).getData());

		final KFile kData = fileServices.getFileContent(dataId);
		final KFileResponseBuilder responseBuilder = createKFileResponseBuilder();
		responseBuilder.send(kData);
	}

	private static String formatRapport(final String rapport) {
		if (rapport != null) {
			final String rappNettoye = rapport.replace("{", "").replace("}", "").replace("\"", "");
			final String[] listeEntrees = rappNettoye.split(",");
			final StringBuilder rappFormat = new StringBuilder();

			for (final String s : listeEntrees) {
				final String[] sSplit = s.split(":");
				if (sSplit.length == 2 && StringUtils.isNotBlank(sSplit[1]) && !"null".equals(sSplit[1])) {
					rappFormat.append(s);
					rappFormat.append("<br/>");
				}
			}
			return rappFormat.toString();

		}
		return null;
	}

	@Override
	public String getPageName() {
		return "Liste des exécutions";
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
