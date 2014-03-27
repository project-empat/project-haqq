/**
 * 
 */
package slab.haqq.lib.adapter.model;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author rasxen
 * <p><h1>Record</h1></p>
 * <p>A record data model
 * <br>Implement a {@link Parcelable} to pass this model between {@link Activity}
 * <br>Saved to xml
 * </p>
 */
public class Record implements Parcelable {
	private long timeStamp;
	private String suraId;
	private int ayaNumber;
	private String prefix;
	private String filePath;

	/**
	 * A record model constructor
	 * @param timeStamp
	 * @param suraId
	 * @param ayaNumber
	 * @param prefix
	 */
	public Record(long timeStamp, String suraId, int ayaNumber, String prefix) {
		this.timeStamp = timeStamp;
		this.suraId = suraId;
		this.ayaNumber = ayaNumber;
		this.prefix = prefix;
	}

	/**
	 * @return the timeStamp
	 */
	public long getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param timeStamp
	 *            the timeStamp to set
	 */
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	/**
	 * @return the suraId
	 */
	public String getSuraId() {
		return suraId;
	}

	/**
	 * @param suraId
	 *            the suraId to set
	 */
	public void setSuraId(String suraId) {
		this.suraId = suraId;
	}

	/**
	 * @return the ayaNumber
	 */
	public int getAyaNumber() {
		return ayaNumber;
	}

	/**
	 * @param ayaNumber
	 *            the ayaNumber to set
	 */
	public void setAyaNumber(int ayaNumber) {
		this.ayaNumber = ayaNumber;
	}

	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @param prefix
	 *            the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath
	 *            the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return prefix + "_" + String.valueOf(timeStamp) + "_" + suraId + "_"
				+ String.valueOf(ayaNumber);
	}
	
	/**
	 * A record constructor with a parcel as its parameter
	 * @param in
	 */
	public Record(Parcel in) {
		this.prefix = in.readString();
		this.suraId = in.readString();
		this.filePath = in.readString();
		this.timeStamp = in.readLong();
		this.ayaNumber = in.readInt();
	}

	/* (non-Javadoc)
	 * @see android.os.Parcelable#describeContents()
	 */
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(prefix);
		dest.writeString(suraId);
		dest.writeString(filePath);
		dest.writeLong(timeStamp);
		dest.writeInt(ayaNumber);
	}

	public static Parcelable.Creator<Record> CREATOR = new Creator<Record>() {

		@Override
		public Record[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Record[size];
		}

		@Override
		public Record createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Record(source);
		}
	};

}
