package snowblood.services.job;

import io.vertigo.core.Home;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.file.FileManager;
import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.persona.security.VSecurityManager;

import java.io.File;

import javax.inject.Inject;

import snowblood.gen.dao.JobdefinitionDAO;
import snowblood.gen.dao.JobexecutionDAO;
import snowblood.gen.domain.Jobdefinition;
import snowblood.gen.domain.Jobexecution;
import snowblood.gen.services.TourdecontrolePAO;
import snowblood.services.file.FileServices;

@Transactional
public class JobServicesImpl implements JobServices {

	@Inject
	private TourdecontrolePAO tourdecontrolePAO;

	@Inject
	private JobexecutionDAO jobexecutionDAO;

	@Inject
	private JobdefinitionDAO jobdefinitionDAO;

	@Inject
	private FileManager fileManager;

	@Inject
	private FileServices fileServices;

	//	private final static String[] REGISTERED_JOB_CODE = {//
	//	CodeJobDefinition.MAINTENANCE_TDC.getCode(),//
	//	};

	@Override
	public void registerJobsAndSchedule() {
		//		for (final String code : REGISTERED_JOB_CODE) {
		//			final DtList<Jobdefinition> tmpList = jobdefinitionDAO.getListByDtField(JobdefinitionFields.CODE.name(), code, 1);
		//			if (!tmpList.isEmpty()) {
		//				final Jobdefinition definition = tmpList.get(0);
		//				jobManager.registerJob(definition);
		//				if (!ConfigHelper.isConnexionDevMode() && definition.getActivation() && definition.getFrequence() != null) {
		//					try {
		//						jobManager.scheduleCron(definition.getCode(), definition.getFrequence());
		//					} catch (final RuntimeException e) {
		//						LOG.error("Erreur pendant l'initiatlisation des jobs", e);
		//					}
		//				} else {
		//					jobManager.stopSchedule(definition.getCode());
		//				}
		//			}
		//		}
	}

	@Override
	public void saveJobexecution(final Jobexecution execution) {
		jobexecutionDAO.save(execution);
	}

	@Override
	public Jobexecution refreshJobexecution(final Jobexecution execution) {
		return jobexecutionDAO.get(execution.getJoeId());
	}

	@Override
	public Long getNextJobexecutionId() {
		return tourdecontrolePAO.getNextJobexecutionId();
	}

	@Override
	public Long getJobdefinitionIdByCode(final String code) {
		final DtList<Jobdefinition> listJobdef = jobdefinitionDAO.getListByDtField("CODE", code, 2);
		if (listJobdef.isEmpty()) {
			throw new RuntimeException("Le job " + code + " est introuvable parmi les Jobdefinitions");
		}
		if (listJobdef.size() > 1) {
			throw new RuntimeException("Plusieurs jobs correspondent au code " + code);
		}

		return listJobdef.get(0).getJodId();
	}

	@Override
	public Jobexecution sauvegarderLogs(final Jobexecution jobex, final String path) {
		final File log = new File(path);
		final VFile kLog = fileManager.createFile(log.getName(), "application/x-gzip", log);
		final Long idLog = fileServices.createFile(kLog);
		jobex.setLogs(idLog.toString());
		jobexecutionDAO.save(jobex);

		return jobex;
	}

	@Override
	public Jobexecution sauvegarderData(final Jobexecution jobex, final String path) {
		final File data = new File(path);
		final VFile kData = fileManager.createFile(data.getName(), "application/x-gzip", data);
		final Long idData = fileServices.createFile(kData);
		jobex.setData(idData.toString());
		jobexecutionDAO.save(jobex);

		return jobex;

	}

	@Override
	public void initJobSession() {
		//		final Utilisateur systemUser = utilisateurDAO.getListByDtField(UtilisateurFields.LOGIN.name(), "system", 1).get(0);
		//		final DtList<Profils> profilsAdmin = profilsDAO.getListByDtField(ProfilsFields.CODE.name(), "Admin", 1);
		//		final JobUserSession session = new JobUserSession();
		//		session.setUtilisateur(systemUser);
		//		session.registerProfils(profilsAdmin);
		//		Home.getComponentSpace().resolve(VSecurityManager.class).startCurrentUserSession(session);
	}

	@Override
	public void stopJobSession() {
		Home.getComponentSpace().resolve(VSecurityManager.class).stopCurrentUserSession();
	}

}
