/**
 * 
 */
package slab.haqq.lib.model.uthmani;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rasxen
 * <p><h1>SuraData</h1></p>
 * <p>A Container of sura data of Uthmani Text for Qur'an</p>
 */
public class SuraData {

	private int suraNumber;
	private String suraName;
	private List<AyaData> ayaTextList;

	/**
	 * Empty Constructor for SuraData
	 */
	public SuraData() {
		ayaTextList = new ArrayList<AyaData>();
	}

	/**
	 * Parameterized Constructor for SuraData
	 * @param suraNumber
	 * @param suraName
	 */
	public SuraData(int suraNumber, String suraName) {
		this.suraNumber = suraNumber;
		this.suraName = suraName;
		ayaTextList = new ArrayList<AyaData>();
	}

	/**
	 * Get Uthmani text of using parameter to get it from this sura collection of {@link AyaData}
	 * @param ayaNumber
	 * @return {@link AyaData} getUthmaniText()
	 */
	public String getAyaText(int ayaNumber) {
		return ayaTextList.get(ayaNumber - 1).getUthmanitext();
	}

	/**
	 * Get default translation of using parameter to get it from this sura collection of {@link AyaData}
	 * @param ayaNumber
	 * @return {@link AyaData} getTranslation()
	 */
	public String getAyaTrans(int ayaNumber) {
		return ayaTextList.get(ayaNumber - 1).getTranslation();
	}

	/**
	 * Get {@link AyaData} using parameter to get it from this sura collection of {@link AyaData}
	 * @param ayaNumber
	 * @return
	 */
	public AyaData getAya(int ayaNumber) {
		return ayaTextList.get(ayaNumber - 1);
	}

	/**
	 * Add {@link AyaData} to this sura collection of {@link AyaData} 
	 * @param aya
	 */
	public void addAya(AyaData aya) {
		ayaTextList.add(aya);
	}

	/**
	 * @return the suraNumber
	 */
	public int getSuraNumber() {
		return suraNumber;
	}

	/**
	 * @param suraNumber
	 *            the suraNumber to set
	 */
	public void setSuraNumber(int suraNumber) {
		this.suraNumber = suraNumber;
	}

	/**
	 * @return the suraName
	 */
	public String getSuraName() {
		return suraName;
	}

	/**
	 * @param suraName
	 *            the suraName to set
	 */
	public void setSuraName(String suraName) {
		this.suraName = suraName;
	}

}
