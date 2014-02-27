/**
 * 
 */
package slab.haqq.lib;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import slab.haqq.lib.adapter.model.Record;

/**
 * @author rasxen
 * 
 */
public class RecordController {

	public static List<Record> recList = new ArrayList<Record>();

	/**
	 * 
	 */
	public RecordController(Context context) {
		// TODO Auto-generated constructor stub
	}

	public static void add(Record record, Context context) {
		recList.add(record);
		writeToXML(context);
	}

	public static void writeToXML(Context context) {

	}

	public static void readFromXML(Context context) {

	}

}
