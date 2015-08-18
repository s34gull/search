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
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

import com.maana.search.Search;
import com.maana.search.SearchResult;

import uk.ac.cam.ha293.tweetlabel.util.Stopwords;

public class HashSearch implements Search {

	private final ConcurrentHashMap<String, ConcurrentSkipListSet<ComparableWordTuple>> searchIndex;

	public HashSearch() {
		searchIndex = new ConcurrentHashMap<String, ConcurrentSkipListSet<ComparableWordTuple>>();
	}

	@Override
	public void indexDocument(final String documentName, final String doc) {
		if (documentName == null || doc == null || documentName.isEmpty() || doc.isEmpty()) {
			StringBuilder strbld = new StringBuilder();
			if (documentName == null || "".equals(documentName)) {
				strbld.append("Parameter 'documentName' cannot be null or empty. ");
			}
			if (doc == null || "".equals(doc)) {
				strbld.append("Parameter 'doc' cannot be null or empty. ");
			}
			throw new IllegalArgumentException(strbld.toString());
		}

		List<String> cleansedDoc = Stopwords.removeStemmedStopWords(doc);
		System.out.printf("cleansedDoc => %s\n", cleansedDoc);

		ConcurrentMap<String, Long> scoredCollection = cleansedDoc.parallelStream().collect(Collectors
				.toConcurrentMap((String word) -> word.replaceAll("\\W", ""), (String word) -> 1L, Long::sum));

		System.out.printf("collection => %s\n", scoredCollection);

		for (Entry<String, Long> entry : scoredCollection.entrySet()) {
			String word = entry.getKey();
			Long count = entry.getValue();
			// try to get the Set mapped to the word and add this document
			ComparableWordTuple cwt = new ComparableWordTuple(documentName, word, count);
			// System.out.printf("ComparableWordTuple => %s\n", cwt);

			ConcurrentSkipListSet<ComparableWordTuple> ns = null;
			if (!searchIndex.containsKey(word)) {
				ns = new ConcurrentSkipListSet<ComparableWordTuple>();
				searchIndex.putIfAbsent(word, ns);
				// System.out.printf("new NavigableSet => %s\n", ns);
			} else {
				ns = searchIndex.get(word);
				// System.out.printf("new NavigableSet => %s\n", ns);
			}
			ns.add(cwt);
		}

	}

	@Override
	public List<SearchResult> search(String searchTerms) {
		if (searchTerms == null || searchTerms.isEmpty()) {
			throw new IllegalArgumentException("Parameter 'searchTerms' cannot be null or empty. ");
		}
		HashSet<String> words = (new HashSet<String>(Arrays.asList(searchTerms.split("\\s+"))));
		System.out.printf("words => %s\n", words);

		Map<String, ComparableSearchResult> results = new HashMap<String, ComparableSearchResult>();

		for (String word : words) {
			word = word.toLowerCase();
			NavigableSet<ComparableWordTuple> ns = searchIndex.get(word);
			if (ns == null) {
				continue;
			}

			for (ComparableWordTuple cwt : ns) {
				ComparableSearchResult result = null;
				if (!results.containsKey(cwt.getDocumentName())) {
					result = new ComparableSearchResult(cwt.getDocumentName(), 0, 0);
					results.put(cwt.getDocumentName(), result);
				} else {
					result = results.get(cwt.getDocumentName());
				}
				System.out.printf("word => %s | count => %s | score => %s\n", cwt.getWord(), cwt.getCount(),
						cwt.getScore());
				result.incrementScore(cwt.getScore());
			}
		}

		return new ArrayList<SearchResult>((new TreeSet<SearchResult>(results.values())).descendingSet());
	}

}
