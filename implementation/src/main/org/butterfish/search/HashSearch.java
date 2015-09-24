package org.butterfish.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import com.maana.search.Search;
import com.maana.search.SearchResult;

import uk.ac.cam.ha293.tweetlabel.util.Stemmer;
import uk.ac.cam.ha293.tweetlabel.util.Stopwords;

/**
 * An implementation of the Search interface that leverages a ConcurrentHashMap
 * for the search term to document::term-position tuple. The index does not
 * contain any stop words; only lowercase, stemmed words are stored in the
 * index. The index supports concurrent addition of documents to the index.
 * There is no support for removal of a given document from the index.
 * 
 * @author jedwards
 *
 */
public class HashSearch implements Search {

	private final ConcurrentHashMap<MString, ConcurrentSkipListSet<ComparableWordTuple>> searchIndex;

	/**
	 * Creates a new instance of the HashSearch object, which implements a naive
	 * stemmed-term search routine.
	 */
	public HashSearch() {
		searchIndex = new ConcurrentHashMap<MString, ConcurrentSkipListSet<ComparableWordTuple>>();
	}

	/**
	 * Documents are indexed by tokenizing on whitespace, converting the tokens
	 * to lowercase, then stemming the tokens (after having removed all stop
	 * words). These stemmed tokens are then stored as the keys in a
	 * ConcurrentHashMap, the keys of which resolve to a NavigableSet of
	 * ComparableWordTuples, which contain the document name in which the word
	 * was found, as well as the positions (0-indexed offset from beginning of
	 * said document) where that term was found.
	 * 
	 * @param documentName
	 *            The name of the document being indexed.
	 * @param doc
	 *            The "document" to index.
	 * 
	 * @throws IllegalArgumentException
	 *             if any of the parameters are null or empty.
	 */
	public void indexDocument(final String documentName, final String doc) {
		if (documentName == null || doc == null || documentName.isEmpty() || doc.isEmpty()) {
			StringBuilder strbld = new StringBuilder();
			if (documentName == null || documentName.isEmpty()) {
				strbld.append("Parameter 'documentName' cannot be null or empty. ");
			}
			if (doc == null || doc.isEmpty()) {
				strbld.append("Parameter 'doc' cannot be null or empty. ");
			}
			throw new IllegalArgumentException(strbld.toString());
		}

		Map<String, ArrayList<Long>> cleansedDoc = Stopwords.reduceNoStemmedStopWords(doc, true);
		// System.out.printf("cleansedDoc => %s\n", cleansedDoc);

		for (Entry<String, ArrayList<Long>> entry : cleansedDoc.entrySet()) {

			ComparableWordTuple cwt = new ComparableWordTuple(entry.getKey(), entry.getValue(), documentName);
			// System.out.printf("ComparableWordTuple => %s\n", cwt);

			MString word = new MString(cwt.getWord());
			searchIndex.putIfAbsent(word, new ConcurrentSkipListSet<ComparableWordTuple>());
			ConcurrentSkipListSet<ComparableWordTuple> ns = searchIndex.get(word);
			// System.out.printf("new NavigableSet => %s\n", ns);

			ns.add(cwt);
		}

	}

	/**
	 * Searching involves tokenizing the list of search terms on whitespace,
	 * converting the tokens to lowercase and stemming them. The converted
	 * tokens are then used to access possible entries within the index (the
	 * ConcurrentHashMap).
	 * 
	 * @param searchTerms
	 *            Whitespace delimited list of terms we are looking for
	 * 
	 * @return List of SearchResult objects. The list will never be null, but
	 *         MAY be empty. The list will be sorted in descending score order.
	 */
	public List<SearchResult> search(String searchTerms) {
		if (searchTerms == null || searchTerms.isEmpty()) {
			throw new IllegalArgumentException("Parameter 'searchTerms' cannot be null or empty. ");
		}
		HashSet<String> words = (new HashSet<String>(Arrays.asList(searchTerms.split("\\s+"))));
		// System.out.printf("HashSearch.search(): searchTerms => %s\n", words);

		Map<String, ComparableSearchResult> results = new HashMap<String, ComparableSearchResult>();

		for (String word : words) {
			word = new Stemmer().stem(word.trim().toLowerCase());
			// System.out.printf("Stemmed word => %s\n", word);
			// System.out.printf("Stemmed word => %s\n", new
			// MString(word).hashCode());

			NavigableSet<ComparableWordTuple> ns = searchIndex.get(new MString(word));
			if (ns == null) {
				continue;
			}

			for (ComparableWordTuple cwt : ns) {
				ComparableSearchResult result = null;
				if (!results.containsKey(cwt.getDocumentName())) {
					result = new ComparableSearchResult(cwt.getDocumentName());
					results.put(cwt.getDocumentName(), result);
				} else {
					result = results.get(cwt.getDocumentName());
				}

				result.addScoredWord(ComparablePositionedWord.fromComparableWordTuple(cwt), cwt.getScore());
			}
		}

		/*
		 * Returns list of search results in descending order.
		 */
		return new ArrayList<SearchResult>((new TreeSet<SearchResult>(results.values())).descendingSet());
	}

}
