package de.ws1718.ismla.verbtrainer.client;

import de.ws1718.ismla.verbtrainer.shared.FieldVerifier;
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

						final List<TextBox> submissions = new ArrayList<>();
						
						//create exercises
						for(TokenExercise tokenEx : result){	
							
							if(tokenEx.isVerb()){
								TextBox tb = new TextBox();
								submissions.add(tb);
								exerciseContainer.add(tb);
								exerciseContainer.add(new InlineHTML("<span id='token_hint'>(" + tokenEx.getToken() + ")</span>"));
							}else{
								exerciseContainer.add(new InlineHTML("<span id='token'> " + tokenEx.getToken() + " </span>"));
							}
						}
						
						//create submit button
						Button submitButton = new Button("Submit");
						submitButton.addStyleName("submitButton");
						submitButton.addClickHandler(new ClickHandler() {
							
							@Override
							public void onClick(ClickEvent event) {
								// TODO Auto-generated method stub
								StringBuilder sb = new StringBuilder();
								for(TextBox tb : submissions){
									sb.append(tb.getText());
								}
								Window.alert(sb.toString());
							}
						});
						
						RootPanel.get("exercise_view").add(submitButton);
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						Window.alert("FAILED");
					}
				});
				
			}
		});
		
		

		RootPanel.get("textAreaContainer").add(textArea);
		RootPanel.get("sendButton").add(sendButton);
		RootPanel.get("exercise_view").add(exerciseContainer);


	}
}
