package swp.bibclient;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * Diese Activity zeigt das Impressum der Oberschule Rockwinkel an. 
 * 
 * @author Patrick Damrow
 *
 */

public class ShowImpressumActivity extends Activity {
	
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
		setContentView(R.layout.show_impressum_layout);
	};
	
	//TODO BackButton abfangen und auf MainActivity verweisen.

}
