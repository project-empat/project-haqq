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
import android.widget.TextView;

public class PlayRecord extends Activity {
	Button playBtn, pauseBtn, stopBtn, baseEvalBtn, fullEvalBtn;
	TextView timestamp, name, sura;

	Record record;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_record);

		record = getIntent().getExtras().getParcelable("recordParsel");

		setTitle(record.toString());

		sura = (TextView) findViewById(R.id.suraNumberRecord);
		name = (TextView) findViewById(R.id.recordName);
		timestamp = (TextView) findViewById(R.id.timeStampRecord);

		playBtn = (Button) findViewById(R.id.playBtn);
		pauseBtn = (Button) findViewById(R.id.pauseBtn);
		stopBtn = (Button) findViewById(R.id.stopBtn);
		baseEvalBtn = (Button) findViewById(R.id.baseEvalBtn);
		fullEvalBtn = (Button) findViewById(R.id.fullEvalBtn);

		playBtn.setOnClickListener(playListener);
		pauseBtn.setOnClickListener(pauseListener);
		stopBtn.setOnClickListener(stopListener);
		baseEvalBtn.setOnClickListener(baseListener);
		fullEvalBtn.setOnClickListener(fullListener);

		updateView();
	}

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.play_record, menu);
		return true;
	}

	public OnClickListener playListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};

	public OnClickListener pauseListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};

	public OnClickListener stopListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};

	public OnClickListener baseListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			GlobalController.resultController.addScore(
					PlayRecord.this,
					record.getPrefix() + "_"
							+ String.valueOf(record.getTimeStamp()), 0.0, 0.0,
					0.0, 0.0);
		}
	};

	public OnClickListener fullListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			GlobalController.resultController.addScore(
					PlayRecord.this,
					record.getPrefix() + "_"
							+ String.valueOf(record.getTimeStamp()), 0.0, 0.0,
					0.0, 0.0);
		}
	};
}
