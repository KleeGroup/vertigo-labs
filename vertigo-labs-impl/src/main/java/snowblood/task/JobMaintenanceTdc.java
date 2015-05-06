package snowblood.task;

import snowblood.services.tourdecontrole.TourDeControleServices;
import io.vertigo.core.Home;

/**
 * Job de maintenance de la tour de controle.
 *
 * @author bgenevaux
 */
public class JobMaintenanceTdc extends JobCalculBase {

	@Override
	protected void launchBatch() {
		Home.getComponentSpace().resolve(TourDeControleServices.class).batchMaintenance();
	}

}
