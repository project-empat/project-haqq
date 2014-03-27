/**
 * 
 */
package slab.haqq.lib.adapter;

import slab.haqq.R;
import slab.haqq.api.result.ResultDetail;
import slab.haqq.lib.GlobalController;
import slab.haqq.lib.adapter.model.Result;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.PopupMenu.OnMenuItemClickListener;

/**
 * @author rasxen
 * <p><h1>ResultAdapter</h1></p>
 * <p>A result model adapter to handle its display its action(open, delete)</p>
 */
public class ResultAdapter extends BaseAdapter {
	private Context context;
	private static LayoutInflater inflater;
	private AlertDialog confirmDeleteDialog;
	private Result resultToProcess = null;
	/**
	 * A constructor for ResultAdapter
	 * Create an {@link AlertDialog} for deleting {@link Result}
	 * @param context
	 */
	public ResultAdapter(Context context) {
		this.context = context;
		inflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		confirmDeleteDialog = new AlertDialog.Builder(context).setTitle("Confirm Deletion")
				.setMessage("Are you sure you want to delete this result")
				.setNegativeButton("No", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
				}).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						GlobalController.resultProvider.deleteScore(confirmDeleteDialog.getContext(), resultToProcess);
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
		return GlobalController.resultProvider.resultList.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return GlobalController.resultProvider.resultList.get(position);
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

		TextView resId = (TextView) vi.findViewById(R.id.resultId);
		TextView pitch = (TextView) vi.findViewById(R.id.pitchScore);
		TextView rhythm = (TextView) vi.findViewById(R.id.rhythmScore);
		TextView volume = (TextView) vi.findViewById(R.id.volScore);
		TextView recog = (TextView) vi.findViewById(R.id.recogScore);

		Result result = GlobalController.resultProvider.resultList
				.get(position);
		Log.v("result", result.getRstId());

		resId.setText(result.getRstId());
		pitch.setText("P : " + String.valueOf(result.getScorePitch()));
		rhythm.setText("R : " + String.valueOf(result.getScoreRhythm()));
		volume.setText("V : " + String.valueOf(result.getScoreVolume()));
		recog.setText("Recog : " + String.valueOf(result.getScoreRecog()));
		
		LinearLayout itemData;
		Button popup;
		
		itemData = (LinearLayout)vi.findViewById(R.id.itemData);
		popup = (Button) vi.findViewById(R.id.popup);
		
		itemData.setOnClickListener(openListener);
		popup.setOnClickListener(popUpListener);
		

		return vi;
	}

	/**
	 * TODO : Documentation
	 */
	private OnClickListener openListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			TextView resId = (TextView) arg0.findViewById(R.id.resultId);
			Intent intent = new Intent(context, ResultDetail.class);
			intent.putExtra("resultParsel",
					GlobalController.resultProvider.resultMap.get(resId.getText()));
			context.startActivity(intent);
		}
	};

	/**
	 * TODO : Documentation
	 */
	private OnClickListener popUpListener = new OnClickListener() {

		@Override
		public void onClick(View vi) {
			// TODO Auto-generated method stub
			TextView resId = (TextView) ((View)vi.getParent()).findViewById(R.id.resultId);
			resultToProcess = GlobalController.resultProvider.resultMap.get(resId.getText());
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
