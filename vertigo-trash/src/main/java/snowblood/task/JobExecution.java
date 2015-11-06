package snowblood.task;

import io.vertigo.app.Home;
import io.vertigo.lang.Assertion;
import io.vertigo.util.StringUtil;

import java.net.InetAddress;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import snowblood.gen.domain.ActivityStatus;
import snowblood.gen.domain.Jobdefinition;
import snowblood.gen.domain.Jobexecution;
import snowblood.services.job.JobServices;
import snowblood.util.DateHelper;
import snowblood.util.IsisUtil;

/**
 * L'état d'une exécution d'un job en cours ou terminée.
 * Il s'agit du thread dans lequel s'exécute le job.
 *
 * @author bgenevaux
 */
public class JobExecution extends Thread {

	private static final Logger LOG = Logger.getLogger(JobExecution.class);

	/** Identifiant du JobExecution. */
	public static final String PARAM_JOB_EXECUTION_ID = "ID_EXECUTION";

	//	public static final String PARAM_FORCE_COMPLET = "FORCE_COMPLET";

	//	public static final String PARAM_A_BLANC = "A_BLANC";

	private final JobServices services;

	private Jobexecution execution;

	private final Jobdefinition definition;

	private final Map<String, String> parametres;

	private Map<String, String> rapport;

	/**
	 * Création du thread d'exécution du job.
	 *
	 * @param definition définition du job.
	 */
	public JobExecution(final Jobdefinition definition, final Map<String, String> params) {
		Assertion.checkNotNull(definition);
		/*---------------------*/
		services = Home.getApp().getComponentSpace().resolve(JobServices.class);
		this.definition = definition;
		execution = new Jobexecution();
		parametres = StringUtil.isEmpty(definition.getParametresEtendus()) ? new HashMap<String, String>() : IsisUtil.deserializeJson(definition.getParametresEtendus());
		rapport = new HashMap<>();
		execution.setJodId(definition.getJodId());
		// FIXME : Node name, IP/Hostname is not reliable (many NIC, many dns aliases)
		// NetworkUtils.getLocalAddress().getHostName()
		try {
			execution.setServeur(InetAddress.getLocalHost().getHostName());
		} catch (final Exception e) {
			// LOL : FIXME
		}
		services.saveJobexecution(execution);
		final Long id = execution.getJoeId();
		parametres.put(PARAM_JOB_EXECUTION_ID, id.toString());
		if (params != null) {
			parametres.putAll(params);
		}

	}

	private static String serializeStringMap(final Map<String, String> map) {
		if (map != null && !map.isEmpty()) {
			final StringBuilder serialized = new StringBuilder();
			serialized.append("{\n");
			for (final Entry<String, String> param : map.entrySet()) {
				serialized.append("\t\"" + param.getKey() + "\":\"" + param.getValue() + "\",\n");
			}
			serialized.deleteCharAt(serialized.length() - 2);
			serialized.append("}");
			return serialized.toString();
		}
		// TODO : faire propre
		return "";
	}

	/**
	 * Exécute le job. Les dates de début et de fin sont GMT.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void run() {

		final Calendar cal = Calendar.getInstance();
		execution.setDebut(DateHelper.convertDateToGMT(cal.getTime(), cal.getTimeZone().getID()));

		execution.setStatus(ActivityStatus.RUNNING);
		services.saveJobexecution(execution);

		JobExecutor executor = null;

		try {
			final Class<? extends JobExecutor> classeImple = (Class<? extends JobExecutor>) Class.forName(definition.getImplementation());
			executor = classeImple.newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			execution.setStatus(ActivityStatus.FAILURE);
			rapport.put("ERROR", "Erreur à l'instanciation du JobExecutor :" + e.getStackTrace().toString());
		}

		Assertion.checkNotNull(executor);

		try {
			executor.run(parametres);
			execution = services.refreshJobexecution(execution);

		} catch (final Exception e) {
			execution = services.refreshJobexecution(execution);
			execution.setStatus(ActivityStatus.FAILURE);
			LOG.warn("Erreur d'execution du job", e);
			throw e;
		} finally {

			try {
				final Calendar calFin = Calendar.getInstance();
				execution.setFin(DateHelper.convertDateToGMT(calFin.getTime(), calFin.getTimeZone().getID()));
				rapport = executor.getRapport();
				if (execution.getRapport() == null) {
					execution.setRapport(serializeStringMap(rapport));
				}

			} catch (final Exception e) {
				throw e;
			} finally {
				services.saveJobexecution(execution);
			}
		}
	}

	/**
	 * Accesseur sur la modélisation en base de l'état de l'exécution courante du job.
	 *
	 * @return la modélisation en base de l'état de l'exécution courante du job.
	 */
	public Jobexecution getExecution() {
		return execution;
	}

	/**
	 * Accesseur sur les paramètres de retour (rapport) du job.
	 *
	 * @param elementName le nom du paramètre.
	 * @return le paramètre.
	 */
	public String getRapport(final String elementName) {
		return rapport.get(elementName);
	}
}
