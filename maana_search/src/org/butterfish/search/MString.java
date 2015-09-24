package org.butterfish.search;

import com.google.common.hash.Hashing;

public class MString {
	
	private final String string;
	private final int hashCode;
	
	public MString(String string) {
		this.string = string;
		this.hashCode = Math.abs(
				Hashing.murmur3_32()
				.hashBytes(string.getBytes())
				.asInt());
	}
	
	public boolean equals(Object obj) {
		boolean isEqual = false;
		
		if (obj != null && obj.getClass() == getClass()) {
			final MString mstr = (MString) obj;
			isEqual = string.equals(mstr.toString());
		}
		
		return isEqual;
	}
	
	public int hashCode() {
		return hashCode;
	}
	
	public String toString() {
		return string;
	}

}
