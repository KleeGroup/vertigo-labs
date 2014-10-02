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
package phoneticsearch.lucene;

import java.io.IOException;
import java.io.Serializable;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;

/**
 * Interface réprésentant un IndexLucene.
 * 
 * @author npiedeloup
 * @param <D> type d'objet indexé
 */
public interface LuceneIndex<D> extends Serializable {

	/**
	 * @return IndexWriter pour mettre à jour l'index. Doit-être close dans un finally.
	 * @throws IOException Exception I/O
	 */
	IndexWriter createIndexWriter() throws IOException;

	/**
	 * @return Searcher pour consulter l'index. Doit-être close dans un finally.
	 * @throws IOException Exception I/O
	 */
	IndexReader createIndexReader() throws IOException;

	//	/**
	//	 * Fermer la base lucene.
	//	 * @throws IOException Exception I/O
	//	 */
	//	void close() throws IOException;

	/**
	 * @param pkValue Clé de l'objet
	 * @return Objet associé dans cet index.
	 */
	D getObjectIndexed(String pkValue);

}
