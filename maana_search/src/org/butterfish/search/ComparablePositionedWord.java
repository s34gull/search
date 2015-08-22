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

		if (retval == 0) {
			retval = -1;
		}

		return retval;
	}

	public boolean equals(Object obj) {
		boolean isEqual = false;

		if (obj != null && obj.getClass() == getClass()) {
			final ComparablePositionedWord cpw = (ComparablePositionedWord) obj;
			isEqual = (cpw.getPositions() == positions && cpw.getWord() == word);
		}

		return isEqual;
	}

	public String toString() {
		return String.format("(word => %s | positions => %s | count => %d)", word, positions, positions.size());
	}
}
