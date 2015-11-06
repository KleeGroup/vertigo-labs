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

import io.vertigo.core.definition.dsl.entity.Entity;
import io.vertigo.core.definition.dsl.entity.EntityGrammar;
import io.vertigo.core.definition.dsl.smart.DslSmartEntityBuilder;
import io.vertigo.lang.Assertion;

import java.util.HashMap;
import java.util.Map;

/**
 * @author npiedeloup
 */
public final class PersonGrammar2 {
	/** Personn Grammar instance. */
	public static final EntityGrammar GRAMMAR;
	private static final Map<String, Entity> ENTITIES = new HashMap<>();

	static {
		GRAMMAR = new EntityGrammar(DslSmartEntityBuilder.build(Person.class));
		for (final Entity entity : GRAMMAR.getEntities()) {
			ENTITIES.put(entity.getName(), entity);
		}
	}

	private PersonGrammar2() {
		//private
	}

	static Entity get(final String entityName) {
		Assertion.checkState(ENTITIES.containsKey(entityName), "grammar doesn't contain this entity {0}", entityName);
		//-----
		return ENTITIES.get(entityName);
	}
}
