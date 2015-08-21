package com.maana.search;

import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import org.butterfish.search.ComparablePositionedWord;

public abstract class SearchResult {

	/**
	 * Name of the document for which this search result was built
	 */
	protected String documentName;

	/**
	 * character offset in document
	 */
	protected TreeSet<ComparablePositionedWord> documentPositions;

	/**
	 * Used to compute weight
	 */
	protected AtomicInteger count;

	/**
	 * score higher is more relevant
	 */
	protected AtomicInteger score;

	public SearchResult(String documentName) {
		this.documentName = documentName;
		this.documentPositions = new TreeSet<ComparablePositionedWord>();
		this.score = new AtomicInteger(0);
		this.count = new AtomicInteger(0);
	}

	public String getDocumentName() {
		return documentName;
	}

	public TreeSet<ComparablePositionedWord> getDocumentPositions() {
		return documentPositions;
	}
	
	public double getWeight() {
		return Math.log10(documentPositions.size() + 1);
	}

	public int getScore() {
		return score.get();
	}
	
	public int getWeightedScore() {
		return (int)(getScore() * getWeight());
	}

	public String toString() {
		return String.format("documentName => %s | documentPosition => %s | score => %d | count => %d | weight => %f | weighted_score => %d", documentName,
				documentPositions, getScore(), count.get(), getWeight(), getWeightedScore());
	}
}
