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

import uk.ac.cam.ha293.tweetlabel.util.Stemmer;
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

		Map<String, ArrayList<Long>> cleansedDoc = Stopwords.reduceNoStemmedStopWords(doc, true);
		// System.out.printf("cleansedDoc => %s\n", cleansedDoc);

		for (Entry<String, ArrayList<Long>> entry : cleansedDoc.entrySet()) {

			ComparableWordTuple cwt = new ComparableWordTuple(entry.getKey(), entry.getValue(), documentName);
			// System.out.printf("ComparableWordTuple => %s\n", cwt);

			searchIndex.putIfAbsent(cwt.getWord(), new ConcurrentSkipListSet<ComparableWordTuple>());
			ConcurrentSkipListSet<ComparableWordTuple> ns = searchIndex.get(cwt.getWord());
			// System.out.printf("new NavigableSet => %s\n", ns);

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
			word = new Stemmer().stem(word.trim().toLowerCase());
			// System.out.printf("Stemmed word => %s\n", word);

			NavigableSet<ComparableWordTuple> ns = searchIndex.get(word);
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
				// System.out.printf("word => %s | count => %s | score => %s\n",
				// cwt.getWord(), cwt.getCount(),
				// cwt.getScore());
				result.addScoredWord(ComparablePositionedWord.fromComparableWordTuple(cwt), cwt.getScore());
			}
		}

		return new ArrayList<SearchResult>((new TreeSet<SearchResult>(results.values())).descendingSet());
	}

}
