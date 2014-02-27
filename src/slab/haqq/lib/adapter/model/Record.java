/**
 * 
 */
package slab.haqq.lib.adapter.model;

/**
 * @author rasxen
 * 
 */
public class Record {
	private long timeStamp;
	private String suraId;
	private int ayaNumber;
	private String prefix;
	private String filePath;

	/**
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

	public String toString() {
		return prefix + "_" + suraId + "_" + String.valueOf(ayaNumber) + "_"
				+ String.valueOf(timeStamp);
	}

}
