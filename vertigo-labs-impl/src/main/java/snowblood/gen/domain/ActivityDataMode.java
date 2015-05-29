package snowblood.gen.domain;

/**
 * Task definition expected data handling.
 *
 * Depending on implementation a task may be able to handle data in various
 * ways. The tasks needs to know, however, for a particular execution which
 * way it should handle data.
 *
 * Defined modes are :
 * <ul>
 * <li>[C]omplete : fully reflects the status
 *     <br/>all active data are present
 *     <br/>logically deleted data may be present
 *     <br/>physically deleted data are absent
 * </li>
 * <li>[D]elta : reflects status changes since previous run
 *     <br/>active data are present only if modified
 *     <br/>logical deletion are notified (may contain data)
 *     <br/>physical deletion are notified
 * </li>
 * </ul>
 *
 * Note that data perimeter is not defined here. Data may represent the full
 * or delta status from only a branch or filtered part. Additional, and most
 * probably case-specific parameters may be required to determine perimeter.
 *
 */

public enum ActivityDataMode {
	COMPLETE("C", "Complete"),
	DELTA("C", "Complete");

	private String code;
	private String label;

	private ActivityDataMode(final String code, final String label) {
		this.code = code;
		this.label = label;
	}

	public String getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}

	/* DEADCODE : kept for reference, could be replaced by a hashmap
	public static ActivityDataMode getByCode(String lookupCode) {
		for (ActivityDataMode adm : ActivityDataMode.values()) {
			if (adm.getCode().equals(lookupCode)) {
				return adm;
			}
		}
		return null;
	}
	*/
}
