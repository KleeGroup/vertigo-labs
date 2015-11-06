package io.vertigo.core.definition.loader.smart;

import io.vertigo.core.definition.dsl.smart.DslEntity;
import io.vertigo.core.definition.dsl.smart.DslField;

@DslEntity
public class Address {
	@DslField(true)
	public String street;
	@DslField(false)
	public String postalCode;
	@DslField(false)
	public String city;
}
