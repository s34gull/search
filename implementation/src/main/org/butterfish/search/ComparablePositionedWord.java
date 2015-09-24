package org.butterfish.search;

import java.util.List;

import org.butterfish.search.ComparableWordTuple;

import com.google.common.hash.Hashing;

/**
 * 
 * @author jedwards
 *
 */
public class ComparablePositionedWord implements Comparable<ComparablePositionedWord> {

	private final String word;
	private final List<Long> positions;
	private final int hashCode;

	/**
	 * 
	 * @param cwt
	 * @return
	 */
	public static ComparablePositionedWord fromComparableWordTuple(ComparableWordTuple cwt) {
		return new ComparablePositionedWord(cwt.getWord(), cwt.getDocumentPositions());
	}

	/**
	 * 
	 * @param word
	 * @param positions
	 */
	public ComparablePositionedWord(String word, List<Long> positions) {
		this.word = word;
		this.positions = positions;
		this.hashCode = Math.abs(
				Hashing.murmur3_32().newHasher()
					.putBytes(word.getBytes())
					.putBytes(positions.toString().getBytes())
					.hash()
					.asInt());
	}

	/**
	 * 
	 * @return
	 */
	public String getWord() {
		return word;
	}

	/**
	 * 
	 * @return
	 */
	public List<Long> getPositions() {
		return positions;
	}

	/**
	 * 
	 */
	public int compareTo(ComparablePositionedWord t) {
		int retval = 1;

		if (t != null) {
			retval = this.positions.size() - t.getPositions().size();
		}

		if (retval == 0) {
			retval = word.compareTo(t.getWord());
		}

		return retval;
	}

	/**
	 * 
	 */
	public boolean equals(Object obj) {
		boolean isEqual = false;

		if (obj != null && obj.getClass() == getClass()) {
			final ComparablePositionedWord cpw = (ComparablePositionedWord) obj;
			isEqual = (cpw.getPositions() == positions && cpw.getWord() == word);
		}

		return isEqual;
	}

	/**
	 * 
	 */
	public String toString() {
		return String.format("(word => %s | positions => %s | count => %d)", word, positions, positions.size());
	}
	
	/**
	 * 
	 */
	public int hashCode() {
		return hashCode;
	}
}
