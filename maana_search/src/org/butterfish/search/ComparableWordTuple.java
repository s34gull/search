package org.butterfish.search;

import java.util.List;

import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

/**
 * 
 * @author jedwards
 *
 */
public class ComparableWordTuple implements Comparable<ComparableWordTuple> {

	private final String word;

	private final List<Long> documentPositions;

	private final String documentName;
	
	private final int hashCode;

	/**
	 * 
	 * @param word
	 * @param documentPositions
	 * @param documentName
	 */
	public ComparableWordTuple(String word, List<Long> documentPositions, String documentName) {
		if (documentName == null || word == null || documentPositions == null || documentPositions.isEmpty()
				|| documentName.isEmpty() || word.isEmpty()) {
			StringBuilder strbld = new StringBuilder();
			if (documentName == null || documentName.isEmpty()) {
				strbld.append("Parameter 'documentName' cannot be null or empty. ");
			}
			if (word == null || word.isEmpty()) {
				strbld.append("Parameter 'word' cannot be null or empty. ");
			}
			if (documentPositions == null || documentPositions.isEmpty()) {
				strbld.append("Parameter 'count' cannot be less than '1'. ");
			}
			throw new IllegalArgumentException(strbld.toString());
		}
		
		this.word = word;
		this.documentPositions = documentPositions;
		this.documentName = documentName;
		this.hashCode = Hashing.murmur3_32().newHasher()
			.putBytes(documentName.getBytes())
			.putBytes(word.getBytes())
			.hash()
			.asInt();
	}

	/**
	 * 
	 * @return
	 */
	public String getDocumentName() {
		return documentName;
	}

	/**
	 * 
	 * @return
	 */
	public List<Long> getDocumentPositions() {
		return documentPositions;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getScore() {
		return (int) (Math.log(this.documentPositions.size() + 1) * 100);
	}

	/**
	 * 
	 * @return
	 */
	public Integer getCount() {
		return this.documentPositions.size();
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
	 */
	public int compareTo(ComparableWordTuple cwt) {
		int retval = 1;

		if (cwt != null) {
			retval = this.getScore() - cwt.getScore();
		}

		if (retval == 0) {
			retval = word.compareTo(cwt.getWord());
		}

		return retval;
	}

	/**
	 * 
	 */
	public boolean equals(Object obj) {
		boolean isEqual = false;

		if (obj != null && obj.getClass() == getClass()) {
			final ComparableWordTuple cwt = (ComparableWordTuple) obj;
			isEqual = (cwt.getDocumentName() == documentName && cwt.getWord() == word);
		}

		return isEqual;
	}

	/**
	 * 
	 */
	public String toString() {
		return String.format("documentName => %s | word => %s | count => %s", documentName, word,
				this.documentPositions.size());
	}

	/**
	 * 
	 */
	public int hashCode() {
		return hashCode;
	}
}
