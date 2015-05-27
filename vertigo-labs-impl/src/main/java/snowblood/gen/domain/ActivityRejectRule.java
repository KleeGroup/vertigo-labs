package snowblood.gen.domain;

/**
 *
 */

public enum ActivityRejectRule {
	NODATA ("N", "No data"),
	GLOBAL ("G", "Global"),
	LINE ("L", "Line by line");
	
    private String code;
	private String label;
	
	ActivityRejectRule(String code, String label){
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
