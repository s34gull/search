package org.butterfish.search;

import com.google.common.hash.Hashing;
import com.maana.search.SearchResult;

/**
 * 
 * @author jedwards
 *
 */
public class ComparableSearchResult extends SearchResult implements Comparable<ComparableSearchResult> {

	private int hashCode;
	/**
	 * 
	 * @param documentName
	 */
	public ComparableSearchResult(String documentName) {
		super(documentName);
		
		this.hashCode = Math.abs(
				Hashing.murmur3_32().newHasher()
					.putBytes(documentName.getBytes())
					.hash()
					.asInt());
	}

	/**
	 * 
	 * @param word
	 * @param score
	 */
	public void addScoredWord(ComparablePositionedWord word, Integer score) {
		System.out.printf(
				"ComparableSearchResult.addScoredWord(): documentName => %s | positionedWord => %s | score => %d\n",
				documentName, word, score);
		this.documentPositions.add(word);
		this.count.addAndGet(word.getPositions().size());
		this.score.addAndGet(score);
		this.weight.addAndGet(Double.doubleToLongBits(Math.log10(word.getPositions().size())));
		this.hashCode = Hashing.murmur3_32().newHasher()
			.putBytes(documentName.getBytes())
			.putInt(getWeightedScore())
			.hash()
			.asInt();
	}

	/**
	 * 
	 */
	@Override
	public double getWeight() {
		return Math.log10(this.documentPositions.size() + 1);
	}

	/**
	 * Returns a negative integer, or a positive integer as this object is less
	 * than, or greater than the specified object. A set is never considered
	 * equal based on size.
	 */
	public int compareTo(ComparableSearchResult t) {
		int retval = 1;

		if (t != null) {
			retval = this.getWeightedScore() - t.getWeightedScore();
		}

		if (retval == 0) {
			retval = documentName.compareTo(t.getDocumentName());
		}
		return retval;
	}

	/**
	 * 
	 */
	public boolean equals(Object obj) {
		boolean isEqual = false;

		if (obj != null && obj.getClass() == getClass()) {
			final ComparableSearchResult csr = (ComparableSearchResult) obj;
			isEqual = (csr.getDocumentName() == documentName && csr.getWeightedScore() == getWeightedScore());
		}

		return isEqual;
	}
	
	/**
	 * 
	 */
	public int hashCode() {
		return hashCode;
	}

}
