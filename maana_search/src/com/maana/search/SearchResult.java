package com.maana.search;

import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.butterfish.search.ComparablePositionedWord;

public abstract class SearchResult {

	/**
	 * Name of the document for which this search result was built
	 */
	protected String documentName;

	/**
	 * character offset in document
	 */
	protected ConcurrentSkipListSet<ComparablePositionedWord> documentPositions;

	/**
	 * Total number of matching words in this search result.
	 */
	protected AtomicInteger count;

	/**
	 * Numeric value indicating relevancy. The higher the number, the more
	 * relevant the search result. Score may be determined by concrete class.
	 */
	protected AtomicInteger score;

	/**
	 * The weight to be assigned to this result set. Weight may be determined by
	 * the concrete class. This field should hold Double values via
	 * Double.doubleToLongBits(double) and Double.longBitsToDouble(long)
	 * conversions.
	 */
	protected AtomicLong weight;

	public SearchResult(String documentName) {
		this.documentName = documentName;
		this.documentPositions = new ConcurrentSkipListSet<ComparablePositionedWord>();
		this.score = new AtomicInteger(1);
		this.count = new AtomicInteger(1);
		this.weight = new AtomicLong(1);
	}

	public String getDocumentName() {
		return documentName;
	}

	public NavigableSet<ComparablePositionedWord> getDocumentPositions() {
		return documentPositions;
	}

	public int getScore() {
		return score.get();
	}
	
	public int getCount() {
		return count.get();
	}
	
	public double getWeight() {
		return Double.longBitsToDouble(weight.get());
	}

	public int getWeightedScore() {
		return (int) (getScore() * getWeight());
	}

	public String toString() {
		return String.format(
				"documentName => %s | documentPosition => %s | score => %d | count => %d | weight => %f | weighted_score => %d",
				documentName, documentPositions, getScore(), count.get(), getWeight(), getWeightedScore());
	}
}
