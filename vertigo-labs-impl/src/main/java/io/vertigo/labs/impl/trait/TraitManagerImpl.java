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
package io.vertigo.labs.impl.trait;

import io.vertigo.dynamo.kvstore.KVStoreManager;
import io.vertigo.dynamo.transaction.VTransactionManager;
import io.vertigo.dynamo.transaction.VTransactionWritable;
import io.vertigo.labs.trait.Trait;
import io.vertigo.labs.trait.TraitManager;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.Option;

import javax.inject.Inject;
import javax.inject.Named;

public final class TraitManagerImpl implements TraitManager {
	private final String collection;
	private final KVStoreManager kvStoreManager;
	private final VTransactionManager transactionManager;

	@Inject
	public TraitManagerImpl(final @Named("collection") String collection, final KVStoreManager kvStoreManager, final VTransactionManager transactionManager) {
		Assertion.checkArgNotEmpty(collection);
		Assertion.checkNotNull(kvStoreManager);
		Assertion.checkNotNull(transactionManager);
		//-----
		this.collection = collection;
		this.kvStoreManager = kvStoreManager;
		this.transactionManager = transactionManager;
	}

	/** {@inheritDoc} */
	@Override
	public <T extends Trait> Option<T> findTrait(final Class<T> traitClass, final String subjectId) {
		Assertion.checkNotNull(traitClass);
		//-----
		return doFind(subjectId, traitClass.getSimpleName(), traitClass);
	}

	/** {@inheritDoc} */
	@Override
	public <T extends Trait> void putTrait(final Class<T> traitClass, final String subjectId, final T trait) {
		Assertion.checkNotNull(traitClass);
		//-----
		doStore(subjectId, traitClass.getSimpleName(), trait);
	}

	/** {@inheritDoc} */
	@Override
	public <T extends Trait> void deleteTrait(final Class<T> traitClass, final String subjectId) {
		doDelete(subjectId, traitClass.getSimpleName());
	}

	//=========================================================================
	//=========================================================================
	//=========================================================================

	private void doStore(final String subjectId, final String traitType, final Trait trait) {
		Assertion.checkNotNull(trait);
		Assertion.checkArgNotEmpty(traitType);
		//-----
		try (VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
			kvStoreManager.put(collection, traitType + ":" + subjectId, trait);
			transaction.commit();
		}
	}

	private <C extends Trait> Option<C> doFind(final String subjectId, final String traitType, final Class<C> clazz) {
		Assertion.checkNotNull(subjectId);
		Assertion.checkArgNotEmpty(traitType);
		//-----
		try (VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
			return kvStoreManager.find(collection, traitType + ":" + subjectId, clazz);
		}
	}

	private void doDelete(final String subjectId, final String traitType) {
		Assertion.checkNotNull(subjectId);
		Assertion.checkArgNotEmpty(traitType);
		//-----
		try (VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
			kvStoreManager.remove(collection, traitType + ":" + subjectId);
			transaction.commit();
		}
	}

}
