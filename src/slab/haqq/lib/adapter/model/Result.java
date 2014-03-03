/**
 * 
 */
package slab.haqq.lib.adapter.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author rasxen
 * 
 */
public class Result implements Parcelable{
	private String rstId;
	private double scorePitch;
	private double scoreRhythm;
	private double scoreVolume;
	private double scoreRecog;

	/**
	 * @param rstId
	 * @param rid
	 * @param scorePitch
	 * @param scoreRhythm
	 * @param scoreVolume
	 * @param scoreRecog
	 */
	public Result(String rstId, double scorePitch,
			double scoreRhythm, double scoreVolume, double scoreRecog) {
		this.rstId = rstId;
		this.scorePitch = scorePitch;
		this.scoreRhythm = scoreRhythm;
		this.scoreVolume = scoreVolume;
		this.scoreRecog = scoreRecog;
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
	public void setScoreRecog(float scoreRecog) {
		this.scoreRecog = scoreRecog;
	}

	/**
	 * @return average score
	 */
	public double getAverageScore() {
		return (double) ((0.5 * scoreRecog) + (0.15 * scorePitch)
				+ (0.25 * scoreRhythm) + (0.1 * scoreVolume));
	}
	
	public Result(Parcel in) {
		this.rstId = in.readString();
		this.scorePitch = in.readDouble();
		this.scoreRecog = in.readDouble();
		this.scoreRhythm = in.readDouble();
		this.scoreVolume = in.readDouble();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(rstId);
		dest.writeDouble(scorePitch);
		dest.writeDouble(scoreRecog);
		dest.writeDouble(scoreRhythm);
		dest.writeDouble(scoreVolume);
	}

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
