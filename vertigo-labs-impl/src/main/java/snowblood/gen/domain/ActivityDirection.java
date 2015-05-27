package snowblood.gen.domain;

/**
 *
 */

public enum ActivityDirection {
	NODATA ("N", "No data"),
	IMPORT ("E", "Export"),
	EXPORT ("I", "Import");
	
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
