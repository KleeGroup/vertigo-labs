package snowblood.gen.services.tourdecontrole;

import io.vertigo.core.Home;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.dynamo.task.metamodel.TaskDefinition;
import io.vertigo.dynamo.task.model.Task;
import io.vertigo.dynamo.task.model.TaskBuilder;
import io.vertigo.dynamo.task.model.TaskResult;
import io.vertigo.lang.Assertion;

import javax.inject.Inject;

/**
 * PAO : Accès aux objects du package. 
 * TourdecontrolePAO
 */
public final class TourdecontrolePAO {
	/** Liste des taches. */
	private static enum Tasks {
		/** Tache TK_GET_NEXT_JOBEXECUTION_ID */
		TK_GET_NEXT_JOBEXECUTION_ID,
	}

	/** Constante de paramètre de la tache DTC_JOE_ID. */
	private static final String ATTR_OUT_TK_GET_NEXT_JOBEXECUTION_ID_DTC_JOE_ID = "DTC_JOE_ID";

	private final TaskManager taskManager;

	/**
	 * Constructeur.
	 * @param taskManager Manager des Task
	 */
	@Inject
	public TourdecontrolePAO(final TaskManager taskManager) {
		Assertion.checkNotNull(taskManager);
		//---------------------------------------------------------------------
		this.taskManager = taskManager;
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
	 * Execute la tache TK_GET_NEXT_JOBEXECUTION_ID.
	 * @return Long dtcJoeId
	*/
	public Long getNextJobexecutionId() {
		final Task task = createTaskBuilder(Tasks.TK_GET_NEXT_JOBEXECUTION_ID)
				.build();
		final TaskResult taskResult = getTaskManager().execute(task);
		return taskResult.getValue(ATTR_OUT_TK_GET_NEXT_JOBEXECUTION_ID_DTC_JOE_ID);
	}

    
    private TaskManager getTaskManager(){
    	return taskManager;
    } 
}
