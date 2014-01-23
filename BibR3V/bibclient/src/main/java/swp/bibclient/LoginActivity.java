package swp.bibclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


/**
 * Die Activity für die Login-Funktion.
 * 
 * @author mosby259
 *
 */

public class LoginActivity extends Activity {
	
	/**
	 * Die Aktuelle Activity als Referenz für die OnClickListener.
	 */
	Activity activity = this;

	
	EditText username, password;
	TextView error;
	private String response;
	private String errorMessage;
	
	
	/**
	 * Wird aufgerufen, wenn die Activity zum ersten mal erstellt wird.
	 * 
	 * @param savedInstanceState
	 *            Speicher, falls die Activity neu aufgerufen wird, nachdem sie
	 *            beendet wurde.
	 * 
	 */
	
	public final void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(this.getClass().getName(), "onCreate");
		setContentView(R.layout.login_layout);
		
		username = (EditText) findViewById(R.id.editText_username);
		password = (EditText) findViewById(R.id.editText_password);
		error = (TextView) findViewById(R.id.textView_error);

		
		// OnClickListener für LoginButton.
		findViewById(R.id.button_login).setOnClickListener(new OnClickListener() {
		
			public final void onClick(final View arg0) {
				
			    /** 
			     * Aufgrund der neuen StrictGuard policy,  ist es nicht erlaubt lange Tasks auf dem Main UI thread
			     * 	auszuführen.
			     * 	Also erzeugen wir einen neuen thread in dem wir die Http-Operationen ausführen
			     */
			    new Thread(new Runnable() {

			     @Override
			     public void run() {
			      ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			      postParameters.add(new BasicNameValuePair("username",username.getText().toString()));
			      postParameters.add(new BasicNameValuePair("password",password.getText().toString()));

			      String response = null;
			      try {
			       response = LoginHttpClient.executeHttpPost("http://10.0.2.2:8080", postParameters);
			       String res = response.toString();
			       response = res.replaceAll("\\s+", "");

			      } catch (Exception e) {
			       e.printStackTrace();
			       errorMessage = e.getMessage();
			      }
			     }

			    }).start();
			    
			    /**
			     * Da wir im neuen Thread den Main Thread nicht updaten können, ist das Updaten
			     * ausserhalb des neuen Threads   
			     */
			    try {
			    	if (response.equals("1")) {
			    		error.setText("Correct Username and Password");
			    	} else {
			    		error.setText("Sorry!! Incorrect Username or Password");
			    	} if (null != errorMessage && !errorMessage.isEmpty()) {
			    		error.setText(errorMessage);
			    	}
			    } catch (Exception e) {
			    	error.setText(e.getMessage());
			    	}
			   }
		 });
		
		// OnClickListener für GastButton.
		findViewById(R.id.button_gastzugang).setOnClickListener(new OnClickListener() {
		
			public final void onClick(final View arg0) {
				
				Intent intent = new Intent(getApplicationContext(),
						MainActivityGuest.class);
				startActivity(intent);
			}
		});	

	}
}
