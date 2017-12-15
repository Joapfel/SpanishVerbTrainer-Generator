package de.ws1718.ismla.verbtrainer.shared;

import java.io.Serializable;

public class TokenExercise implements Serializable{

	private String originalToken;
	private String verbLemma;
	private boolean isVerb;
	
	public TokenExercise() {
		
	}
	
	public TokenExercise(String originalToken, String verbLemma, boolean isVerb) {
		super();
		this.originalToken = originalToken;
		this.verbLemma = verbLemma;
		this.isVerb = isVerb;
	}

	public String getOriginalToken() {
		return originalToken;
	}

	public void setOriginalToken(String originalToken) {
		this.originalToken = originalToken;
	}

	public String getVerbLemma() {
		return verbLemma;
	}

	public void setVerbLemma(String verbLemma) {
		this.verbLemma = verbLemma;
	}

	public boolean isVerb() {
		return isVerb;
	}

	public void setVerb(boolean isVerb) {
		this.isVerb = isVerb;
	}
	
	


	

	
	
	
}
