package snowblood.gen.domain;

/**
 * Defines a task expected data exchange.
 * 
 * <ul>
 * <li>[LOC]al : when no data exchange happens between the application and
 * the outside world. These tasks are likely to be computations only, they
 * may produce (temporary) data files or streams that are not sent outside
 * the application.  
 * </li>
 * <li>[OUT]put : applies to tasks sending data outside of the application.
 * The task may expect an acknowledgement message from the target application.
 * </li>
 * <li>[IN_]put : applies to tasks receiving data from outside of the
 * application. Such a task is allowed to send an acknowledgement message
 * with a result status.
 * </li>
 * <li>[SER]vice : applies to other tasks or when bi-directionnal exchange
 * are possible. This applies to most web services when both the call and the
 * answer may contain data (query/reply).</li>
 * </ul>
 *  
 */

public enum ActivityDirection {
	LOCAL ("LOC", "Local"),
	OUTPUT ("OUT", "Output (export)"),
	INPUT ("INx", "Input (import)"),
	SERVICE ("SER", "Service");
	
    private String code;
	private String label;
	
	ActivityDirection(String code, String label){
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
