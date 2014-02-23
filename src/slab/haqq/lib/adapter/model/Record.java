/**
 * 
 */
package slab.haqq.lib.adapter.model;

/**
 * @author rasxen
 *
 */
public class Record {
	private String rid;
	private int timeStamp;
	private String suraId;
	private int ayaNumber;
	private String prefix;
	
	/**
	 * @param id
	 * @param timeStamp
	 * @param suraId
	 * @param ayaNumber
	 * @param prefix
	 */
	public Record(String id, int timeStamp, String suraId, int ayaNumber,
			String prefix) {
		this.rid = id;
		this.timeStamp = timeStamp;
		this.suraId = suraId;
		this.ayaNumber = ayaNumber;
		this.prefix = prefix;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return rid;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.rid = id;
	}

	/**
	 * @return the timeStamp
	 */
	public int getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(int timeStamp) {
		this.timeStamp = timeStamp;
	}

	/**
	 * @return the suraId
	 */
	public String getSuraId() {
		return suraId;
	}

	/**
	 * @param suraId the suraId to set
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
	 * @param ayaNumber the ayaNumber to set
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
	 * @param prefix the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
}
