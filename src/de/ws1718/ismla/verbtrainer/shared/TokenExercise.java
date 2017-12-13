package de.ws1718.ismla.verbtrainer.shared;

import java.io.Serializable;

public class TokenExercise implements Serializable{

	private String token;
	private boolean isVerb;
	
	public TokenExercise() {
		
	}
	
	public TokenExercise(String token, boolean isVerb) {
		super();
		this.token = token;
		this.isVerb = isVerb;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isVerb() {
		return isVerb;
	}

	public void setVerb(boolean isVerb) {
		this.isVerb = isVerb;
	}

	
	
	
}
