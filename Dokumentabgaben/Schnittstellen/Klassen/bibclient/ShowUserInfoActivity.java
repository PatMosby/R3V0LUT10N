package swp.bibclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Diese Activity zeigt die persönlichen Daten des angemeldeten Nutzers an.
 * 
 * @author Patrick Damrow
 *
 */

public class ShowUserInfoActivity extends Activity{
	
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
	}

}
