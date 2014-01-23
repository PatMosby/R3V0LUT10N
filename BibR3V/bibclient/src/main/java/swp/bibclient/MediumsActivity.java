package swp.bibclient;

import swp.bibcommon.Medium;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

/**
 * Die Activity um die Medien anzeigen zu lassen in unserer App.
 *
 * @author Patrick Damrow
 *
 */
public class MediumsActivity extends Activity {

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
        setContentView(R.layout.mediums_layout);

        // OnClickListener für Medien anzeigen.
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
                new AsyncMediumTask(activity).execute();
            }

        });

        // Long Click ermöglichen, zum anzeigen eines Mediums:
        final ListView listView = ((ListView) findViewById(R.id.listView1));

        listView.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(final AdapterView<?> arg0,
                    final View arg1, final int pos, final long id) {

                Log.v("long clicked", "pos" + " " + pos);
                // Wechsel zur ShowMediumActivity, falls auf "hinzufügen"
                // geklickt wird.
                Intent intent = new Intent(getApplicationContext(),
                        ShowMediumActivity.class);
                MediumAdapter mediumAdapter = (MediumAdapter) listView.getAdapter();
                intent.putExtra(Medium.class.getName(), mediumAdapter.getItem(pos));
                Log.i(this.getClass().getName(), mediumAdapter.getItem(pos)
                        .toString());
                startActivity(intent);
                return true;
            }
        });
    }
}
