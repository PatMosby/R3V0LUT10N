package swp.bibclient;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import swp.bibcommon.Medium;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Ein eigener AsyncTask für die MainAcivity zum holen und anzeigen der Medien.
 *
 *@author Patrick Damrow
 */
public class AsyncMediumTask extends AsyncTask<Void, Integer, List<Medium>> {

	/**
	 * Diese Activity wird als Referenz gebraucht. Alternativ könnte dieser
	 * Asynctask als Klasse in der Acivity implementiert werden.
	 */
	private final Activity activity;

	/**
	 * @param activity
	 *            Eine Referenz auf die ausführende Activity.
	 */
	public AsyncMediumTask(final Activity activity) {
		this.activity = activity;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected final List<Medium> doInBackground(final Void... arg0) {
		List<Medium> list;
		try {
			Network network = new Network();
			list = network.getMediums();
		} catch (ClientProtocolException e) {
			Log.i(this.getClass().toString(), e.getLocalizedMessage());
			// Ausgabe für den Nutzer bei einem Verbindungsproblem:
			onProgressUpdate(1);
			return null;
		} catch (ClassCastException e) {
			Log.e(this.getClass().toString(), e.getLocalizedMessage());
			// Ausgabe für den Nutzer bei unerwartetem
			// Datenkonvertierungsproblem:
			onProgressUpdate(2);
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i(AsyncMediumTask.class.getName(), "Get all mediums.");
		for (Medium medium : list) {
			Log.i(AsyncMediumTask.class.getName(), medium.toString());
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected final void onPostExecute(final List<Medium> results) {
		Log.i(AsyncMediumTask.class.getName(), "Begin onPostExecute.");

		if (results != null) {
			ListView listView = (ListView) activity
					.findViewById(R.id.listView1);
			// Benutze den eigenen MediumAdapter um die Listview zu füllen.
			MediumAdapter adapter = new MediumAdapter(activity, R.layout.rowlayout,
					android.R.id.text1, results);
			listView.setAdapter(adapter);
		} else {
			Log.e(AsyncMediumTask.class.getName(),
					"No results returned from the service site.");
		}

	}

	/**
	 * On Progress Update kann in einem AsyncTask genutzt werden um
	 * Statusupdates anzuzeigen.
	 *
	 * @param integers
	 *            Eine Code für den Zustand.
	 */
	
	//http://developer.android.com/reference/android/os/AsyncTask.html.
	protected final void onProgressUpdate(final Integer integers) {
		Log.i(AsyncMediumTask.class.getName(), "Show progress update.");
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
