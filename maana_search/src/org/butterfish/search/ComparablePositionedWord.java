package org.butterfish.search;

import java.util.List;

public class ComparablePositionedWord implements Comparable<ComparablePositionedWord> {

	private String word;
	private List<Long> positions;

	public static ComparablePositionedWord fromComparableWordTuple(ComparableWordTuple cwt) {
		return new ComparablePositionedWord(cwt.getWord(), cwt.getDocumentPositions());
	}

	public ComparablePositionedWord(String word, List<Long> positions) {
		this.word = word;
		this.positions = positions;
	}

	public String getWord() {
		return word;
	}

	public List<Long> getPositions() {
		return positions;
	}

	public int compareTo(ComparablePositionedWord t) {
		int retval = 1;

		if (t != null) {
			retval = this.positions.size() - t.getPositions().size();
		}

		return retval;
	}

	public String toString() {
		return String.format("word => %s | positions => %s", word, positions);
	}
}
