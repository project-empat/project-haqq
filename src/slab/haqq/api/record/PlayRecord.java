package slab.haqq.api.record;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import slab.haqq.R;
import slab.haqq.lib.GlobalController;
import slab.haqq.lib.adapter.model.Record;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class PlayRecord extends Activity {
	Button baseEvalBtn, fullEvalBtn;
	TextView timestamp, name, sura;
	ImageButton playBtn, pauseBtn, stopBtn;
	Record record;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_record);

		record = getIntent().getExtras().getParcelable("recordParsel");

		setTitle(record.toString());

		sura = (TextView) findViewById(R.id.suraNumberRecord);
		name = (TextView) findViewById(R.id.recordName);
		timestamp = (TextView) findViewById(R.id.timeStampRecord);

		playBtn = (ImageButton) findViewById(R.id.playButton);
		pauseBtn = (ImageButton) findViewById(R.id.pauseButton);
		stopBtn = (ImageButton) findViewById(R.id.stopButton);
		baseEvalBtn = (Button) findViewById(R.id.baseEvalBtn);
		fullEvalBtn = (Button) findViewById(R.id.fullEvalBtn);

		playBtn.setOnClickListener(playListener);
		pauseBtn.setOnClickListener(pauseListener);
		stopBtn.setOnClickListener(stopListener);
		baseEvalBtn.setOnClickListener(baseListener);
		fullEvalBtn.setOnClickListener(fullListener);

		updateView();
	}

	/**
	 * TODO : Documentation
	 */
	@SuppressLint("SimpleDateFormat")
	public void updateView() {
		sura.setText("Q.S. "
				+ GlobalController.SuraMap.get(record.getSuraId()).getName()
				+ " : " + String.valueOf(record.getAyaNumber()));
		Format format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date date = new Date(record.getTimeStamp());
		timestamp.setText(format.format(date));
		name.setText(record.toString());
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.play_record, menu);
		return true;
	}

	/**
	 * TODO : Documentation
	 */
	public OnClickListener playListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};

	/**
	 * TODO : Documentation
	 */
	public OnClickListener pauseListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};

	/**
	 * TODO : Documentation
	 */
	public OnClickListener stopListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};

	/**
	 * TODO : Documentation
	 */
	public OnClickListener baseListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			GlobalController.resultController.addScore(
					PlayRecord.this,
					record.getPrefix() + "_"
							+ String.valueOf(record.getTimeStamp()), 0.0, 0.0,
					0.0, 0.0, "");
		}
	};

	/**
	 * TODO : Documentation
	 */
	public OnClickListener fullListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			GlobalController.resultController.addScore(
					PlayRecord.this,
					record.getPrefix() + "_"
							+ String.valueOf(record.getTimeStamp()), 0.0, 0.0,
					0.0, 0.0,"");
		}
	};
}
