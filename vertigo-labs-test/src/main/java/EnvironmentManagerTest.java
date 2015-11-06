/**
 * vertigo - simple java starter
 *
 * Copyright (C) 2013, KleeGroup, direction.technique@kleegroup.com (http://www.kleegroup.com)
 * KleeGroup, Centre d'affaire la Boursidiere - BP 159 - 92357 Le Plessis Robinson Cedex - France
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.vertigo.core.definition.loader.smart;

import io.vertigo.AbstractTestCaseJU4;
import io.vertigo.core.config.AppConfig;
import io.vertigo.core.config.AppConfigBuilder;
import io.vertigo.core.config.LogConfig;
import io.vertigo.core.definition.dsl.dynamic.DynamicDefinition;
import io.vertigo.core.definition.dsl.dynamic.DynamicDefinitionRepository;
import io.vertigo.core.definition.dsl.entity.Entity;
import io.vertigo.core.spaces.definiton.DefinitionSpace;

import org.junit.Assert;
import org.junit.Test;

public final class EnvironmentManagerTest extends AbstractTestCaseJU4 {

	@Override
	protected AppConfig buildAppConfig() {
		return new AppConfigBuilder()
				.beginBoot().withLogConfig(new LogConfig("/log4j.xml")).endBoot()
				.build();
	}

	private final DynamicDefinitionRepository dynamicDefinitionRepository = DslDynamicRegistryMock.createDynamicDefinitionRepository();

	@Test
	public void simpleTest() {
		final DefinitionSpace definitionSpace = getApp().getDefinitionSpace();
		final Entity addressEntity = PersonGrammar2.get(Address.class.getName());
		final Entity personEntity = PersonGrammar2.get(Person.class.getName());
		final DynamicDefinition address1Definition = DynamicDefinitionRepository.createDynamicDefinitionBuilder("MAIN_ADDRESS", addressEntity, "io.vertigo.test.model")
				.addPropertyValue("street", "1, rue du louvre")
				.addPropertyValue("postalCode", "75008")
				.addPropertyValue("city", "Paris")
				.build();
		dynamicDefinitionRepository.addDefinition(address1Definition);

		final DynamicDefinition address2Definition = DynamicDefinitionRepository.createDynamicDefinitionBuilder("SECOND_ADDRESS", addressEntity, "io.vertigo.test.model")
				.addPropertyValue("street", "105, rue martin")
				.addPropertyValue("postalCode", "75008")
				.addPropertyValue("city", "Paris CEDEX")
				.build();
		dynamicDefinitionRepository.addDefinition(address2Definition);

		final DynamicDefinition personDefinition = DynamicDefinitionRepository.createDynamicDefinitionBuilder("MISTER_BEAN", personEntity, "io.vertigo.test.model")
				.addPropertyValue("name", "105, rue martin")
				.addPropertyValue("firstName", "75008")
				.addPropertyValue("age", 42)
				.addPropertyValue("height", 175.0d)
				.addPropertyValue("male", Boolean.TRUE)
				.addDefinition("mainAddress", address1Definition)
				.addDefinition("secondaryAddress", address2Definition)
				.build();
		dynamicDefinitionRepository.addDefinition(personDefinition);

		dynamicDefinitionRepository.solve(definitionSpace);
		Assert.assertNotNull(personDefinition);
	}
}
