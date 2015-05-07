package snowblood.gen.dao;

import io.vertigo.core.Home;
import io.vertigo.dynamo.impl.persistence.util.DAOBroker;
import io.vertigo.dynamo.persistence.PersistenceManager;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.dynamo.task.metamodel.TaskDefinition;
import io.vertigo.dynamo.task.model.Task;
import io.vertigo.dynamo.task.model.TaskBuilder;
import io.vertigo.dynamo.task.model.TaskResult;
import io.vertigo.lang.Option;

import javax.inject.Inject;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * JobexecutionDAO
 */
public final class JobexecutionDAO extends DAOBroker<snowblood.gen.domain.Jobexecution, java.lang.Long> {
	/** Liste des taches. */
	private static enum Tasks {
		/** Tache TK_GET_LIST_JOBEXECUTION_JOURNAUX_A_SUPPRIMER */
		TK_GET_LIST_JOBEXECUTION_JOURNAUX_A_SUPPRIMER,
		/** Tache TK_GET_JOBEXECUTION_PAR_JOBDEFINITION_SUPERVISION */
		TK_GET_JOBEXECUTION_PAR_JOBDEFINITION_SUPERVISION,
		/** Tache TK_GET_LAST_JOB_EXECUTION */
		TK_GET_LAST_JOB_EXECUTION,
		/** Tache TK_GET_LISTE_JOBEXECUTION_FILTREE */
		TK_GET_LISTE_JOBEXECUTION_FILTREE,
		/** Tache TK_GET_JOBEXECUTION_EN_COURS */
		TK_GET_JOBEXECUTION_EN_COURS,
	}

	/** Constante de paramètre de la tache DTC_JOBEXECUTION. */
	private static final String ATTR_OUT_TK_GET_LIST_JOBEXECUTION_JOURNAUX_A_SUPPRIMER_DTC_JOBEXECUTION = "DTC_JOBEXECUTION";

	/** Constante de paramètre de la tache JOD_ID. */
	private static final String ATTR_IN_TK_GET_JOBEXECUTION_PAR_JOBDEFINITION_SUPERVISION_JOD_ID = "JOD_ID";

	/** Constante de paramètre de la tache DTC_JOBEXECUTION. */
	private static final String ATTR_OUT_TK_GET_JOBEXECUTION_PAR_JOBDEFINITION_SUPERVISION_DTC_JOBEXECUTION = "DTC_JOBEXECUTION";

	/** Constante de paramètre de la tache JOD_ID. */
	private static final String ATTR_IN_TK_GET_LAST_JOB_EXECUTION_JOD_ID = "JOD_ID";

	/** Constante de paramètre de la tache DT_JOBEXECUTION. */
	private static final String ATTR_OUT_TK_GET_LAST_JOB_EXECUTION_DT_JOBEXECUTION = "DT_JOBEXECUTION";

	/** Constante de paramètre de la tache JOD_ID. */
	private static final String ATTR_IN_TK_GET_LISTE_JOBEXECUTION_FILTREE_JOD_ID = "JOD_ID";

	/** Constante de paramètre de la tache CRITERE. */
	private static final String ATTR_IN_TK_GET_LISTE_JOBEXECUTION_FILTREE_CRITERE = "CRITERE";

	/** Constante de paramètre de la tache DTC_JOBEXECUTION. */
	private static final String ATTR_OUT_TK_GET_LISTE_JOBEXECUTION_FILTREE_DTC_JOBEXECUTION = "DTC_JOBEXECUTION";

	/** Constante de paramètre de la tache JOD_ID. */
	private static final String ATTR_IN_TK_GET_JOBEXECUTION_EN_COURS_JOD_ID = "JOD_ID";

	/** Constante de paramètre de la tache DTC_JOBEXECUTION. */
	private static final String ATTR_OUT_TK_GET_JOBEXECUTION_EN_COURS_DTC_JOBEXECUTION = "DTC_JOBEXECUTION";

	 
	/**
	 * Contructeur.
	 * @param persistenceManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public JobexecutionDAO(final PersistenceManager persistenceManager, final TaskManager taskManager) {
		super(snowblood.gen.domain.Jobexecution.class, persistenceManager, taskManager);
	}

	/**
	 * Création d'une tache.
	 * @param task Type de la tache
	 * @return Builder de la tache
	 */
	private TaskBuilder createTaskBuilder(final Tasks task) {
		final TaskDefinition taskDefinition = Home.getDefinitionSpace().resolve(task.toString(), TaskDefinition.class);
		return new TaskBuilder(taskDefinition);
	}

