package org.butterfish.search;

public class ComparableWordTuple implements Comparable<ComparableWordTuple> {

	private String documentName;

	private long count;

	private String word;

	public ComparableWordTuple(String documentName, String word, long count) {
		if (documentName == null || word == null || count < 1 || "".equals(documentName) || "".equals(word)) {
			StringBuilder strbld = new StringBuilder();
			if (documentName == null || "".equals(documentName)) {
				strbld.append("Parameter 'documentName' cannot be null or empty. ");
			}
			if (word == null || "".equals(word)) {
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

	public double getScore() {
		return Math.log(count);
	}

	public long getCount() {
		return count;
	}

	public String getWord() {
		return word;
	}

	public int compareTo(ComparableWordTuple cwt) {
		int retval = 1;
		
		if (cwt != null) {
			retval = (int) (this.getScore() - cwt.getScore()) * 1000;
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
}
