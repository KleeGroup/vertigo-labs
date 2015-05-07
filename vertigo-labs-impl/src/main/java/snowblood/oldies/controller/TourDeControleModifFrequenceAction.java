package snowblood.oldies.controller;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.struts2.core.ContextForm;
import io.vertigo.struts2.core.ContextList;
import io.vertigo.struts2.core.ContextRef;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import fr.justice.isis.controller.AbstractIsisActionSupport;
import fr.justice.isis.controller.navigation.Menu;
import fr.justice.isis.controller.navigation.NavigationItem;
import fr.justice.isis.domain.tourdecontrole.Jobdefinition;
import fr.justice.isis.services.tourdecontrole.FreqUtils;
import fr.justice.isis.services.tourdecontrole.TdcConstructionFrequence;
import fr.justice.isis.services.tourdecontrole.TdcListe;
import fr.justice.isis.services.tourdecontrole.TourDeControleServices;

/**
 * Controller de la popin d'édition de la fréquence d'exécution d'un job.
 *
 * @author fdangelotillon
 */
public class TourDeControleModifFrequenceAction extends AbstractIsisActionSupport {

	/**
	 *
	 */
	private static final long serialVersionUID = 7683890220393254544L;

	private final ContextRef<Long> jodIdRef = new ContextRef<>("jodId", Long.class, this);
	private final ContextRef<Jobdefinition> jobdefRef = new ContextRef<>("jobdef", Jobdefinition.class, this);
	private final ContextForm<TdcConstructionFrequence> freqRef = new ContextForm<>("freq", this);
	private final ContextList<TdcListe> listTypeFreqRef = new ContextList<>("typeFreq", this);
	private final ContextList<TdcListe> listPeriodeUnitesRef = new ContextList<>("periodeUnites", this);

	@Inject
	private TourDeControleServices tdcServices;

	protected void initContext(@Named("jodId") final String jodId) {

		jodIdRef.set(Long.valueOf(jodId));
		final TdcConstructionFrequence fr = new TdcConstructionFrequence();
		initListTypeFreq();
		initListePeriodeUnites();

		jobdefRef.set(tdcServices.getJobdefinition(Long.valueOf(jodId)));
		final Jobdefinition jobdef = tdcServices.getJobdefinition(Long.valueOf(jodId));
		final String freq = jobdef.getFrequence();
		final String typeFreq = FreqUtils.cronOuPeriode(freq);
		if (FreqUtils.IS_CRON.equals(typeFreq)) {
			fr.setTypeFrequence("CRO");
			fr.setChaineCron(freq);
		} else if (FreqUtils.IS_PERIOD.equals(typeFreq)) {
			fr.setTypeFrequence("PER");
			fr.setValeurPeriode(Integer.valueOf(freq));
		}

		freqRef.publish(fr);
	}

	private void initListTypeFreq() {
		final TdcListe p = new TdcListe();
		p.setId("PER");
		p.setLibelle("Période");

		final TdcListe c = new TdcListe();
		c.setId("CRO");
		c.setLibelle("Chaîne cron");

		final TdcListe m = new TdcListe();
		m.setId("MAN");
		m.setLibelle("Définition manuelle de la fréquence");

		final DtList<TdcListe> liste = new DtList<>(TdcListe.class);
		liste.add(p);
		liste.add(c);
		liste.add(m);

		listTypeFreqRef.publish(liste);
	}

	private void initListePeriodeUnites() {
		final TdcListe j = new TdcListe();
		j.setId("JOU");
		j.setLibelle("jours");

		final TdcListe h = new TdcListe();
		h.setId("HEU");
		h.setLibelle("heures");

		final TdcListe m = new TdcListe();
		m.setId("MIN");
		m.setLibelle("minutes");

		final TdcListe s = new TdcListe();
		s.setId("SEC");
		s.setLibelle("secondes");

		final DtList<TdcListe> liste = new DtList<>(TdcListe.class);
		liste.add(j);
		liste.add(h);
		liste.add(m);
		liste.add(s);

		listPeriodeUnitesRef.publish(liste);
	}

	@Override
	public String getPageName() {
		return "Détermination de la fréquence d'exécution";
	}

	@Override
	public List<NavigationItem> getPageParents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Menu getActiveMenu() {
		return Menu.TOURDECONTROLE;
	}

	public String annuler() {
		return "annuler";
	}

}
