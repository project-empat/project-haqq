/**
 * 
 */
package slab.haqq.service;

import slab.haqq.lib.adapter.model.Result;
import android.app.NotificationManager;

/**
 * @author rasxen
 *
 */
public interface IEvaluationService {
	/**
	 * TODO : Documentation
	 */
	public void NotifyStart(NotificationManager nm, int id);
	/**
	 * TODO : Documentation
	 */
	public void NotifyDone(NotificationManager nm, int id, Result result);
}
