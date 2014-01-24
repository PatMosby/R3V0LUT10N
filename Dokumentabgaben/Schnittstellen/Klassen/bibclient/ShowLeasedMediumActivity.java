package swp.bibclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

 	/*
 	 * Mit dieser Activity werden alle Medien eines Nutzers angezeigt,
 	 * die er ausgeliehen hat
 	 */
public class ShowLeasedMediumActivity extends Activity{
	
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
    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(this.getClass().getName(), "onCreate");
        setContentView(R.layout.show_leased_medium_layout);
    }
}
