package org.butterfish.search;

public class ComparableWordTuple implements Comparable<ComparableWordTuple> {

	private String documentName;

	private Long count;

	private String word;

	public ComparableWordTuple(String documentName, String word, Long count) {
		if (documentName == null || word == null || count < 1 || documentName.isEmpty() || word.isEmpty()) {
			StringBuilder strbld = new StringBuilder();
			if (documentName == null || documentName.isEmpty()) {
				strbld.append("Parameter 'documentName' cannot be null or empty. ");
			}
			if (word == null || word.isEmpty()) {
				strbld.append("Parameter 'word' cannot be null or empty. ");
			}
			if (count < 1) {
				strbld.append("Parameter 'count' cannot be less than '1'. ");
			}
			throw new IllegalArgumentException(strbld.toString());
		}
		this.documentName = documentName;
		this.word = word;
		this.count = count;
	}

	public String getDocumentName() {
		return documentName;
	}

	public Integer getScore() {
		return (int) (Math.log(count+1) * 100);
	}

	public Long getCount() {
		return count;
	}

	public String getWord() {
		return word;
	}

	public int compareTo(ComparableWordTuple cwt) {
		int retval = 1;

		if (cwt != null) {
			retval = this.getScore() - cwt.getScore();
		}

		return retval;
	}

	public boolean equals(Object obj) {
		boolean isEqual = false;

		if (obj != null && obj.getClass() == getClass()) {
			final ComparableWordTuple cwt = (ComparableWordTuple) obj;
			isEqual = (cwt.getDocumentName() == documentName && cwt.getWord() == word);
		}

		return isEqual;
	}

	public String toString() {
		return String.format("documentName => %s | word => %s | count => %s", documentName, word, count);
	}
}
