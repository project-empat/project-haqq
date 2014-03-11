/**
 * 
 */
package slab.haqq.api.record;

import slab.haqq.R;
import slab.haqq.lib.GlobalController;
import slab.haqq.lib.adapter.RecordAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * @author rasxen
 * 
 */
public class RecordAPI extends Fragment {
	private ListView lv;
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_record, container,
				false);
		
		lv = (ListView) rootView.findViewById(R.id.api_record_lv);
		GlobalController.recordAdapter = new RecordAdapter(this.getActivity());
		lv.setAdapter(GlobalController.recordAdapter);
		//lv.setOnItemClickListener(recordListener);
		return rootView;
	}
}
