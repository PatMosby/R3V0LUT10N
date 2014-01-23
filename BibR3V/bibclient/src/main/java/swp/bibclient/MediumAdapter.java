package swp.bibclient;

import java.util.List;

import swp.bibcommon.Medium;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Dieser MediumAdapter ersetzt den normalen ArrayAdapter. Der ArrayAdapter würde
 * in getView nur toString() auf dem Medium aufrufen, wenn wir Medium als Typ für
 * ein ArrayAdapter<T> verwenden. Mit unserem eigenem Adapter können wir selber
 * bestimmen, wie wir aus dem Meium ein Text für unsere Listview machen.
 *
 * Tutorial:
 * http://stackoverflow.com/questions/2265661/how-to-use-arrayadaptermyclass
 *
 * @author Patrick Damrow
 * 
 */
public class MediumAdapter extends ArrayAdapter<Medium> {

	/**
	 * Die Medien, als Liste.
	 */
	private final List<Medium> items;

	/**
	 * Der aktuelle Context.
	 */
	private final Context context;

	/**
	 * Die Klasse für unseren eigenen MediumAdapter.
	 *
	 * @param context
	 *            Der aktuelle Context.
	 * @param resource
	 *            Resource ID für ein Layout.
	 * @param textViewResourceId
	 *            Die ID für den Textview, in der sich das Layout befindet.
	 * @param books
	 *            Die Medien, mit denen wir arbeiten wollen.
	 */
	public MediumAdapter(final Context context, final int resource,
			final int textViewResourceId, final List<Medium> mediums) {
		super(context, resource, textViewResourceId, mediums);
		this.context = context;
		this.items = mediums;
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
		textView.setText(Html.fromHtml("<b>" + items.get(position).getCategories()
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
