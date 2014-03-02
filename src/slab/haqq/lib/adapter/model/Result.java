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
	private float scorePitch;
	private float scoreRhythm;
	private float scoreVolume;
	private float scoreRecog;

	/**
	 * @param rstId
	 * @param rid
	 * @param scorePitch
	 * @param scoreRhythm
	 * @param scoreVolume
	 * @param scoreRecog
	 */
	public Result(String rstId, float scorePitch,
			float scoreRhythm, float scoreVolume, float scoreRecog) {
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
	public float getScorePitch() {
		return scorePitch;
	}

	/**
	 * @param scorePitch
	 *            the scorePitch to set
	 */
	public void setScorePitch(float scorePitch) {
		this.scorePitch = scorePitch;
	}

	/**
	 * @return the scoreRhythm
	 */
	public float getScoreRhythm() {
		return scoreRhythm;
	}

	/**
	 * @param scoreRhythm
	 *            the scoreRhythm to set
	 */
	public void setScoreRhythm(float scoreRhythm) {
		this.scoreRhythm = scoreRhythm;
	}

	/**
	 * @return the scoreVolume
	 */
	public float getScoreVolume() {
		return scoreVolume;
	}

	/**
	 * @param scoreVolume
	 *            the scoreVolume to set
	 */
	public void setScoreVolume(float scoreVolume) {
		this.scoreVolume = scoreVolume;
	}

	/**
	 * @return the scoreRecog
	 */
	public float getScoreRecog() {
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
	public float getAverageScore() {
		return (float) ((0.5 * scoreRecog) + (0.15 * scorePitch)
				+ (0.25 * scoreRhythm) + (0.1 * scoreVolume));
	}
	
	public Result(Parcel in) {
		this.rstId = in.readString();
		this.scorePitch = in.readFloat();
		this.scoreRecog = in.readFloat();
		this.scoreRhythm = in.readFloat();
		this.scoreVolume = in.readFloat();
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
		dest.writeFloat(scorePitch);
		dest.writeFloat(scoreRecog);
		dest.writeFloat(scoreRhythm);
		dest.writeFloat(scoreVolume);
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
