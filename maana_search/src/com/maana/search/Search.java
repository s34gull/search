package com.maana.search;

import java.util.List;

public interface Search {
	public void indexDocument(String documentName, String doc);

    public List<SearchResult> search(String searchTerms);
}
