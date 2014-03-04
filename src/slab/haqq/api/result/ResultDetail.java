package slab.haqq.api.result;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import slab.haqq.R;
import slab.haqq.lib.GlobalController;
import slab.haqq.lib.adapter.model.Result;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ResultDetail extends Activity {
	Result result;
	TextView displayName, pitchScore, rhythmScore, volumeScore, recogScore, averageScore, commentText;
	LinearLayout imageData;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result_detail);
		
		result = getIntent().getExtras().getParcelable("resultParsel");
		setTitle(result.getRstId());
		
		displayName = (TextView)findViewById(R.id.resultIDText);
		pitchScore = (TextView)findViewById(R.id.pitchScore);
		rhythmScore = (TextView)findViewById(R.id.rhythmScore);
		volumeScore = (TextView)findViewById(R.id.volumeScore);
		recogScore = (TextView)findViewById(R.id.recogScore);
		averageScore = (TextView)findViewById(R.id.averageScore);
		commentText = (TextView)findViewById(R.id.comment);
		
		imageData = (LinearLayout)findViewById(R.id.imageSave);
		imageData.setDrawingCacheEnabled(true);
		updateView();
	}
	
	/**
	 * TODO : Documentation
	 */
	private void updateView(){
		
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result_detail, menu);
		return true;
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_saveimage:
			SaveImage();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * TODO : Documentation
	 */
	private void SaveImage(){
		String filepath = Environment.getExternalStorageDirectory().getAbsolutePath();
		File file = new File(filepath, GlobalController.RES_SNAPSHOT_FOLDER);
		if(!file.exists()){
			file.mkdirs();
		}
		Bitmap bitmap = imageData.getDrawingCache();
		try {
			bitmap.compress(CompressFormat.JPEG, 100, new FileOutputStream(file.getAbsolutePath()+"/"+result.getRstId()+".jpg"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
