package snowblood.gen.domain;

/**
 * Task execution triggering method.
 *
 * A task may be triggered by multiple means depending on circumstance.
 * That's why each execution instance logs how it was created.
 *
 * Defined triggering methods are :
 * <ul>
 * <li>[S]cheduled : triggered by the application internal scheduler.</li>
 * <li>[E]xternal : triggered by an external event (ex: web-service call).</li>
 * <li>[M]anual : triggered directly by a user action.</li>
 * <li>[R]ule : triggered by a business rule.</li>
 * </ul>
 *
 * External scheduler are not considered as "scheduling" in this scheme.
 * If a scheduler calls on a regular basis a web-service triggering a task
 * we'd like to see it as an external call, as we don't have any mean of
 * action on the scheduler. This is a responsibility question regarding who's
 * in charge of scheduling. At least, avoid mixing internal and external
 * scheduling.
 *
 * Rule-based triggering means that a certain condition may be reached without
 * a user having the knowledge of it. For example, a task may be started when
 * a queue reaches a defined length. Most of times adding to the queue won't
 * start anything, sometimes it will. This sets a class for workflow/bpm.
 *
 */

public enum ActivityTrigger {
	SCHEDULED("S", "Scheduled"),
	EXTERNAL("E", "External"),
	MANUAL("M", "Manual"),
	RULE("R", "Rule-based");

	private String code;
	private String label;

	private ActivityTrigger(final String code, final String label) {
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
