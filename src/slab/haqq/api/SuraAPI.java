/**
 * 
 */
package slab.haqq.api;

import slab.haqq.AyaList;
import slab.haqq.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import slab.haqq.lib.GlobalController;
import slab.haqq.lib.adapter.*;

/**
 * @author rasxen
 * 
 */
public class SuraAPI extends Fragment {
	private ListView lv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.activity_sura, container,
				false);
		lv = (ListView) rootView.findViewById(R.id.api_sura_lv);
		lv.setAdapter(new SuraAdapter(GlobalController.SuraList, rootView
				.getContext()));
		lv.setOnItemClickListener(suraListener);
		return rootView;
	}

	private OnItemClickListener suraListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(parent.getContext(), AyaList.class);
			intent.putExtra("suraParcel",
					GlobalController.SuraList.get(position));
			startActivity(intent);
		}
	};
}
