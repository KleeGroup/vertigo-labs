package io.vertigo.pandora.webservices.smart;

import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

import java.util.List;

public final class SmartResult<D extends DtObject> {
	//	public String name;
	public SmartDataDefinition definition;
	public List<D> rows;

	public SmartResult(final Class<D> dtObjectClass, final List<D> rows) {
		definition = new SmartDataDefinition(DtObjectUtil.findDtDefinition(dtObjectClass));
		this.rows = rows;
	}
	//	public SmartDataDefinition getDefinition(){
	//		
	//	}
	//
	//	public List<D extends DtObject> getRows(){
	//		
	//	}
}
