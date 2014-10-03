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
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanQuery.TooManyClauses;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;

import phoneticsearch.MessageOutput;
import phoneticsearch.Person;

/**
 * Plugin de d'indexation de DtList utilisant Lucene en Ram.
 * 
 * @author npiedeloup
 */
public final class LuceneIndexPlugin {
	private static final String UUID_KEY = "UUID";
	private final Analyzer indexAnalyser;
	private final Analyzer frQueryAnalyser;
	private final Analyzer nonfrQueryAnalyser;
	private final Analyzer noPhoneticQueryAnalyser;
	private LuceneIndex<Person> index;

	//	private final int nbTermsMax = 1024; //a paramètrer
	public enum IndexType {
		EXACT, PHONETIC, FUZZY
	}

	/**
	 * Constructeur.
	 * @param localeManager Manager des messages localisés
	 * @param cacheManager Manager des caches
	 */
	public LuceneIndexPlugin(final IndexType type) {
		indexAnalyser = new DefaultAnalyzer(true, true, false); //les stop word marchent mal si asymétrique entre l'indexation et la query
		frQueryAnalyser = new DefaultAnalyzer(true, false, false);
		nonfrQueryAnalyser = new DefaultAnalyzer(false, true, false);
		noPhoneticQueryAnalyser = new DefaultAnalyzer(false, false, false);
	}

	private LuceneIndex<Person> createIndex(final List<Person> fullDtc, final MessageOutput out) throws IOException {
		final RamLuceneIndex<Person> luceneDb = new RamLuceneIndex<>(indexAnalyser);
		try (final IndexWriter indexWriter = luceneDb.createIndexWriter()) {
			long indexCount = 0;

			for (final Person dto : fullDtc) {
				final Document document = new Document();
				final String uuid = UUID.randomUUID().toString();
				document.add(createKeyword(UUID_KEY, uuid, true));

				document.add(createIndexed("fullname", dto.getName() + " " + dto.getFirstname(), false));
				document.add(createIndexed("name", dto.getName(), false));
				document.add(createIndexed("firstname", dto.getFirstname(), false));

				//final String birthdaysString = dto.getBirthday() != null ? DateTools.dateToString(dto.getBirthday(), DateTools.Resolution.DAY) : "";
				//document.add(createKeyword("birthday", birthdaysString, false));

				//document.add(createIndexed("address", dto.getAddress(), false));
				//document.add(createIndexed("zipcode", dto.getZipcode(), false));
				//document.add(createIndexed("city", dto.getCity(), false));
				//document.add(createIndexed("phone", dto.getPhone(), false));

				//final StringBuilder sb = new StringBuilder();
				//sb.append(" / ").append(dto.getName()).append(" / ").append(dto.getFirstname()) //
				//		.append(" / ").append(birthdaysString).append(" / ").append(dto.getAddress()) //
				//		.append(" / ").append(dto.getZipcode()).append(" / ").append(dto.getCity()) //
				//		.append(" / ").append(dto.getPhone());
				//document.add(createIndexed("all", sb.toString(), false));

				indexWriter.addDocument(document);
				luceneDb.mapDocument(uuid, dto);
				if (++indexCount % 10000 == 0) {
					out.displayMessage("index " + indexCount);
				}
			}
			out.displayMessage("index " + indexCount + " done");
			return luceneDb;
		}
	}

	private List<Person> getCollection(final String keywords, final String[] searchedDtFieldList, final int maxRows, final String boostedField) throws IOException, InvalidTokenOffsetsException {
		final Query query = createQuery(keywords, searchedDtFieldList, boostedField);
		return executeQuery(index, query, maxRows);
	}

	private List<Person> executeQuery(final LuceneIndex<Person> luceneDb, final Query query, final int maxRow) throws IOException, InvalidTokenOffsetsException {
		try (final IndexReader indexReader = luceneDb.createIndexReader()) {
			final IndexSearcher searcher = new IndexSearcher(indexReader);
			//1. Exécution des la Requête
			final TopDocs topDocs = searcher.search(query, null, maxRow);

			//2. Traduction du résultat Lucene en une Collection
			return translateDocs(luceneDb, searcher, topDocs, query);
		} catch (final TooManyClauses e) {
			throw new RuntimeException("Too many clauses", e);
		}
	}

	/**
	 * 
	 * @throws IOException
	 * @throws InvalidTokenOffsetsException
	 */
	private List<Person> translateDocs(final LuceneIndex<Person> luceneDb, final IndexSearcher searcher, final TopDocs topDocs, final Query query) throws IOException, InvalidTokenOffsetsException {
		final List<Person> dtcResult = new ArrayList<>();
		for (final ScoreDoc scoreDoc : topDocs.scoreDocs) {
			final Document document = searcher.doc(scoreDoc.doc);
			final Person dto = luceneDb.getObjectIndexed(document.get(UUID_KEY));
			dto.setScore(Math.round(scoreDoc.score / Math.max(topDocs.getMaxScore(), 2.5f) * 100));
			dtcResult.add(dto);

			//final QueryScorer queryScorer = new QueryScorer(query);
			//final Highlighter highlighter = new Highlighter(queryScorer);
			//highlighter.setTextFragmenter(new SimpleSpanFragmenter(queryScorer, Integer.MAX_VALUE));
			//highlighter.setMaxDocCharsToAnalyze(Integer.MAX_VALUE);
			//final String[] strings = highlighter.getBestFragments(indexAnalyser, "fullname", dto.getName() + " " + dto.getFirstname(), 5);
			//System.out.println("found: " + Arrays.toString(strings));
		}
		return dtcResult;
	}

