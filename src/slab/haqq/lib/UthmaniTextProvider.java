/**
 * 
 */
package slab.haqq.lib;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jqurantree.orthography.Document;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import slab.haqq.lib.model.uthmani.AyaData;
import slab.haqq.lib.model.uthmani.SuraData;
import android.content.Context;
import android.util.Log;

/**
 * @author rasxen
 * <p><h1>UthmaniTextProvider</h1></p>
 * <p>A provider for uthmani text, read from xml
 * <br>the xml is provided by {@linkplain tanzil.net} 
 * <br>have a collection {@link SuraData} for a complete display of uthmani text, 
 * <br>different from {@link Document} from jquran API which automatically remove its pause marks
 * </p>
 * <p>have two inner class {@link TranslationHandler} and {@link UthmaniTextHandler} a handler for sax event</p>
 */
public class UthmaniTextProvider {
	private static String TRANS_PATH = "xml/id.indonesian.xml";
	private static String QURAN_UTHMANI = "xml/quran-uthmani.xml";
	public static List<SuraData> suraList = new ArrayList<SuraData>();

	/**
	 * A UthmaniTextProvider constructor
	 * @param context
	 */
	public UthmaniTextProvider(Context context) {
		// TODO Auto-generated constructor stub
		try {
			XMLReader xmlreader = XMLReaderFactory.createXMLReader();
			xmlreader.setContentHandler(new UthmaniTextHandler());

			InputStream stream = context.getAssets().open(QURAN_UTHMANI);
			InputSource source = new InputSource(stream);

			xmlreader.parse(source);
			stream.close();

			xmlreader = XMLReaderFactory.createXMLReader();
			xmlreader.setContentHandler(new TranslationHandler());

			stream = context.getAssets().open(TRANS_PATH);
			source = new InputSource(stream);

			xmlreader.parse(source);
			stream.close();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Get uthmani text from its sura number and aya number
	 * @param suraNumber
	 * @param ayaNumber
	 * @return {@link AyaData} getUthmaniText()
	 */
	public static String getUthmaniText(int suraNumber, int ayaNumber) {
		return suraList.get(suraNumber - 1).getAyaText(ayaNumber);
	}

	/**
	 * Get translation from its sura number and aya number
	 * @param suraNumber
	 * @param ayaNumber
	 * @return {@link AyaData} getTranslation()
	 */
	public static String getTranslation(int suraNumber, int ayaNumber) {
		return suraList.get(suraNumber - 1).getAyaTrans(ayaNumber);
	}

	/**
	 * @author rasxen
	 * A sax event handler for reading translation data
	 */
	public class TranslationHandler extends DefaultHandler {
		private static final String TRANSLATION_ELEMENT = "translation";

		/* (non-Javadoc)
		 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
		 */
		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			// TODO Auto-generated method stub
			if (qName.equals(TRANSLATION_ELEMENT)) {
				Log.v("trans",
						attributes.getValue("sura") + " "
								+ attributes.getValue("aya"));
				suraList.get(Integer.parseInt(attributes.getValue("sura")) - 1)
						.getAya(Integer.parseInt(attributes.getValue("aya")))
						.setTranslation(attributes.getValue("text"));
				;
			}
		}
	}

	/**
	 * @author rasxen
	 * A sax event handler for reading uthmani text data
	 */
	public class UthmaniTextHandler extends DefaultHandler {
		private static final String SURA_ELEMENT = "sura";
		private static final String AYA_ELEMENT = "aya";

		/* (non-Javadoc)
		 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
		 */
		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			// TODO Auto-generated method stub
			if (qName.equals(SURA_ELEMENT)) {
				suraList.add(new SuraData(Integer.parseInt(attributes
						.getValue("index")), attributes.getValue("name")));
			} else if (qName.equals(AYA_ELEMENT)) {
				AyaData aya;
				aya = new AyaData(attributes.getValue("text"), suraList.get(
						suraList.size() - 1).getSuraNumber(),
						Integer.parseInt(attributes.getValue("index")));
				if (Integer.parseInt(attributes.getValue("index")) == 1
						&& suraList.size() > 1) {
					aya.setBismillah(attributes.getValue("bismillah"));
				}
				suraList.get(suraList.size() - 1).addAya(aya);
			}
		}
	}
}
