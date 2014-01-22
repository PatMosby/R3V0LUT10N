package swp.bibclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class LoginActivity extends Activity {
	
	/**
	 * Die Aktuelle Activity als Referenz für die OnClickListener.
	 */
	Activity activity = this;

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
	
		// OnClickListener für LoginButton.
		findViewById(R.id.button_login).setOnClickListener(new OnClickListener() {
		
			public final void onClick(final View arg0) {
				
				Intent intent = new Intent(getApplicationContext(),
						BooksActivity.class);
				startActivity(intent);
			}
		});	
		
		// OnClickListener für GastButton.
		findViewById(R.id.button_gastzugang).setOnClickListener(new OnClickListener() {
		
			public final void onClick(final View arg0) {
				
				Intent intent = new Intent(getApplicationContext(),
						BooksActivity.class);
				startActivity(intent);
			}
		});	

	}
}
