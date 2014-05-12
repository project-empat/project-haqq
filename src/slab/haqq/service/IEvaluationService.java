/**
 * 
 */
package slab.haqq.service;

import slab.haqq.ResultDetail;
import slab.haqq.lib.adapter.model.Record;
import slab.haqq.lib.adapter.model.Result;
import android.app.NotificationManager;
import android.content.res.Resources.NotFoundException;

/**
 * @author rasxen
 * <p><h1>IEvaluationService</h1></p>
 * <p>A default interface for Evaluation Service</p>
 */
public interface IEvaluationService {
	/**
	 * Manage Notification when evaluation service is starting
	 * @param nm
	 * @param id
	 * @param record
	 */
	public void NotifyStart(NotificationManager nm, int id, Record record);

	/**
	 * Manage Notification when evaluation service is finishing
	 * then add pending intent to {@link ResultDetail} with current result
	 * @param nm
	 * @param id
	 * @param result
	 */
	public void NotifyDone(NotificationManager nm, int id, Result result);
	
	/**
	 * Manage Notification when an {@link Exception}, {@link NotFoundException}, {@link OutOfMemoryError} happened 
	 * @param nm
	 * @param id
	 * @param message
	 */
	public void NotifyFailed(NotificationManager nm, int id, String message);
}
