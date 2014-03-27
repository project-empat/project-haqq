/**
 * 
 */
package slab.haqq.lib.adapter;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import slab.haqq.R;
import slab.haqq.api.record.PlayRecord;
import slab.haqq.lib.GlobalController;
import slab.haqq.lib.adapter.model.Record;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;

/**
 * @author rasxen
 * A record model adapter to handle its display its action(open, delete)
 */
public class RecordAdapter extends BaseAdapter {

	private Context context;
	private static LayoutInflater inflater;
	private AlertDialog confirmDeleteDialog;
	private Record recordToProcess = null;

	/**
	 * A constructor for RecordAdapter
	 * Create an {@link AlertDialog} for deleting {@link Record}
	 * @param context
	 */
	public RecordAdapter(Context context) {
		this.context = context;
		inflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		confirmDeleteDialog = new AlertDialog.Builder(context).setTitle("Confirm Deletion")
				.setMessage("Are you sure you want to delete this record")
				.setNegativeButton("No", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
				}).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						GlobalController.recordProvider.deleteRecord(recordToProcess, confirmDeleteDialog.getContext());
					}
				}).create();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return GlobalController.recordProvider.recordList.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return GlobalController.recordProvider.recordList.get(arg0);
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

		Record record = GlobalController.recordProvider.recordList.get(arg0);

		name.setText(record.toString());
		Date date = new Date(record.getTimeStamp());
		Format format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		timestamp.setText(String.valueOf(format.format(date)));
		sura.setText(GlobalController.SuraMap.get(record.getSuraId()).getName());
		aya.setText(String.valueOf(record.getAyaNumber()));
		
		LinearLayout itemData;
		Button popup;
		
		itemData = (LinearLayout)vi.findViewById(R.id.itemData);
		popup = (Button) vi.findViewById(R.id.popup);
		
		itemData.setOnClickListener(openListener);
		popup.setOnClickListener(popUpListener);
		
		return vi;
	}
	
	private OnClickListener openListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			TextView name = (TextView) arg0.findViewById(R.id.recordName);
			Intent intent = new Intent(context, PlayRecord.class);
			intent.putExtra("recordParsel",
					GlobalController.recordProvider.recordMap.get(name.getText()));
			context.startActivity(intent);
		}
	};
	
	private OnClickListener popUpListener = new OnClickListener() {
		
		@Override
		public void onClick(View vi) {
			// TODO Auto-generated method stub
			TextView name = (TextView) ((View)vi.getParent()).findViewById(R.id.recordName);
			recordToProcess = GlobalController.recordProvider.recordMap.get(name.getText());
			PopupMenu popup = new PopupMenu(context, vi);
			popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());
			popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {
				
				@Override
				public boolean onMenuItemClick(MenuItem item) {
					// TODO Auto-generated method stub
					Log.v("delete", "delete");
					confirmDeleteDialog.show();
					return false;
				}
			});
			popup.show();
		}
	};

}
