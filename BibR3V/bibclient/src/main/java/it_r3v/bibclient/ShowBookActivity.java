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
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * Diese Activity wird benutzt um ein Buch im Detail anzuzeigen.
 *
 * @author D. Lüdemann
 *
 */
public class ShowBookActivity extends Activity {

	/**
	 * Hier wird das Layout mit den aktuellen Daten befüllt.
	 * onResume, damit der Screen bei jedem Aufruf das aktuelle Buch anzeigt.
	 *
	 */
	@Override
	protected final void onResume() {

		super.onResume();

		Bundle bundle = getIntent().getExtras();
		Book bookFromIntent = null;
		try {
			//Wieder das Buch aus dem Intent auspacken
			bookFromIntent = (Book) bundle
					.getSerializable(Book.class.getName());
		} catch (Exception e) {
			Log.e(this.getClass().getName(), "Error while reading book");
			Log.e(this.getClass().getName(), e.toString());
		}
		if (bookFromIntent != null) {

			TextView textView1 = (TextView) findViewById(R.id.textView1);
			textView1.setText(bookFromIntent.getAuthors());

			TextView textView2 = (TextView) findViewById(R.id.textView2);
			textView2.setText(bookFromIntent.getTitle());

			TextView textView3 = (TextView) findViewById(R.id.textView3);
			textView3.setText(bookFromIntent.getIndustrialIdentifier());

			TextView textView4 = (TextView) findViewById(R.id.textView4);
			textView4.setText(String.valueOf(bookFromIntent.getId()));
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected final void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_book_layout);
	}
}
