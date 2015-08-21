package org.butterfish.search;

import com.maana.search.SearchResult;

public class ComparableSearchResult extends SearchResult implements Comparable<ComparableSearchResult> {

	public ComparableSearchResult(String documentName) {
		super(documentName);
	}

	/**
	 * Returns a negative integer, zero, or a positive integer as this object is
	 * less than, equal to, or greater than the specified object.
	 */
	public int compareTo(ComparableSearchResult t) {
		return this.score.get() - t.score.get();
	}
	
	public void addScoredWord(ComparablePositionedWord word, Integer score) {
		this.documentPositions.add(word);
		this.score.addAndGet(score);
	}

}
