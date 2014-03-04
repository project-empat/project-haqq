/**
 * 
 */
package slab.haqq.api.result;

import slab.haqq.R;
import slab.haqq.lib.GlobalController;
import slab.haqq.lib.adapter.ResultAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author rasxen
 * 
 */
public class ResultAPI extends Fragment {
	private ListView lv;
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.activity_result, container,
				false);
		
		lv = (ListView) rootView.findViewById(R.id.api_result_lv);
		lv.setAdapter(new ResultAdapter(rootView.getContext()));
		lv.setOnItemClickListener(resultListener);
		return rootView;
	}
	
	/**
	 * TODO : Documentation
	 */
	private OnItemClickListener resultListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(parent.getContext(), ResultDetail.class);
			intent.putExtra("resultParsel",
					GlobalController.resultController.resList.get(position));
			startActivity(intent);
			
		}
	};
}
