package slab.haqq;

import slab.haqq.lib.GlobalController;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.TextView;

public class SplashHaqq extends Activity {
	private final Runnable init = new Runnable() {
		public void run() {
			GlobalController.init(SplashHaqq.this);
			handler.removeCallbacks(init);
		}
	};

	private final Runnable updateGui = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String tag = "";
			String msg = "";
			while (true) {
				tag = GlobalController.getInitTag();
				msg = GlobalController.getInitMessage();

				splashTag.setText(tag);
				splashMsg.setText(msg);

				if (GlobalController.getInitCode() == GlobalController.FINISHING_CODE) {
					break;
				}
			}
			handler.removeCallbacks(updateGui);
		}
	};

	TextView splashTag;
	TextView splashMsg;
	Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_splash_haqq);

		splashTag = (TextView) findViewById(R.id.splashTag);
		splashMsg = (TextView) findViewById(R.id.splashMsg);

		splashTag.setText(GlobalController.getInitTag());
		splashMsg.setText(GlobalController.getInitMessage());

		handler = new Handler();
		handler.post(init);

		handler.post(updateGui);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SplashHaqq.this, HaqqMain.class);
				startActivity(intent);
				finish();
			}
		}, GlobalController.SPLASH_TIME_OUT);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.splash_haqq, menu);
		return true;
	}

}
