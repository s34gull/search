package org.butterfish.search;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Function;
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
		List<String> cleansedDoc = Stopwords.removeStemmedStopWords(doc);

		Map<String, Long> collection = cleansedDoc.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		
		for(Entry<String,Long> entry : collection.entrySet()) {
			String word = entry.getKey();
			Long count = entry.getValue();
			// try to get the Set mapped to the word and add this document
			ComparableWordTuple cwt = new ComparableWordTuple(documentName, word, count);
			// use locks to control creation of new entry in map
		}

	}

	@Override
	public List<SearchResult> search(String searchTerms) {
		// TODO Auto-generated method stub
		return null;
	}

}
