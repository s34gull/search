package com.maana.search;

import java.util.List;

/**
 * Interface that defines generic search operations: indexing of documents and
 * finding documents via that index that match specified search terms.
 * 
 * @author jedwards
 *
 */
public interface Search {
	
	/**
	 * 
	 * @param documentName
	 * @param doc
	 */
	public void indexDocument(String documentName, String doc);

	/**
	 * 
	 * @param searchTerms
	 * @return
	 */
    public List<SearchResult> search(String searchTerms);
}
