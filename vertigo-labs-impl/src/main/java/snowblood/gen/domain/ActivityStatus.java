package snowblood.gen.domain;

/**
 * Task execution status.
 *
 * Defined status are :
 * <ul>
 * <li>[SUC]cess : the task successfully completed. Even in the event some
 *     data or computations were not able to complete as expected, no further
 *     attention is required, no corrective action is to be taken.
 * <li>[PAR]tial success : the task completed, however further attention is
 *     required. This status is preferably limited to business rule failure.
 * </li>
 * <li>[RUN]ning : the task is running.
 * </li>
 * <li>[FAI]lure : the task failed. This status is preferably limited to
 *     technical failure.
 * </li>
 * </ul>
 *
 * A business rule <b>must</b> define severity of each fail case. For example
 * an import task encountering a missing data file may end with :
 * <ul>
 * <li>Success : if the file is optional, a log file may contain information
 * that this file was missing, but no further action is required.
 * </li>
 * <li>Partial success : the file is mandatory, but the task avoids crashing.
 * Some data may even be processed and modified into database. This status
 * means some actions are to be taken, at least a finer log review to assess
 * the exact severity.
 * </li>
 * <li>Failure : the file is mandatory and the task crashed or wasn't able to
 * do anything. Hopefully no data were modified.
 * </li>
 * </ul>
 *
 * Of course, one could argue that a task must always be resilient, robust.
 * This can't be expected every time.
 *
 */

public enum ActivityStatus {
	SUCCESS("SUC", "Success"),
	PARTIAL("PAR", "Partial success"),
	RUNNING("RUN", "Running"),
	FAILURE("FAI", "Failure");

	private String code;
	private String label;

	private ActivityStatus(final String code, final String label) {
		this.code = code;
		this.label = label;
	}

	public String getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}

}
