package de.ws1718.ismla.verbtrainer.server;

import de.ws1718.ismla.verbtrainer.client.GreetingService;
import de.ws1718.ismla.verbtrainer.shared.FieldVerifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {

	public String greetServer(String input) throws IllegalArgumentException {
		
		System.out.println("HALLO WELT");
		
		InputStream resourceStream = getServletContext().getResourceAsStream("/es-verb-forms.tsv");
		BufferedReader reader = new BufferedReader(new InputStreamReader(resourceStream));
		
		HashMap<String, String> verbMapping = new HashMap<>();
		
		try {
			
			//read file into map
			String line;
			while ((line = reader.readLine()) != null){
				String[] verbs = line.split("\t");
				
				verbMapping.put(verbs[0], verbs[1]);
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
//		for(String s : verbMapping.keySet()){
//			
//			String target = verbMapping.get(s);
//			
//			System.out.println(s + " -> " + target);
//		}
		
		
		String[] words = input.split(" ");
		for(String w : words){
			if(verbMapping.keySet().contains(w)){
				
				String hint = verbMapping.get(w);
				
				System.out.println(hint);
			}else{
				System.out.println("nicht vorhanden...");
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException("Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo + ".<br><br>It looks like you are using:<br>"
				+ userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
}
