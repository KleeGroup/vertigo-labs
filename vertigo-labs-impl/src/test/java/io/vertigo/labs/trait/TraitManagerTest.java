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
package io.vertigo.labs.trait;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import io.vertigo.AbstractTestCaseJU4;
import io.vertigo.lang.Option;

/**
 * @author pchretien
 */
public class TraitManagerTest extends AbstractTestCaseJU4 {
	@Inject
	private TraitManager traitManager;

	@Test
	public void loadEmptyData() {
		traitManager.deleteTrait(Commenting.class, "45");
		final Option<Commenting> commenting = traitManager.findTrait(Commenting.class, "45");
		Assert.assertTrue(!commenting.isPresent());
	}

	@Test
	public void putData() {
		final Commenting commenting = new Commenting();
		commenting.setComments("my nice comment");
		traitManager.putTrait(Commenting.class, "45", commenting);

		final Option<Commenting> commenting2 = traitManager.findTrait(Commenting.class, "45");
		Assert.assertTrue(commenting2.isPresent());
	}
}
