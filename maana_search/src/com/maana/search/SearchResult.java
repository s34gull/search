package com.maana.search;

import java.util.concurrent.atomic.AtomicInteger;

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
	protected AtomicInteger score;

	public SearchResult(String documentName, int documentPosition, int score) {
		this.documentName = documentName;
		this.documentPosition = documentPosition;
		this.score = new AtomicInteger(score);
	}

	public String getDocumentName() {
		return documentName;
	}

	public int getDocumentPosition() {
		return documentPosition;
	}

	public int getScore() {
		return score.get();
	}

	public String toString() {
		return String.format("documentName => %s | documentPosition => %s | score => %s", documentName,
				documentPosition, score);
	}
}
