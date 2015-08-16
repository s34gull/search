package org.butterfish.search;

import java.util.SortedSet;

import com.maana.search.SearchResult;

public class ComparableSearchResult extends SearchResult implements Comparable<ComparableSearchResult> {

	private SortedSet<Object> words; 

	public ComparableSearchResult(String documentName, int documentPorition, int score) {
		super(documentName, documentPorition, score);
	}

	/**
	 * Returns a negative integer, zero, or a positive integer as this object is
	 * less than, equal to, or greater than the specified object.
	 */
	public int compareTo(ComparableSearchResult t) {
		return this.score - t.score;
	}

}
