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

import java.util.List;

import eu.it_r3v.bibcommon.Book;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Dieser BookAdapter ersetzt den normalen ArrayAdapter. Der ArrayAdapter würde
 * in getView nur toString() auf dem Book aufrufen, wenn wir Book als Typ für
 * ein ArrayAdapter<T> verwenden. Mit unserem eigenem Adapter können wir selber
 * bestimmen, wie wir aus dem Book ein Text für unsere Listview machen.
 *
 * Tutorial:
 * http://stackoverflow.com/questions/2265661/how-to-use-arrayadaptermyclass
 *
 * @author D. Lüdemann
 * 
 */
public class BookAdapter extends ArrayAdapter<Book> {

	/**
	 * Die Bücher, als Liste.
	 */
	private final List<Book> items;

	/**
	 * Der aktuelle Context.
	 */
	private final Context context;

	/**
	 * Die Klasse für unseren eigenen BookAdapter.
	 *
	 * @param context
	 *            Der aktuelle Context.
	 * @param resource
	 *            Resource ID für ein Layout.
	 * @param textViewResourceId
	 *            Die ID für den Textview, in der sich das Layout befindet.
	 * @param books
	 *            Die Bücker, mit den wir arbeiten wollen.
	 */
	public BookAdapter(final Context context, final int resource,
			final int textViewResourceId, final List<Book> books) {
		super(context, resource, textViewResourceId, books);
		this.context = context;
		this.items = books;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public final View getView(final int position, final View view,
			final ViewGroup parent) {

		View myView = view;
		// So bekommen wir eine View, wenn keine übergeben wird:
		if (myView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			myView = inflater.inflate(R.layout.rowlayout, parent, false);
		}

		TextView textView = (TextView) myView.findViewById(R.id.label);

		// Wir nutzen HTML.fromHtml um HTML-Tags, wie <b> im String zu
		// interpretieren.
		textView.setText(Html.fromHtml("<b>" + items.get(position).getAuthors()
				+ "</b>: \"" + items.get(position).getTitle() + "\""));

		/*
		 * Hier könnte man ein anderes Icon als ein Buch setzen. Beispielsweise
		 * eines für Paper;
		 */
		// ImageView imageView = (ImageView)
		// rowView.findViewById(R.id.bookicon);
		// imageView.setImageResource(R.drawable.book);

		return myView;
	}
}
