package com.maana.search;

public abstract class SearchResult { 

	/**
	 * Name of the document for which this search result was built
	 */
	protected String documentName;

	/**
	 * character offset in document
	 */
	protected int documentPosition;

	/**
	 * score higher is more relevant
	 */
	protected int score;
	
	public SearchResult(String documentName, int documentPorition, int score) {
		this.documentName = documentName;
		this.documentPosition = documentPorition;
		this.score = score;
	}
	

	public String getDocumentName() {
		return documentName;
	}

	public int getDocumentPosition() {
		return documentPosition;
	}

	public int getScore() {
		return score;
	}
}
