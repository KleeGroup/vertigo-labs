package io.vertigo.pandora.webservices.smart;

import io.vertigo.dynamo.domain.metamodel.DtDefinition;
import io.vertigo.dynamo.domain.metamodel.DtField;

import java.util.ArrayList;
import java.util.List;

public final class SmartDataDefinition {
	public String name;
	public List<SmartDataField> fields = new ArrayList<>();

	SmartDataDefinition(final DtDefinition dtDefinition) {
		name = dtDefinition.getName();
		for (final DtField dtField : dtDefinition.getFields()) {
			fields.add(new SmartDataField(dtField));
		}
	}
}
