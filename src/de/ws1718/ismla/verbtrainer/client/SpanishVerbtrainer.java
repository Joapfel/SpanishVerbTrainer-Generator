package de.ws1718.ismla.verbtrainer.client;

import de.ws1718.ismla.verbtrainer.shared.FieldVerifier;
import de.ws1718.ismla.verbtrainer.shared.Submitted;
import de.ws1718.ismla.verbtrainer.shared.TokenExercise;
import javafx.beans.binding.StringBinding;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SpanishVerbtrainer implements EntryPoint {

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync client2Server = GWT.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		final TextArea textArea = new TextArea();
		textArea.setStyleName("textArea");
		
		final HTMLPanel exerciseContainer = new HTMLPanel("");
		exerciseContainer.setStyleName("exerciseContainer");
		
		final Button sendButton = new Button("Send");
		sendButton.addStyleName("sendButton");
		sendButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				String input = textArea.getText();
				
				client2Server.getTokensForExercise(input, new AsyncCallback<List<TokenExercise>>() {
					@Override
					public void onSuccess(List<TokenExercise> result) {
						// TODO Auto-generated method stub
//						Window.alert("SUCCESS");
						
						//hide the button and the textbox
						sendButton.setVisible(false);
						textArea.setVisible(false);
						textArea.setText("");
						exerciseContainer.clear();
						exerciseContainer.setVisible(true);
						

						final List<TextBox> submissions = new ArrayList<>();
						final List<TokenExercise> correctVerbForms = new ArrayList<>();
						
						//create exercises
						for(TokenExercise tokenEx : result){	
	
							if(tokenEx.isVerb()){
								TextBox tb = new TextBox();
								submissions.add(tb);
								//store the correct forms for later
								correctVerbForms.add(tokenEx);
								exerciseContainer.add(tb);
								exerciseContainer.add(new InlineHTML("<span id='token_hint'>(" + tokenEx.getVerbLemma() + ")</span>"));
							}else{
								exerciseContainer.add(new InlineHTML("<span id='token'> " + tokenEx.getOriginalToken() + " </span>"));
							}
						}
						
						//create submit button
						final Button submitButton = new Button("Submit");
						submitButton.addStyleName("submitButton");
						submitButton.addClickHandler(new ClickHandler() {
							
							@Override
							public void onClick(ClickEvent event) {
								// TODO Auto-generated method stub
								final List<Submitted> submissionsCheck = new ArrayList<>();
								
								StringBuilder sb = new StringBuilder();
								for(int i = 0; i < submissions.size(); i++){
									TextBox tb = submissions.get(i);
									String correctAnswer = correctVerbForms.get(i).getOriginalToken();
									Submitted submittedToken = new Submitted(tb.getText().trim(), correctAnswer);
									submissionsCheck.add(submittedToken);
									
									sb.append(tb.getText() +  " ");
									
									Window.alert(correctAnswer);
								}
//								Window.alert(sb.toString());
								
								//check the submissions
								client2Server.getResultsForSubmission(submissionsCheck, new AsyncCallback<List<Submitted>>() {
									
									@Override
									public void onSuccess(List<Submitted> result) {
										// TODO Auto-generated method stub
										for(int i = 0; i < result.size(); i++){
											if(result.get(i).isCorrectSubmission()){
												//mark it as correct
												TextBox tb = submissions.get(i);
												tb.setText(result.get(i).getSubmitedToken().toUpperCase());
											}else{
												//mark it as wrong
												TextBox tb = submissions.get(i);
												tb.setText(result.get(i).getSubmitedToken().toLowerCase() + " -> " + result.get(i).getOriginalToken());
											}
										}
										
										//hide the button
										submitButton.setVisible(false);
//										
										//create a reset button
										final Button resetButton = new Button("Reset");
										RootPanel.get("resetButton").add(resetButton);
										resetButton.addClickHandler(new ClickHandler() {
											
											@Override
											public void onClick(ClickEvent event) {
												// TODO Auto-generated method stub
												resetButton.setVisible(false);
												exerciseContainer.setVisible(false);
												textArea.setVisible(true);
												sendButton.setVisible(true);
											}
										});
									}
									
									@Override
									public void onFailure(Throwable caught) {
										// TODO Auto-generated method stub
										Window.alert("FAILED TO CHECK THE SUBMISSION(S)");
									}
								});
							}
						});
						
						RootPanel.get("submitButton").add(submitButton);
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						Window.alert("FAILED TO PROCESS THE TEXT");
					}
				});
				
			}
		});
		
		

		RootPanel.get("textAreaContainer").add(textArea);
		RootPanel.get("textAreaContainer").add(exerciseContainer);
		RootPanel.get("sendButton").add(sendButton);
		


	}
}
