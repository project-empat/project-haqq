/**
 * 
 */
package slab.haqq.lib.model.uthmani;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rasxen
 * 
 */
public class SuraData {

	private int suraNumber;
	private String suraName;
	private List<AyaData> ayaTextList;

	/**
	 * TODO : Documentation
	 */
	public SuraData() {
		ayaTextList = new ArrayList<AyaData>();
	}

	/**
	 * TODO : Documentation
	 * @param suraNumber
	 * @param suraName
	 */
	public SuraData(int suraNumber, String suraName) {
		this.suraNumber = suraNumber;
		this.suraName = suraName;
		ayaTextList = new ArrayList<AyaData>();
	}

	/**
	 * TODO : Documentation
	 * @param ayaNumber
	 * @return
	 */
	public String getAyaText(int ayaNumber) {
		return ayaTextList.get(ayaNumber - 1).getUthmanitext();
	}

	/**
	 * TODO : Documentation
	 * @param ayaNumber
	 * @return
	 */
	public String getAyaTrans(int ayaNumber) {
		return ayaTextList.get(ayaNumber - 1).getTranslation();
	}

	/**
	 * TODO : Documentation
	 * @param ayaNumber
	 * @return
	 */
	public AyaData getAya(int ayaNumber) {
		return ayaTextList.get(ayaNumber - 1);
	}

	/**
	 * TODO : Documentation
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
