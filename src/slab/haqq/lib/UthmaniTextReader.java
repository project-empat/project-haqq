/**
 * 
 */
package slab.haqq.lib;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jqurantree.core.resource.ResourceUtil;
import org.jqurantree.tanzil.TanzilReader;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import android.content.Context;
import android.util.Log;
import slab.haqq.lib.model.uthmani.AyaData;
import slab.haqq.lib.model.uthmani.SuraData;

/**
 * @author rasxen
 * 
 */
public class UthmaniTextReader {
	private static String TRANS_PATH = "xml/id.indonesian.xml";
	public static List<SuraData> suraList = new ArrayList<SuraData>();

	/**
	 * 
	 */
	public UthmaniTextReader(Context context) {
		// TODO Auto-generated constructor stub
		try {
			XMLReader xmlreader = XMLReaderFactory.createXMLReader();
			xmlreader.setContentHandler(new UthmaniTextHandler());

			InputStream stream = ResourceUtil
					.open(TanzilReader.TANZIL_RESOURCE_PATH);
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

	public static String getUthmaniText(int suraNumber, int ayaNumber) {
		return suraList.get(suraNumber - 1).getAyaText(ayaNumber);
	}

	public static String getTranslation(int suraNumber, int ayaNumber) {
		return suraList.get(suraNumber - 1).getAyaTrans(ayaNumber);
	}

	public class TranslationHandler extends DefaultHandler {
		private static final String TRANSLATION_ELEMENT = "translation";

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

	public class UthmaniTextHandler extends DefaultHandler {
		private static final String SURA_ELEMENT = "sura";
		private static final String AYA_ELEMENT = "aya";

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
