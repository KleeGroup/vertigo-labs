package snowblood.task;

import snowblood.services.tourdecontrole.TourDeControleServices;
import io.vertigo.core.Home;

/**
 * Control tower maintenance task :
 *     - log and data files purge
 *     - execution entry purge
 *
 * @author flimont
 */
public class JobMaintenanceTdc extends JobCalculBase {

	@Override
	protected void launchBatch() {
		Home.getComponentSpace().resolve(TourDeControleServices.class).batchMaintenance();
	}

}
