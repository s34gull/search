package org.butterfish.search;

import java.util.SortedSet;

import com.maana.search.SearchResult;

public class ComparableSearchResult extends SearchResult implements Comparable<ComparableSearchResult> {

	public ComparableSearchResult(String documentName, int documentPosition, int score) {
		super(documentName, documentPosition, score);
	}

	/**
	 * Returns a negative integer, zero, or a positive integer as this object is
	 * less than, equal to, or greater than the specified object.
	 */
	public int compareTo(ComparableSearchResult t) {
		return this.score.get() - t.score.get();
	}
	
	public void incrementScore(int delta) {
		score.addAndGet(delta);
	}

}
