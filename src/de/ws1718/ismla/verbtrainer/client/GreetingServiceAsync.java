package de.ws1718.ismla.verbtrainer.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.ws1718.ismla.verbtrainer.shared.Submitted;
import de.ws1718.ismla.verbtrainer.shared.TokenExercise;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;
	
	void getResultsForSubmission(List<Submitted> submittedResults, AsyncCallback<List<Submitted>> callback);
	void getTokensForExercise(String text, AsyncCallback<List<TokenExercise>> callback);
}
