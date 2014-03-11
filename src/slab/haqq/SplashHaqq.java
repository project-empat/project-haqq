package slab.haqq;

import slab.haqq.lib.GlobalController;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.TextView;

public class SplashHaqq extends Activity {
	TextView splashTag;
	TextView splashMsg;
	Handler handler;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_splash_haqq);
		new SplashTask().execute();
		// splashTag.setText(GlobalController.getInitTag());
		// splashMsg.setText(GlobalController.getInitMessage());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.splash_haqq, menu);
		return true;
	}

	public class SplashTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			splashTag = (TextView) findViewById(R.id.splashTag);
			splashMsg = (TextView) findViewById(R.id.splashMsg);
		};
		
		public void updateProgress(){
			publishProgress();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			GlobalController.init(this, SplashHaqq.this);
			return null;
		}
		
		protected void onProgressUpdate(Void... values) {
			String tag = "";
			String msg = "";
			tag = GlobalController.getInitTag();
			msg = GlobalController.getInitMessage();
			splashTag.setText(tag);
			splashMsg.setText(msg);
		};

		@Override
		protected void onPostExecute(Void result) {
			//splashUpdate.cancel(true);
			Intent intent = new Intent(SplashHaqq.this,
					HaqqMain.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY|Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		};
	};

}
