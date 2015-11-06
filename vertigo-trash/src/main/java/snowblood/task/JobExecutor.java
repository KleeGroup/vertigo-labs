package snowblood.task;

import java.util.Map;

/**
 * General interface to be implemented by tasks.
 *
 * @author bgenevaux
 */
public interface JobExecutor {

	/**
	 * Task execution method
	 *
	 * @param params extended parameters.
	 */
	void run(final Map<String, String> params);

	/**
	 * Access to execution report.
	 * 	 *
	 * @return execution report.
	 */
	Map<String, String> getRapport();
}
