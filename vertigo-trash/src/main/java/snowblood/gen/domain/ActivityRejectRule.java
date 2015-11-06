package snowblood.gen.domain;

/**
 * Defines the behavior of a task regarding data rejection.
 *
 * <ul>
 * <li>[NOD]ata : rejection rule concept does not apply to this task.</li>
 * <li>[GLO]bal : either all data are processed successfully or none.
 *     It is expected that no change occurs on target data when such a
 *     job fails (global transaction).</li>
 * <li>[LIN]e by line : each "line" of data is processed independently
 *     from the transaction point of view. This may apply to a "block"
 *     by block transaction. An error count threshold may be defined to
 *     prevent full processing after a certain number of failure. However
 *     processed lines are commited and cannot be rollbacked.</li>
 * </ul>
 *
 */

public enum ActivityRejectRule {
	NODATA("NOD", "No data"),
	GLOBAL("GLO", "Global"),
	LINE("LIN", "Line by line");

	private String code;
	private String label;

	private ActivityRejectRule(final String code, final String label) {
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