	private Query createQuery(final String keywords, final String[] searchedFieldList, final String boostedField) throws IOException {
		if (keywords == null || keywords.isEmpty()) {
			return new MatchAllDocsQuery();
		}
		//----------------
		final BooleanQuery query = new BooleanQuery();

		for (final String dtField : searchedFieldList) {
			final Query queryWord = createParsedQuery(dtField, keywords);
			if (dtField.equals(boostedField)) {
				queryWord.setBoost(queryWord.getBoost() * 4);
			}
			query.add(queryWord, BooleanClause.Occur.SHOULD);
		}
		System.out.println("look for : " + query);
		return query;
	}

	private Query createParsedQuery(final String fieldName, final String keywords) throws IOException {
		final BooleanQuery query = new BooleanQuery();
		final Reader simpleReader = new StringReader(keywords);
		try (final TokenStream simpleTokenStream = noPhoneticQueryAnalyser.tokenStream(fieldName, simpleReader)) {
			simpleTokenStream.reset();
			try {
				final CharTermAttribute simpleTermAttribute = simpleTokenStream.getAttribute(CharTermAttribute.class);
				while (simpleTokenStream.incrementToken()) {
					final String tokenizedKeyword = new String(simpleTermAttribute.buffer(), 0, simpleTermAttribute.length());
					final BooleanQuery wordQuery = new BooleanQuery();
					final BooleanQuery frWordQuery = createWordQuery(frQueryAnalyser, fieldName, tokenizedKeyword);
					frWordQuery.setBoost(frWordQuery.getBoost() * 2);
					final BooleanQuery nonfrWordQuery = createWordQuery(nonfrQueryAnalyser, fieldName, tokenizedKeyword);
					nonfrWordQuery.setBoost(nonfrWordQuery.getBoost() / 2);
					wordQuery.add(frWordQuery, BooleanClause.Occur.SHOULD);
					wordQuery.add(nonfrWordQuery, BooleanClause.Occur.SHOULD);
					query.add(wordQuery, BooleanClause.Occur.MUST);
				}
			} finally {
				simpleReader.reset();
				simpleTokenStream.end();
			}
		}
		return query;
	}

	private BooleanQuery createWordQuery(final Analyzer currentQueryAnalyser, final String fieldName, final String tokenizedKeyword) throws IOException {
		final BooleanQuery wordQuery = new BooleanQuery();
		final Reader reader = new StringReader(tokenizedKeyword);
		try (final TokenStream tokenStream = currentQueryAnalyser.tokenStream(fieldName, reader)) {
			tokenStream.reset();
			try {
				final CharTermAttribute termAttribute = tokenStream.getAttribute(CharTermAttribute.class);
				//final List<SpanQuery> clauses = new ArrayList<>();

				while (tokenStream.incrementToken()) {
					final BooleanQuery fuzzyTermQuery = new BooleanQuery();
					final String term = new String(termAttribute.buffer(), 0, termAttribute.length());
					final PrefixQuery termQuery = new PrefixQuery(new Term(fieldName, term));
					fuzzyTermQuery.add(termQuery, BooleanClause.Occur.SHOULD);
					final FuzzyQuery fuzzyQuery = new FuzzyQuery(new Term(fieldName, term), 1, 0, 50, true);
					fuzzyTermQuery.add(fuzzyQuery, BooleanClause.Occur.SHOULD);
					fuzzyQuery.setBoost(0.8f);
					wordQuery.add(fuzzyTermQuery, BooleanClause.Occur.SHOULD);

					//clauses.add(new SpanMultiTermQueryWrapper(termQuery));
					//clauses.add(new SpanMultiTermQueryWrapper(fuzzyQuery));
				}
				//Pour l'instant slop � 0, essayer d'autre chose apr�s
				//3e param inOrder � true car on veut que les mots soient en ordre
				//final SpanNearQuery spanNearQuery = new SpanNearQuery(clauses.toArray(new SpanQuery[clauses.size()]), 0, true);
				//query.add(spanNearQuery, BooleanClause.Occur.MUST);
			} finally {
				reader.reset();
				tokenStream.end();
			}
		}
		return wordQuery;
	}

	private static IndexableField createKeyword(final String fieldName, final String fieldValue, final boolean storeValue) {
		return new StringField(fieldName, fieldValue, storeValue ? Field.Store.YES : Field.Store.YES);
	}

	private static IndexableField createIndexed(final String fieldName, final String fieldValue, final boolean storeValue) {
		return new TextField(fieldName, fieldValue, storeValue ? Field.Store.YES : Field.Store.YES);
	}

	/**
	 * Filtre une liste par des mots clés et une recherche fullText.
	 * @param <Person> type d'objet de la liste
	 * @param keywords Mots clés de la recherche
	 * @param searchedFields Liste des champs sur lesquels porte la recheche
	 * @param maxRows Nombre de résultat maximum
	 * @param boostedField Liste des champs boostés (boost de 4 en dur)
	 * @param dtc Liste source
	 * @return Liste résultat
	 */
	public List<Person> getCollection(final String keywords) {
		try {
			return this.<Person> getCollection(keywords, new String[] { "fullname" }, 20, "name");
		} catch (final IOException | InvalidTokenOffsetsException e) {
			throw new RuntimeException("Erreur d'indexation", e);
		}
	}

	public void indexDatas(final List<Person> datas, final MessageOutput out) throws IOException {
		index = null;
		index = createIndex(datas, out);
	}
}
