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
import android.util.Log;

import com.ringdroid.DTW;
import com.ringdroid.Fourier;
import com.ringdroid.Skoring;
import com.ringdroid.soundfile.CheapSoundFile;

/**
 * @author rasxen
 * <p><h1>DefaultEvaluationService</h1></p>
 * <p>A Basic background Service to evaluate record, 
 * <br>and process its notification
 * </p>
 */
public class DefaultEvaluationService extends IntentService implements
		IEvaluationService {
	/**
	 * Constructor of Evaluation Service
	 * @param name
	 */
	public DefaultEvaluationService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * An empty service constructor
	 * Necessary for every service
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
						"Evaluating ... : " + record.getPrefix() + "_"
								+ String.valueOf(record.getTimeStamp()))
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
				this).setSmallIcon(R.drawable.ic_stat_done_evaluation)
				.setContentTitle("Evaluation done : " + result.getRstId())
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
		// long endTime = System.currentTimeMillis() + 5 * 1000;

		final CheapSoundFile.ProgressListener listener = new CheapSoundFile.ProgressListener() {

			@Override
			public boolean reportProgress(double fractionComplete) {
				// TODO Auto-generated method stub
				return true;
			}
		};
		try {
			CheapSoundFile fileSource = CheapSoundFile.create(
					GlobalController.murattalProvider.getMurattalFilePath(
							"saadalghamidi",
							Integer.parseInt(record.getSuraId()),
							record.getAyaNumber()), listener);
			CheapSoundFile fileRecord = CheapSoundFile.create(
					record.getFilePath(), listener);
			Log.v("Evaluation","fileLoad");
			Skoring skoring = new Skoring();
			Fourier fftSource = new Fourier();
			Fourier fftRecord = new Fourier();

			int[] frameGainsSource = fileSource.getFrameGains();
			int[] frameGainsRecord = fileRecord.getFrameGains();
			Log.v("Evaluation","fileLoadDone");
			int freqs = 8000;
			fftSource.calcSpec(frameGainsSource, freqs);
			fftRecord.calcSpec(frameGainsRecord, freqs);

			double[] energySource = skoring.calcEnergy(frameGainsSource);
			double[] energyRecord = skoring.calcEnergy(frameGainsRecord);
			skoring.clearArray();

			DTW pitchDTW = new DTW(fftSource.getMagnitude(),
					fftRecord.getMagnitude());
			fftSource.clearMagnitude();
			fftRecord.clearMagnitude();
			DTW volDTW = new DTW(energySource, energyRecord);
			
			double pitchScore = skoring.getPitchScore(pitchDTW.getDistance());
			double volScore = skoring.getVolScore(volDTW.getDistance());
			double rhythmScore = skoring.getRhyScore(volDTW.getInBeat());
			
			Log.v("pitch distance", String.valueOf(pitchDTW.getDistance()));
			Log.v("vol distance", String.valueOf(volDTW.getDistance()));
			Log.v("rhy beat", String.valueOf(volDTW.getInBeat()));

			Result result = GlobalController.resultProvider.addScore(
					this,
					record.getPrefix() + "_"
							+ String.valueOf(record.getTimeStamp()),
					pitchScore, rhythmScore, volScore, 0.0, "");
			NotifyDone(nm, nid, result);
		} catch (Exception e) {
			// TODO: handle exception
			Log.v("Evaluation", e.toString());
			NotifyFailed(nm, nid, e.toString());
		}

		/*
		 * while (System.currentTimeMillis() < endTime) { synchronized (this) {
		 * try { wait(endTime - System.currentTimeMillis());
		 * 
		 * } catch (Exception e) { } finally { Result result =
		 * GlobalController.resultProvider.addScore( this, record.getPrefix() +
		 * "_" + String.valueOf(record.getTimeStamp()), 0.0, 0.0, 0.0, 0.0, "");
		 * NotifyDone(nm, nid, result); } } }
		 */

	}

	/* (non-Javadoc)
	 * @see slab.haqq.service.IEvaluationService#NotifyFailed(android.app.NotificationManager, int, java.lang.String)
	 */
	@Override
	public void NotifyFailed(NotificationManager nm, int id, String message) {
		// TODO Auto-generated method stub
		NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(
				this).setSmallIcon(R.drawable.ic_stat_evaluationfailed)
				.setContentTitle("Evaluation Failed")
				.setContentText(message);
		nm.notify(id, nBuilder.build());
	}

}
