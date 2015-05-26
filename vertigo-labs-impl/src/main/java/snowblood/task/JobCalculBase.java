package snowblood.task;

import io.vertigo.core.Home;
import io.vertigo.lang.VUserException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

import snowblood.gen.domain.Jobexecution;
import snowblood.services.job.JobServices;
import snowblood.services.tourdecontrole.TourDeControleServices;

/**
 * Classe abstraite pour les jobs basiques consistant en un simple appel de service isis.
 *
 * @author bgenevaux
 */
public abstract class JobCalculBase implements JobExecutor {

	protected abstract void launchBatch();

	@Override
	public final void run(final Map<String, String> params) {
		final TourDeControleServices tdcServices = Home.getComponentSpace().resolve(TourDeControleServices.class);
		final Jobexecution jobex = tdcServices.getJobexecution(Long.parseLong(params.get(JobExecution.PARAM_JOB_EXECUTION_ID)));
		try {
			final File logFile = File.createTempFile("log", ".log");
			final Logger logger = Logger.getLogger(getClass());
			final SimpleLayout layout = new SimpleLayout();
			final FileAppender appender = new FileAppender(layout, logFile.getAbsolutePath());
			logger.addAppender(appender);
			try {
				Home.getComponentSpace().resolve(JobServices.class).initJobSession();
				launchBatch();
				jobex.setJetCd(JobEtatEnum.SUCCES.getCode());
			} catch (final VUserException e) {
				logger.error(e.getMessage(), e);
				jobex.setJetCd(JobEtatEnum.SUCCES_PARTIEL.getCode());
			} catch (final Exception e) {
				logger.fatal(e.getMessage(), e);
				jobex.setJetCd(JobEtatEnum.ECHEC.getCode());
			} finally {
				Home.getComponentSpace().resolve(JobServices.class).stopJobSession();
				Home.getComponentSpace().resolve(JobServices.class).sauvegarderLogs(jobex, logFile.getAbsolutePath());
				tdcServices.saveJobexecution(jobex);
			}
		} catch (final IOException e) {
			// Echec de création du fichier log
			jobex.setJetCd(JobEtatEnum.ECHEC.getCode());
			tdcServices.saveJobexecution(jobex);
		}
	}

	@Override
	public Map<String, String> getRapport() {
		return null;
	}

}
