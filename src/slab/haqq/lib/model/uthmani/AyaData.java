/**
 * 
 */
package slab.haqq.lib.model.uthmani;

/**
 * @author rasxen
 * 
 */
public class AyaData {

	private String Uthmanitext;
	private int suraNumber, ayaNumber;
	private String bismillah;
	private String translation;

	/**
	 * TODO : Documentation
	 */
	public AyaData() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * TODO : Documentation
	 * @param uthmanitext
	 * @param suraNumber
	 * @param ayaNumber
	 */
	public AyaData(String uthmanitext, int suraNumber, int ayaNumber) {
		Uthmanitext = uthmanitext;
		this.suraNumber = suraNumber;
		this.ayaNumber = ayaNumber;
	}

	/**
	 * TODO : Documentation
	 * @return the uthmanitext
	 */
	public String getUthmanitext() {
		return (ayaNumber == 1) ? ((suraNumber > 1) ? bismillah + "\n"
				+ Uthmanitext : Uthmanitext) : Uthmanitext;
	}

	/**
	 * @param uthmanitext
	 *            the uthmanitext to set
	 */
	public void setUthmanitext(String uthmanitext) {
		Uthmanitext = uthmanitext;
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
	 * @return the bismillah
	 */
	public String getBismillah() {
		return bismillah;
	}

	/**
	 * @param bismillah
	 *            the bismillah to set
	 */
	public void setBismillah(String bismillah) {
		this.bismillah = bismillah;
	}

	/**
	 * @return the translation
	 */
	public String getTranslation() {
		return translation;
	}

	/**
	 * @param translation
	 *            the translation to set
	 */
	public void setTranslation(String translation) {
		this.translation = translation;
	}

}
