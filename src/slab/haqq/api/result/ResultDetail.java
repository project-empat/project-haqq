package slab.haqq.api.result;

import slab.haqq.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ResultDetail extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result_detail);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.result_detail, menu);
		return true;
	}

}
