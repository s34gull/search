package org.butterfish.search;

public class ComparableWordTuple implements Comparable<ComparableWordTuple> {

	private String documentName;
	
	private int count;
	
	private String word; 
	
	public ComparableWordTuple(String documentName, String word, int count) {
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

	public int getCount() {
		return count;
	}
	
	public String getWord() {
		return word;
	}
	
	public int compareTo(ComparableWordTuple t) {
		return (int)(this.getScore() - t.getScore())*1000;
	}
}
