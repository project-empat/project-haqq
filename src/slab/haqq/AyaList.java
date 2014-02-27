package slab.haqq;

import slab.haqq.lib.adapter.model.Sura;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AyaList extends Activity {
	private Sura sura;
	private ListView ayaListView;
	private String[] ayaArray;
	private ArrayAdapter<String> ayaAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aya_list);

		this.sura = getIntent().getExtras().getParcelable("suraParcel");
		setTitle(sura.getName() + " - " + sura.getArname());

		ayaArray = new String[this.sura.getAyaCount()];
		for (int i = 0; i < ayaArray.length; i++) {
			ayaArray[i] = sura.getName()
					+ " - " + String.valueOf(i + 1);
		}

		ayaAdapter = new ArrayAdapter<String>(this,
				R.layout.simple_listview_adapter, R.id.simpleString, ayaArray);

		ayaListView = (ListView) findViewById(R.id.ayaLV);
		ayaListView.setAdapter(ayaAdapter);
		ayaListView.setOnItemClickListener(ayaListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.aya_list, menu);
		return true;
	}

	private OnItemClickListener ayaListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(parent.getContext(), ReadAya.class);
			intent.putExtra("suraData", sura);
			intent.putExtra("suraNumber", sura.getIdn());
			intent.putExtra("ayaNumber", position + 1);
			startActivity(intent);
		}
	};
}
