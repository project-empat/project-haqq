package slab.haqq;

import java.util.ArrayList;
import java.util.List;

import slab.haqq.lib.GlobalController;
import slab.haqq.lib.adapter.model.Sura;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * @author rasxen
 * <p><h1>Goto Activity</h1></p>
 * <p>Goto UI Handle,
 * <br>provide quick jump to specific aya
 * </p>
 */
public class GotoAyaActivity extends Activity {

	private Spinner suraDropdown, ayaDropdown;
	private ArrayAdapter<String> ayaAdapter;
	private Sura suradata;
	private Button jumpBtn;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goto_aya);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Log.v("Goto", "layoutComplete");
		setTitle("Go to Aya");
		suraDropdown = (Spinner)findViewById(R.id.suraDrop);
		ayaDropdown = (Spinner)findViewById(R.id.ayaDrop);
		
		List<String> sura = new ArrayList<String>();
		for(int i=0;i < GlobalController.SuraList.size(); i++){
			sura.add(GlobalController.SuraList.get(i).getName());
		}
		ArrayAdapter<String> suraadapter = new ArrayAdapter<String>(this, R.layout.spinner_simple, R.id.spinnerText1, sura);
		suraDropdown.setAdapter(suraadapter);
		suraDropdown.setOnItemSelectedListener(suraListener);
		Log.v("Goto", "Sura Dropdown Complete");
		suradata = GlobalController.SuraList.get(0); 
		
		List<String> aya = new ArrayList<String>();
		for(int i=0;i < suradata.getAyaCount(); i++){
			aya.add(String.valueOf(i+1));
		}
		ayaAdapter = new ArrayAdapter<String>(this, R.layout.spinner_simple, R.id.spinnerText1, aya);
		ayaDropdown.setAdapter(ayaAdapter);
		Log.v("Goto", "Aya Dropdown Complete");
		jumpBtn = (Button)findViewById(R.id.jumpButton);
		jumpBtn.setOnClickListener(gotoListener);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.goto_aya, menu);
		return true;
	}
	
	/**
	 * TODO : Documentation
	 */
	private OnItemSelectedListener suraListener = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
				long id) {
			// TODO Auto-generated method stub
			suradata = GlobalController.SuraList.get(pos);
			ayaAdapter.clear();
			List<String> aya = new ArrayList<String>();
			for(int i=0;i < suradata.getAyaCount(); i++){
				aya.add(String.valueOf(i+1));
			}
			ayaAdapter.addAll(aya);
			//ayaAdapter = new ArrayAdapter<String>(GotoAyaActivity.this, R.id.simpleStringSpinner2, R.layout.simple_spinner2_adapter, aya);
			//ayaDropdown.setAdapter(ayaAdapter);
		}
		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};

	/**
	 * TODO : Documentation
	 */
	private OnClickListener gotoListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(GotoAyaActivity.this,ReadAya.class);
			intent.putExtra("suraData", suradata);
			intent.putExtra("suraNumber", suradata.getIdn());
			intent.putExtra("ayaNumber", ayaDropdown.getSelectedItemPosition() + 1);
			startActivity(intent);
		}
	};
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	    	onBackPressed();
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
}
