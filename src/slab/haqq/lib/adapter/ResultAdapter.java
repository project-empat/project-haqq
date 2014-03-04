/**
 * 
 */
package slab.haqq.lib.adapter;

import slab.haqq.R;
import slab.haqq.lib.GlobalController;
import slab.haqq.lib.adapter.model.Result;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author rasxen
 * 
 */
public class ResultAdapter extends BaseAdapter {
	private Context context;
	private static LayoutInflater inflater;
	

	/**
	 * TODO : Documentation
	 * @param context
	 */
	public ResultAdapter(Context context) {
		this.context = context;
		inflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return GlobalController.resultController.resList.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return GlobalController.resultController.resList.get(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi = convertView;
		if (vi == null) {
			vi = inflater.inflate(R.layout.result_adapter_layout, null);
		}
		
		TextView resId = (TextView)vi.findViewById(R.id.resultId);
		TextView pitch = (TextView)vi.findViewById(R.id.pitchScore);
		TextView rhythm = (TextView)vi.findViewById(R.id.rhythmScore);
		TextView volume = (TextView)vi.findViewById(R.id.volScore);
		TextView recog = (TextView)vi.findViewById(R.id.recogScore);
		
		Result result = GlobalController.resultController.resList.get(position);
		Log.v("result", result.getRstId());
		
		resId.setText(result.getRstId());
		pitch.setText("P : "+String.valueOf(result.getScorePitch()));
		rhythm.setText("R : "+String.valueOf(result.getScoreRhythm()));
		volume.setText("V : "+String.valueOf(result.getScoreVolume()));
		recog.setText("Recog : "+String.valueOf(result.getScoreRecog()));
		
		return vi;
	}

}
