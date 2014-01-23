package swp.bibclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ShowImpressumActivity extends Activity {
	
	Activity activity = this;
	
	public final void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(this.getClass().getName(), "onCreate");
		setContentView(R.layout.show_impressum_layout);
	};
	
	//TODO BackButton abfangen und auf MainActivity verweisen.

}
