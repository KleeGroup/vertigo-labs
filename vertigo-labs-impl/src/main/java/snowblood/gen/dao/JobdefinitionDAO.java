package snowblood.gen.dao;

import io.vertigo.dynamo.impl.store.util.DAOBroker;
import io.vertigo.dynamo.persistence.StoreManager;
import io.vertigo.dynamo.task.TaskManager;

import javax.inject.Inject;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * JobdefinitionDAO
 */
public final class JobdefinitionDAO extends DAOBroker<snowblood.gen.domain.Jobdefinition, java.lang.Long> {
	 
	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public JobdefinitionDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(snowblood.gen.domain.Jobdefinition.class, storeManager, taskManager);
	}
	
}
