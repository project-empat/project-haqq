package slab.haqq;

import slab.haqq.lib.GlobalController;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * @author rasxen
 * <p><h1>Splash Activity</h1></p>
 * <p>
 * Loading the XML Resource before the main application can start.
 * </p>
 */
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

	/**
	 * @author rasxen
	 * <p><h2>Splash Async Task</h2></p>
	 * <p>The AsyncTask that call the loader and update splash view
	 * <br>Provide public method to call publishProgress() protected method 
	 * </p>
	 */
	public class SplashTask extends AsyncTask<Void, Void, Void> {
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			splashTag = (TextView) findViewById(R.id.splashTag);
			splashMsg = (TextView) findViewById(R.id.splashMsg);
		};
		
		/**
		 * Provide access to protected publishProgress() method
		 */
		public void updateProgress(){
			publishProgress();
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
		 */
		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			GlobalController.init(this, SplashHaqq.this);
			return null;
		}
		
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onProgressUpdate(java.lang.Object[])
		 */
		@Override
		protected void onProgressUpdate(Void... values) {
			String tag = "";
			String msg = "";
			tag = GlobalController.getInitTag();
			msg = GlobalController.getInitMessage();
			splashTag.setText(tag);
			splashMsg.setText(msg);
		};

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Void result) {
			//splashUpdate.cancel(true);
			Intent intent = new Intent(SplashHaqq.this,
					HaqqMain.class);
			startActivity(intent);
		};
	};

}
