package snowblood.services.tourdecontrole;

import io.vertigo.core.Home;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.lang.Option;
import io.vertigo.tempo.job.metamodel.JobDefinition;
import io.vertigo.tempo.scheduler.SchedulerManager;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import snowblood.gen.dao.JobdefinitionDAO;
import snowblood.gen.dao.JobexecutionDAO;
import snowblood.gen.domain.Jobdefinition;
import snowblood.gen.domain.Jobexecution;
import snowblood.gen.services.TdcDetailCritere;
import snowblood.services.file.FileServices;
import snowblood.task.JobMaintenanceTdc;

/**
 * Implémentation des services de la tour de contrôle.
 *
 * @author fdangelotillon
 */
@Transactional
public class TourDeControleServicesImpl implements TourDeControleServices {
	@Inject
	private JobexecutionDAO jobexecutionDAO;
	@Inject
	private JobdefinitionDAO jobdefinitionDAO;

	//@Inject
	//private DossierPfeDAO dossierPfeDAO;

	@Inject
	private SchedulerManager schedulerManager;

	@Inject
	private FileServices fileServices;

	@Override
	public DtList<Jobexecution> getJobexecutionParJobdefinition(final Long jodId) {
		return jobexecutionDAO.getJobexecutionParJobdefinitionSupervision(jodId);
	}

	@Override
	public Jobdefinition getJobdefinition(final Long jodId) {
		return jobdefinitionDAO.get(jodId);
	}

	@Override
	public void saveJobdefinition(final Jobdefinition jobdef) {
		// if (!ConfigHelper.isConnexionDevMode() && jobdef.getActivation() && jobdef.getFrequence() != null) {
		//Fixme
		//Fixme
		//Fixme
		//Fixme
		//Fixme

		schedulerManager.scheduleEverySecondInterval(Home.getDefinitionSpace().resolve(jobdef.getCode(), JobDefinition.class), 10);

		// } else {
		//	jobManager.stopSchedule(jobdef.getCode());
		// }
		jobdefinitionDAO.save(jobdef);
	}

	@Override
	public Option<DtList<Jobexecution>> getListeJobexecutionFiltree(final Long jodId, final TdcDetailCritere critere) {
		return jobexecutionDAO.getListeJobexecutionFiltree(jodId, critere);
	}

	@Override
	public Option<DtList<Jobexecution>> getJobexecutionEncours(final Long jodId) {
		return jobexecutionDAO.getJobexecutionEnCours(jodId);
	}

	//	@Override
	//	public DtList<DossierPfe> getDossierPFEparJodId(final Long jodId) {
	//		return dossierPfeDAO.getDossierPfeParJobdef(jodId);
	//	}

	@Override
	public Jobexecution getJobexecution(final Long exeId) {
		return jobexecutionDAO.get(exeId);
	}

	@Override
	public void saveJobexecution(final Jobexecution jobex) {
		jobexecutionDAO.save(jobex);

	}

	@Override
	public Jobexecution getLastJobexecutionParJobdefinition(final Long jodId) {
		final Option<Jobexecution> jobex = jobexecutionDAO.getLastJobExecution(jodId);
		return jobex.isDefined() ? jobex.get() : null;
	}

	@Override
	public void batchMaintenance() {
		final Logger logger = Logger.getLogger(JobMaintenanceTdc.class);
		final DtList<Jobexecution> listJobexeJournauxDelete = jobexecutionDAO.getListJobexecutionJournauxASupprimer();
		for (final Jobexecution jobexe : listJobexeJournauxDelete) {
			if (jobexe.getLogs() != null) {
				try {
					fileServices.deleteFile(Long.parseLong(jobexe.getLogs()));
					logger.info("Le document " + fileServices.getFileContent(Long.parseLong(jobexe.getLogs())).getFileName()
							+ " a été supprimé définitivement de la corbeille.");
				} catch (final RuntimeException e) {
					logger.error("Le document " + fileServices.getFileContent(Long.parseLong(jobexe.getLogs())).getFileName()
							+ " n'a pas pu être supprimé définitivement de la corbeille.");
				}
			}
			if (jobexe.getData() != null) {
				try {
					fileServices.deleteFile(Long.parseLong(jobexe.getData()));
					logger.info("Le document " + fileServices.getFileContent(Long.parseLong(jobexe.getLogs())).getFileName()
							+ " a été supprimé définitivement de la corbeille.");
				} catch (final RuntimeException e) {
					logger.error("Le document " + fileServices.getFileContent(Long.parseLong(jobexe.getData())).getFileName()
							+ " n'a pas pu être supprimé définitivement de la corbeille.");
				}
			}
		}
	}
}
