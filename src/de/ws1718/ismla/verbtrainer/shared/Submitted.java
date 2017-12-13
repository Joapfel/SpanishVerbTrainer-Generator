package de.ws1718.ismla.verbtrainer.shared;

import java.io.Serializable;

public class Submitted implements Serializable{

	private String submitedToken;
	private String originalToken;
	private boolean correctSubmission;
	
	public Submitted() {
		
	}

	public Submitted(String submitedToken, String originalToken, boolean correctSubmission) {
		super();
		this.submitedToken = submitedToken;
		this.originalToken = originalToken;
		this.correctSubmission = correctSubmission;
	}

	public String getSubmitedToken() {
		return submitedToken;
	}

	public void setSubmitedToken(String submitedToken) {
		this.submitedToken = submitedToken;
	}

	public String getOriginalToken() {
		return originalToken;
	}

	public void setOriginalToken(String originalToken) {
		this.originalToken = originalToken;
	}

	public boolean isCorrectSubmission() {
		return correctSubmission;
	}

	public void setCorrectSubmission(boolean correctSubmission) {
		this.correctSubmission = correctSubmission;
	}
	
	
	
}
