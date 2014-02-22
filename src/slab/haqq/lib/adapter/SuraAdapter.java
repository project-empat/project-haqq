/**
 * 
 */
package slab.haqq.lib.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import slab.haqq.R;
import slab.haqq.lib.GlobalController;
import slab.haqq.lib.GlobalController.Sura;

/**
 * @author rasxen
 *
 */
public class SuraAdapter extends BaseAdapter {
	
	private List<GlobalController.Sura> suraList;
	private Context context;
	private static LayoutInflater inflater;
	

	/**
	 * @param suraList
	 * @param context
	 */
	public SuraAdapter(List<Sura> suraList, Context context) {
		this.suraList = suraList;
		this.context = context;
		inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return suraList.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return suraList.get(arg0);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View vi = arg1;
        if (vi == null)
            vi = inflater.inflate(R.layout.sura_adapter_layout,null);
        TextView textNum = (TextView) vi.findViewById(R.id.sura_adapter_num);
        TextView textSura = (TextView)vi.findViewById(R.id.sura_adapter_name);
        TextView textVerse = (TextView)vi.findViewById(R.id.sura_adapter_verse);
        
        textNum.setText(suraList.get(arg0).getId());
        textSura.setText(suraList.get(arg0).getName()+" ("+suraList.get(arg0).getArname()+")");
        textVerse.setText(String.valueOf(suraList.get(arg0).getAyaCount())+" Aya");
        
		return vi;
	}

}
