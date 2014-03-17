/**
 * 
 */
package slab.haqq.service;

import java.util.Random;

import slab.haqq.R;
import slab.haqq.api.result.ResultDetail;
import slab.haqq.lib.GlobalController;
import slab.haqq.lib.adapter.model.Record;
import slab.haqq.lib.adapter.model.Result;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

/**
 * @author rasxen
 * 
 */
public class DefaultEvaluationService extends IntentService implements
		IEvaluationService {

	/**
	 * @param name
	 */
	public DefaultEvaluationService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name
	 */
	public DefaultEvaluationService() {
		super("DefaultEvaluationService");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see slab.haqq.service.IEvaluationService#NotifyStart()
	 */
	@Override
	public void NotifyStart(NotificationManager nm, int id, Record record) {
		// TODO Auto-generated method stub
		NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(
				this)
				.setSmallIcon(R.drawable.ic_stat_evaluation_loading)
				.setContentTitle(
						"Haqq : " + record.getPrefix() + "_"
								+ String.valueOf(record.getTimeStamp())
								+ "Evaluating ...")
				.setContentText("Please wait");
		nm.notify(id, nBuilder.build());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see slab.haqq.service.IEvaluationService#NotifyDone()
	 */
	@Override
	public void NotifyDone(NotificationManager nm, int id, Result result) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, ResultDetail.class);
		intent.putExtra("resultParsel", result);

		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(ResultDetail.class);
		stackBuilder.addNextIntent(intent);

		PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(
				this)
				.setSmallIcon(R.drawable.ic_stat_done_evaluation)
				.setContentTitle(
						"Haqq : " + result.getRstId() + " Evaluation Done")
				.setContentText(result.toString())
				.setContentIntent(pendingIntent);
		nm.notify(id, nBuilder.build());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.IntentService#onHandleIntent(android.content.Intent)
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		Record record = intent.getExtras().getParcelable("recordParsel");
		NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		int nid = new Random().nextInt();
		NotifyStart(nm, nid, record);
		long endTime = System.currentTimeMillis() + 5 * 1000;
		while (System.currentTimeMillis() < endTime) {
			synchronized (this) {
				try {
					wait(endTime - System.currentTimeMillis());
				} catch (Exception e) {
				} finally {
					Result result = GlobalController.resultProvider.addScore(
							this,
							record.getPrefix() + "_"
									+ String.valueOf(record.getTimeStamp()),
							0.0, 0.0, 0.0, 0.0, "");
					NotifyDone(nm, nid, result);
				}
			}
		}

	}

}