	/**
	 * Execute la tache TK_GET_LIST_JOBEXECUTION_JOURNAUX_A_SUPPRIMER.
	 * @return fr.justice.isis.toolbox.kitvertigo.dynamo.domain.model.DtList<fr.justice.isis.domain.tourdecontrole.Jobexecution> dtcJobexecution
	*/
	public io.vertigo.dynamo.domain.model.DtList<snowblood.gen.domain.Jobexecution> getListJobexecutionJournauxASupprimer() {
		final Task task = createTaskBuilder(Tasks.TK_GET_LIST_JOBEXECUTION_JOURNAUX_A_SUPPRIMER)
				.build();
		final TaskResult taskResult = getTaskManager().execute(task);
		return taskResult.getValue(ATTR_OUT_TK_GET_LIST_JOBEXECUTION_JOURNAUX_A_SUPPRIMER_DTC_JOBEXECUTION);
	}

	/**
	 * Execute la tache TK_GET_JOBEXECUTION_PAR_JOBDEFINITION_SUPERVISION.
	 * @param jodId Long 
	 * @return fr.justice.isis.toolbox.kitvertigo.dynamo.domain.model.DtList<fr.justice.isis.domain.tourdecontrole.Jobexecution> dtcJobexecution
	*/
	public io.vertigo.dynamo.domain.model.DtList<snowblood.gen.domain.Jobexecution> getJobexecutionParJobdefinitionSupervision(final Long jodId) {
		final Task task = createTaskBuilder(Tasks.TK_GET_JOBEXECUTION_PAR_JOBDEFINITION_SUPERVISION)
				.withValue(ATTR_IN_TK_GET_JOBEXECUTION_PAR_JOBDEFINITION_SUPERVISION_JOD_ID, jodId)
				.build();
		final TaskResult taskResult = getTaskManager().execute(task);
		return taskResult.getValue(ATTR_OUT_TK_GET_JOBEXECUTION_PAR_JOBDEFINITION_SUPERVISION_DTC_JOBEXECUTION);
	}

	/**
	 * Execute la tache TK_GET_LAST_JOB_EXECUTION.
	 * @param jodId Long 
	 * @return Option de fr.justice.isis.domain.tourdecontrole.Jobexecution dtJobexecution
	*/
	public Option<snowblood.gen.domain.Jobexecution> getLastJobExecution(final Long jodId) {
		final Task task = createTaskBuilder(Tasks.TK_GET_LAST_JOB_EXECUTION)
				.withValue(ATTR_IN_TK_GET_LAST_JOB_EXECUTION_JOD_ID, jodId)
				.build();
		final TaskResult taskResult = getTaskManager().execute(task);
		return Option.option(taskResult.<snowblood.gen.domain.Jobexecution> getValue(ATTR_OUT_TK_GET_LAST_JOB_EXECUTION_DT_JOBEXECUTION));
	}

	/**
	 * Execute la tache TK_GET_LISTE_JOBEXECUTION_FILTREE.
	 * @param jodId Long 
	 * @param critere fr.justice.isis.services.tourdecontrole.TdcDetailCritere 
	 * @return Option de fr.justice.isis.toolbox.kitvertigo.dynamo.domain.model.DtList<fr.justice.isis.domain.tourdecontrole.Jobexecution> dtcJobexecution
	*/
	public Option<io.vertigo.dynamo.domain.model.DtList<snowblood.gen.domain.Jobexecution>> getListeJobexecutionFiltree(final Long jodId, final snowblood.gen.services.TdcDetailCritere critere) {
		final Task task = createTaskBuilder(Tasks.TK_GET_LISTE_JOBEXECUTION_FILTREE)
				.withValue(ATTR_IN_TK_GET_LISTE_JOBEXECUTION_FILTREE_JOD_ID, jodId)
				.withValue(ATTR_IN_TK_GET_LISTE_JOBEXECUTION_FILTREE_CRITERE, critere)
				.build();
		final TaskResult taskResult = getTaskManager().execute(task);
		return Option.option(taskResult.<io.vertigo.dynamo.domain.model.DtList<snowblood.gen.domain.Jobexecution>> getValue(ATTR_OUT_TK_GET_LISTE_JOBEXECUTION_FILTREE_DTC_JOBEXECUTION));
	}

	/**
	 * Execute la tache TK_GET_JOBEXECUTION_EN_COURS.
	 * @param jodId Long 
	 * @return Option de fr.justice.isis.toolbox.kitvertigo.dynamo.domain.model.DtList<fr.justice.isis.domain.tourdecontrole.Jobexecution> dtcJobexecution
	*/
	public Option<io.vertigo.dynamo.domain.model.DtList<snowblood.gen.domain.Jobexecution>> getJobexecutionEnCours(final Long jodId) {
		final Task task = createTaskBuilder(Tasks.TK_GET_JOBEXECUTION_EN_COURS)
				.withValue(ATTR_IN_TK_GET_JOBEXECUTION_EN_COURS_JOD_ID, jodId)
				.build();
		final TaskResult taskResult = getTaskManager().execute(task);
		return Option.option(taskResult.<io.vertigo.dynamo.domain.model.DtList<snowblood.gen.domain.Jobexecution>> getValue(ATTR_OUT_TK_GET_JOBEXECUTION_EN_COURS_DTC_JOBEXECUTION));
	}

}
