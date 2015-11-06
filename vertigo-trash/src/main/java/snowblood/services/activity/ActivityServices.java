package snowblood.services.activity;

import io.vertigo.dynamo.domain.model.DtList;
import snowblood.gen.domain.Jobdefinition;
import snowblood.gen.domain.Jobexecution;
import snowblood.gen.services.TdcDetailCritere;

/**
 * Public service interface for activity management.
 * 
 * Different method groups are defined :
 * <ul>
 * <li>Definition CRUD : save, load, list, remove</li> 
 * <li>Execution CRUD  : save, load, list, remove</li> 
 * <li>Execution control : run, notify, addData, addLogs</li> 
 * <ul>
 *
 * @author Frankie Limont
 */
public interface ActivityServices {

	// ************************************************************************
	// DEFINITION CRUD
	// ************************************************************************
	/**
	 * Save (create or update) an Activity execution instance.
	 *
	 * @param definition ActivityExecution (mutated, ID is set if created).
	 */
	void saveActivityDefinition(Jobdefinition definition);

	/**
	 * Load an ActivityDefinition instance.
	 *
	 * @param definitionId ActivityDefinition ID.
	 * @return execution instance.
	 */
	Jobdefinition loadActivityDefinition(Long definitionId);

	/**
	 * List Activity definition matching criteria.
	 *
	 * @param criteria Activity definition filter.
	 * @return definition list.
	 */
	DtList<Jobdefinition> listActivitydefinition(TdcDetailCritere criteria);

	/**
	 * Delete an Activity definition instance.
	 * Cannot delete if referenced by execution.
	 *
	 * @param definitionId ActivityExecution ID.
	 */
	void removeActivityDefinition(Long definitionId);

	// ************************************************************************
	// EXECUTION CRUD
	// ************************************************************************

	/**
	 * Save (create or update) an Activity execution instance.
	 *
	 * @param execution ActivityExecution (mutated, ID is set if created).
	 */
	void saveActivityExecution(Jobexecution execution);

	/**
	 * Load an Activity execution instance.
	 *
	 * @param executionId ActivityExecution ID.
	 * @return execution instance.
	 */
	Jobexecution loadActivityExecution(Long executionId);

	/**
	 * List Activity execution instance matching criteria.
	 *
	 * @return execution instance list.
	 */
	DtList<Jobexecution> listActivityExecution(TdcDetailCritere criteria);

	/**
	 * Delete an Activity execution instance.
	 *
	 * @param executionId ActivityExecution ID.
	 */
	void removeActivityExecution(Long executionId);

	// ************************************************************************
	// EXECUTION control
	// ************************************************************************

	/**
	 * Activity launch.
	 * 
	 * Creates a Jobexecution instance based on a registered Jobdefinition and
	 * an execution context. The launching context is recorded in appropriate
	 * execution fields and is expected to be quite short (under 4K bytes).
	 * If extensive context is required, it is advised to create a separate
	 * data object (table, file...) and sends only the reference through this
	 * parameter.
	 *
	 * @param contextJson activity launch context in a JSON formated string
	 * @return Jobexecution instance.
	 */
	Jobexecution run(Long activityDefinitionId, String contextJson);

	/**
	 * Sends a notification to a job.
	 * 
	 * This notification mechanism mimics the Linux kill command. The task
	 * implementation class may or may not implements adequate notification
	 * listener. Success is not guaranteed.
	 *
	 * @param jobex le jobexecution.
	 * @param action message to send.
	 * @return le jobexecution mis à jour.
	 */
	Jobexecution notify(Jobexecution jobex, String action);

	/**
	 * Attach a log file to an execution.
	 *
	 * @param jobex Jobexecution.
	 * @param path Path to data file.
	 * @return updated Jobexecution.
	 */
	Jobexecution addLogs(Jobexecution jobex, String path);

	/**
	 * Attach a data file to an execution.
	 *
	 * @param jobex Jobexecution.
	 * @param path Path to data file.
	 * @return updated Jobexecution.
	 */
	Jobexecution addData(Jobexecution jobex, String path);
}
