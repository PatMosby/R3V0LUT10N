package swp.bibclient;

import swp.bibcommon.Medium;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * Diese Activity wird benutzt um ein Medium im Detail anzuzeigen.
 *
 * @author Patrick Damrow
 *
 */
public class ShowMediumActivity extends Activity {

	/**
	 * Hier wird das Layout mit den aktuellen Daten befüllt.
	 * onResume, damit der Screen bei jedem Aufruf das aktuelle Medium anzeigt.
	 *
	 */
	@Override
	protected final void onResume() {

		super.onResume();

		Bundle bundle = getIntent().getExtras();
		Medium mediumFromIntent = null;
		try {
			//Wieder das Medium aus dem Intent auspacken
			mediumFromIntent = (Medium) bundle
					.getSerializable(Medium.class.getName());
		} catch (Exception e) {
			Log.e(this.getClass().getName(), "Error while reading Medium");
			Log.e(this.getClass().getName(), e.toString());
		}
		if (mediumFromIntent != null) {

//			TextView textView1 = (TextView) findViewById(R.id.textView1);
//			textView1.setText(mediumFromIntent.getAuthors());

			TextView textView2 = (TextView) findViewById(R.id.textView2);
			textView2.setText(mediumFromIntent.getTitle());

//			TextView textView3 = (TextView) findViewById(R.id.textView3);
//			textView3.setText(mediumFromIntent.getIndustrialIdentifier());

			TextView textView4 = (TextView) findViewById(R.id.textView4);
			textView4.setText(String.valueOf(mediumFromIntent.getId()));
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected final void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_medium_layout);
	}
}
