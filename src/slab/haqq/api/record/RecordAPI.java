/**
 * 
 */
package slab.haqq.api.record;

import slab.haqq.R;
import slab.haqq.lib.GlobalController;
import slab.haqq.lib.adapter.RecordAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * @author rasxen
 * 
 */
public class RecordAPI extends Fragment {
	private ListView lv;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.activity_record, container,
				false);
		
		lv = (ListView) rootView.findViewById(R.id.api_record_lv);
		lv.setAdapter(new RecordAdapter(rootView.getContext()));
		lv.setOnItemClickListener(recordListener);
		return rootView;
	}
	
	private OnItemClickListener recordListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(parent.getContext(), PlayRecord.class);
			intent.putExtra("recordParsel",
					GlobalController.recController.recList.get(position));
			startActivity(intent);
			
		}
	};
}
