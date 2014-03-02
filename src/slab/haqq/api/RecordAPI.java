/**
 * 
 */
package slab.haqq.api;

import slab.haqq.R;
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
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.activity_record, container,
				false);
		
		lv = (ListView) rootView.findViewById(R.id.api_record_lv);
		lv.setAdapter(new RecordAdapter(rootView.getContext()));
		
		return rootView;
	}
}
