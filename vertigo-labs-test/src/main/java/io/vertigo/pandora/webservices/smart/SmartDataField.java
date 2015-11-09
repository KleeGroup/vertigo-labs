package io.vertigo.pandora.webservices.smart;

import io.vertigo.dynamo.domain.metamodel.DtField;

public final class SmartDataField {
	public final String name;
	public final String label;
	public final boolean required;
	public final String dataType;

	SmartDataField(final DtField field) {
		name = field.getName();
		required = field.isRequired();
		label = field.getLabel().getDisplay();
		dataType = field.getDomain().getDataType().toString();
	}
}
