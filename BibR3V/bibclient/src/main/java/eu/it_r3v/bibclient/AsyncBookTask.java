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

package eu.it_r3v.bibclient;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import eu.it_r3v.bibclient.R;
import eu.it_r3v.Book;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Ein eigener AsyncTask für die MainAcivity zum holen und anzeigen der Bücher.
 *
 * Tutorials:
 * http://www.techrepublic.com/blog/app-builder/calling-restful-services
 * -from-your-android-app/1076
 * http://droid-blog.net/2011/05/25/how-to-serverkommunikation
 * -in-android-rest-json/
 * http://www.vogella.com/articles/AndroidJSON/article.html
 * http://www.vogella.com/articles/AndroidListView/article.html
 *
 * @author D. Lüdemann
 *
 */
public class AsyncBookTask extends AsyncTask<Void, Integer, List<Book>> {

	/**
	 * Diese Activity wird als Referenz gebraucht. Alternativ könnte dieser
	 * Asynctask als Klasse in der Acivity implementiert werden.
	 */
	private final Activity activity;

	/**
	 * @param activity
	 *            Eine Referenz auf die ausführende Activity.
	 */
	public AsyncBookTask(final Activity activity) {
		this.activity = activity;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected final List<Book> doInBackground(final Void... arg0) {
		List<Book> list;
		try {
			Network network = new Network();
			list = network.getBooks();
		} catch (ClientProtocolException e) {
			Log.i(this.getClass().toString(), e.getLocalizedMessage());
			// Ausgabe für den Nutzer bei einem Verbindungsproblem:
			onProgressUpdate(1);
			return null;
		} catch (IOException e) {
			// Ausgabe für den Nutzer bei einem Verbindungsproblem:
			onProgressUpdate(1);
			Log.i(this.getClass().toString(), e.getLocalizedMessage());
			return null;
		} catch (ClassCastException e) {
			Log.e(this.getClass().toString(), e.getLocalizedMessage());
			// Ausgabe für den Nutzer bei unerwartetem
			// Datenkonvertierungsproblem:
			onProgressUpdate(2);
			return null;
		}
		Log.i(AsyncBookTask.class.getName(), "Get all books.");
		for (Book book : list) {
			Log.i(AsyncBookTask.class.getName(), book.toString());
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected final void onPostExecute(final List<Book> results) {
		Log.i(AsyncBookTask.class.getName(), "Begin onPostExecute.");

		if (results != null) {
			ListView listView = (ListView) activity
					.findViewById(R.id.listView1);
			// Benutze den eigenen BookAdapter um die Listview zu füllen.
			BookAdapter adapter = new BookAdapter(activity, R.layout.rowlayout,
					android.R.id.text1, results);
			listView.setAdapter(adapter);
		} else {
			Log.e(AsyncBookTask.class.getName(),
					"No results returned from the service site.");
		}

	}

	/**
	 * On Progress Update kann in einem AsyncTask genutzt werden um
	 * Statusupdates anzuzeigen. Siehe:
	 * http://developer.android.com/reference/android/os/AsyncTask.html.
	 *
	 * @param integers
	 *            Eine Code für den Zustand.
	 */
	protected final void onProgressUpdate(final Integer integers) {
		Log.i(AsyncBookTask.class.getName(), "Show progress update.");
		// Toast und andere UserInterface Interaktionen können nicht im
		// Asynctask aufgerufen werden. Stattdessen benutzen wir
		// runOnUIThread auf der Activity.
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				int error;
				if (integers == 1) {
					error = R.string.error_connection;
				} else {
					error = R.string.error_data;
				}
				// Zeige eine Fehlermeldung via Toast.
				Toast.makeText(activity.getBaseContext(), error,
						Toast.LENGTH_SHORT).show();
			}
		});
	}
}
