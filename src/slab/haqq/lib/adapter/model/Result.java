/**
 * 
 */
package slab.haqq.lib.adapter.model;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author rasxen
 * <p><h1>Result</h1></p>
 * <p>A result data model
 * <br>Implement a {@link Parcelable} to pass this model between {@link Activity}
 * <br>Saved to xml
 * </p>
 */
public class Result implements Parcelable {
	private String rstId;
	private double scorePitch;
	private double scoreRhythm;
	private double scoreVolume;
	private double scoreRecog;
	private String recogText;
	private String resultState;
	
	public static final String BASIC = "BASIC";
	public static final String FULL = "FULL";

	/**
	 * A result model constructor
	 * 
	 * @param rstId
	 * @param rid
	 * @param scorePitch
	 * @param scoreRhythm
	 * @param scoreVolume
	 * @param scoreRecog
	 */
	public Result(String rstId, double scorePitch, double scoreRhythm,
			double scoreVolume, double scoreRecog, String recogText, String resultState) {
		this.rstId = rstId;
		this.scorePitch = scorePitch;
		this.scoreRhythm = scoreRhythm;
		this.scoreVolume = scoreVolume;
		this.scoreRecog = scoreRecog;
		this.recogText = recogText;
		this.resultState = resultState;
	}

	/**
	 * @return the rstId
	 */
	public String getRstId() {
		return rstId;
	}

	/**
	 * @param rstId
	 *            the rstId to set
	 */
	public void setRstId(String rstId) {
		this.rstId = rstId;
	}

	/**
	 * @return the scorePitch
	 */
	public double getScorePitch() {
		return scorePitch;
	}

	/**
	 * @param scorePitch
	 *            the scorePitch to set
	 */
	public void setScorePitch(double scorePitch) {
		this.scorePitch = scorePitch;
	}

	/**
	 * @return the scoreRhythm
	 */
	public double getScoreRhythm() {
		return scoreRhythm;
	}

	/**
	 * @param scoreRhythm
	 *            the scoreRhythm to set
	 */
	public void setScoreRhythm(double scoreRhythm) {
		this.scoreRhythm = scoreRhythm;
	}

	/**
	 * @return the scoreVolume
	 */
	public double getScoreVolume() {
		return scoreVolume;
	}

	/**
	 * @param scoreVolume
	 *            the scoreVolume to set
	 */
	public void setScoreVolume(double scoreVolume) {
		this.scoreVolume = scoreVolume;
	}

	/**
	 * @return the scoreRecog
	 */
	public double getScoreRecog() {
		return scoreRecog;
	}

	/**
	 * @param scoreRecog
	 *            the scoreRecog to set
	 */
	public void setScoreRecog(double scoreRecog) {
		this.scoreRecog = scoreRecog;
	}

	/**
	 * @return the recogText
	 */
	public String getRecogText() {
		return recogText;
	}

	/**
	 * @param recogText
	 *            the recogText to set
	 */
	public void setRecogText(String recogText) {
		this.recogText = recogText;
	}
	
	/**
	 * @return the recogText
	 */
	public String getResultState() {
		return resultState;
	}

	/**
	 * @param recogText
	 *            the recogText to set
	 */
	public void setResultState(String resultState) {
		this.resultState = resultState;
	}

	/**
	 * Return of average score of this {@link Result}
	 * with a scale 0.45 for recog, 0.15 for pitch,
	 * 0.3 for rhtyhm, 0.1 for volume (Full)
	 * with a scale 0.5 for rhythm, 0.3 for pitch, and 0.2 for vol
	 * 
	 * @return average score
	 */
	public double getAverageScore() {
		if(resultState.equalsIgnoreCase(BASIC)){
			return Math.round((0.25 * scorePitch)
				+ (0.6 * scoreRhythm) + (0.15 * scoreVolume));
		}
		return Math.round((double) ((0.45 * scoreRecog) + (0.15 * scorePitch)
				+ (0.3 * scoreRhythm) + (0.1 * scoreVolume)));
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "P : " + String.valueOf(getScorePitch()) + " R : "
				+ String.valueOf(getScoreRhythm()) + " V : "
				+ String.valueOf(getScoreVolume()) + " Recog : "
				+ String.valueOf(getScoreRecog());
	}

	/**
	 * A result constructor with a parcel as its parameter
	 * @param in
	 */
	public Result(Parcel in) {
		this.rstId = in.readString();
		this.recogText = in.readString();
		this.resultState = in.readString();
		this.scorePitch = in.readDouble();
		this.scoreRecog = in.readDouble();
		this.scoreRhythm = in.readDouble();
		this.scoreVolume = in.readDouble();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.Parcelable#describeContents()
	 */
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(rstId);
		dest.writeString(recogText);
		dest.writeString(resultState);
		dest.writeDouble(scorePitch);
		dest.writeDouble(scoreRecog);
		dest.writeDouble(scoreRhythm);
		dest.writeDouble(scoreVolume);
	}

	/**
	 * TODO : Documentation
	 */
	public static Parcelable.Creator<Result> CREATOR = new Creator<Result>() {

		@Override
		public Result[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Result[size];
		}

		@Override
		public Result createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Result(source);
		}
	};
}
