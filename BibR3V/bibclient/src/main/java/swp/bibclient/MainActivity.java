package swp.bibclient;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Die StartActivity unserer App.
 *
 * @author Patrick Damrow
 *
 */
public class MainActivity extends Activity {

    /**
     * Die aktuelle Activity als Referenz für die OnClickListener.
     */
    private final Activity activity = this;

    /**
     * Wird aufgerufen, wenn die Activity zum ersten Mal erstellt wird.
     *
     *     * @param savedInstanceState
     *            Speicher, falls die Activity neu aufgerufen wird, nachdem sie
     *            beendet wurde.
     *
     */
    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(this.getClass().getName(), "onCreate");
        setContentView(R.layout.bib_main_layout);

        // OnClickListener für Bücher anzeigen.
        findViewById(R.id.button1).setOnClickListener(new OnClickListener() {

            /*
             * (non-Javadoc)
             *
             * @see android.view.View.OnClickListener#onClick(android.view.View)
             */
            @Override
            public final void onClick(final View arg0) {
                // Button b = (Button) findViewById(R.id.button1);
                // b.setClickable(false);
                new AsyncBookTask(activity).execute();
            }

        });
    }
}
