package de.ws1718.ismla.verbtrainer.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.ws1718.ismla.verbtrainer.shared.Submitted;
import de.ws1718.ismla.verbtrainer.shared.TokenExercise;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;

	List<Submitted> getResultsForSubmission(List<Submitted> submittedResults);

	List<TokenExercise> getTokensForExercise(String text);
}
