package io.vertigo.core.definition.loader.smart;

import io.vertigo.core.definition.dsl.smart.DslEntity;
import io.vertigo.core.definition.dsl.smart.DslField;

@DslEntity
public class Person {
	@DslField(true)
	public String name;
	@DslField(true)
	public String firstName;
	@DslField(true)
	public Integer age;
	@DslField(true)
	public Double height;
	@DslField(true)
	public Boolean male;
	@DslField(true)
	public Address mainAddress;
	@DslField(false)
	public Address secondaryAddress;
}
