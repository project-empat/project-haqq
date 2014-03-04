/**
 * 
 */
package slab.haqq.lib.adapter;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import slab.haqq.R;
import slab.haqq.lib.GlobalController;
import slab.haqq.lib.adapter.model.Record;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author rasxen
 * 
 */
public class RecordAdapter extends BaseAdapter {

	private Context context;
	private static LayoutInflater inflater;

	/**
	 * TODO : Documentation
	 * @param context
	 */
	public RecordAdapter(Context context) {
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
		return GlobalController.recController.recList.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return GlobalController.recController.recList.get(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@SuppressLint("SimpleDateFormat")
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View vi = arg1;
		if (vi == null) {
			vi = inflater.inflate(R.layout.record_adapter_layout, null);
		}

		TextView name = (TextView) vi.findViewById(R.id.recordName);
		TextView timestamp = (TextView) vi.findViewById(R.id.timeStampRecord);
		TextView sura = (TextView) vi.findViewById(R.id.suraNumberRecord);
		TextView aya = (TextView) vi.findViewById(R.id.ayaNumberRecord);

		Record record = GlobalController.recController.recList.get(arg0);

		name.setText(record.toString());
		Date date = new Date(record.getTimeStamp());
		Format format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		timestamp.setText(String.valueOf(format.format(date)));
		sura.setText(GlobalController.SuraMap.get(record.getSuraId()).getName());
		aya.setText(String.valueOf(record.getAyaNumber()));

		return vi;
	}

}
