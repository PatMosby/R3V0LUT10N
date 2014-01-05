/*
 * Copyright (c) 2013 AG Softwaretechnik, University of Bremen, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package it_r3v.bibclient;

import it_r3v.bibclient.R;
import swp.bibcommon.Book;
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
 * Die StartActivity unserer App.
 *
 * @author D. Lüdemann
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

        // Long Click ermöglichen, zum anzeigen eines Buchs:
        final ListView listView = ((ListView) findViewById(R.id.listView1));

        listView.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(final AdapterView<?> arg0,
                    final View arg1, final int pos, final long id) {

                Log.v("long clicked", "pos" + " " + pos);
                // Wechsel zur ShowBookActivity, falls auf "hinzufügen"
                // geklickt wird.
                Intent intent = new Intent(getApplicationContext(),
                        ShowBookActivity.class);
                BookAdapter bookAdapter = (BookAdapter) listView.getAdapter();
                intent.putExtra(Book.class.getName(), bookAdapter.getItem(pos));
                Log.i(this.getClass().getName(), bookAdapter.getItem(pos)
                        .toString());
                startActivity(intent);
                return true;
            }
        });
    }
}
