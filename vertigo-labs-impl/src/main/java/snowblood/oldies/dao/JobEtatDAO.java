package snowblood.oldies.dao;

import io.vertigo.dynamo.impl.persistence.util.DAOBroker;
import io.vertigo.dynamo.persistence.PersistenceManager;
import io.vertigo.dynamo.task.TaskManager;

import javax.inject.Inject;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * JobEtatDAO
 */
public final class JobEtatDAO extends DAOBroker<snowblood.gen.domain.tourdecontrole.JobEtat, java.lang.String> {
	 
	/**
	 * Contructeur.
	 * @param persistenceManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public JobEtatDAO(final PersistenceManager persistenceManager, final TaskManager taskManager) {
		super(snowblood.gen.domain.tourdecontrole.JobEtat.class, persistenceManager, taskManager);
	}
}
