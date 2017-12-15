package de.ws1718.ismla.verbtrainer.server;

import de.ws1718.ismla.verbtrainer.client.GreetingService;
import de.ws1718.ismla.verbtrainer.shared.FieldVerifier;
import de.ws1718.ismla.verbtrainer.shared.Submitted;
import de.ws1718.ismla.verbtrainer.shared.TokenExercise;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {

	@Override
	public List<TokenExercise> getTokensForExercise(String text) {
		// TODO Auto-generated method stub
		
		List<TokenExercise> rval = new ArrayList<TokenExercise>();
		
		InputStream resourceStream = getServletContext().getResourceAsStream("/es-verb-forms.tsv");
		BufferedReader reader = new BufferedReader(new InputStreamReader(resourceStream));

		HashMap<String, String> verbMapping = new HashMap<>();

		try {

			// read file into map
			String line;
			while ((line = reader.readLine()) != null) {
				String[] verbs = line.split("\t");

				verbMapping.put(verbs[0], verbs[1]);

			}
			
			String[] words = text.split(" ");

			for (String w : words) {
				if (verbMapping.keySet().contains(w)) {

					String hint = verbMapping.get(w);
					rval.add(new TokenExercise(w, hint, true));
					System.out.println(hint);
					
				} else {
					
					rval.add(new TokenExercise(w, "", false));
					System.out.println("nicht vorhanden...");
					
				}
			}
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rval;
	}

	@Override
	public List<Submitted> getResultsForSubmission(List<Submitted> submittedResults) {
		// TODO Auto-generated method stub
		for(Submitted submitted : submittedResults){
			String targetAnswer = submitted.getOriginalToken();
			String answer = submitted.getSubmitedToken();
			if(targetAnswer.trim().equals(answer.trim())){
				submitted.setCorrectSubmission(true);
			}
		}
		return submittedResults;
	}


	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html
	 *            the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}

	@Override
	public String greetServer(String name) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

}
