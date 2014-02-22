/**
 * 
 */
package slab.haqq.api;

import slab.haqq.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
		View rootView = inflater.inflate(R.layout.activity_sura,
				container, false);
		lv = (ListView)rootView.findViewById(R.id.api_sura_lv);
		lv.setAdapter(new SuraAdapter(GlobalController.SuraList, rootView.getContext()));
		return rootView;
	}
}
