/**
 * 
 */
package slab.haqq.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import android.content.Context;
import slab.haqq.lib.adapter.model.Record;

/**
 * @author rasxen
 * 
 */
public class RecordController {
	public final static String RECORD_RES_NAME = "Haqq_Record.xml";
	public final static String RECORDS_LIST_ELEMENT = "records";
	public final static String RECORD_ELEMENT = "record";

	public List<Record> recList = new ArrayList<Record>();

	/**
	 * 
	 */
	public RecordController(Context context) {
		// TODO Auto-generated constructor stub
		recList.clear();
		if (isRecordExist(context)) {
			readFromXML(context);
		} else {
			CreateRecordXMLs(context);
			readFromXML(context);
		}
	}

	private void CreateRecordXMLs(Context context) {
		try {
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document xmlDoc = docBuilder.newDocument();
			xmlDoc.appendChild(xmlDoc.createElement(RECORDS_LIST_ELEMENT));
			
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(xmlDoc);
			StreamResult result = new StreamResult(new File(context.getExternalFilesDir(null),
					RECORD_RES_NAME));
			transformer.transform(source, result);
			//docBuilder.parse(new File(context.getExternalFilesDir(null), RECORD_RES_NAME));
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean isRecordExist(Context context) {
		File file = new File(context.getExternalFilesDir(null), RECORD_RES_NAME);
		return file.exists();
	}

	public void add(Record record, Context context) {
		recList.add(record);
		writeToXML(context, record);
	}

	public void writeToXML(Context context, Record record) {
		try {
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document xmlDoc;
			File file = new File(context.getExternalFilesDir(null), RECORD_RES_NAME);
			
			if(!file.exists()){
				xmlDoc = docBuilder.newDocument();
				xmlDoc.appendChild(xmlDoc.createElement(RECORDS_LIST_ELEMENT));
			}else{
				xmlDoc = docBuilder.parse(new File(context.getExternalFilesDir(null), RECORD_RES_NAME));
			}
			
			Element rootElement = xmlDoc.getDocumentElement();
			Element recElement = xmlDoc.createElement(RECORD_ELEMENT);
			
			Attr timestamp, sura, aya, prefix, path;
			
			timestamp = xmlDoc.createAttribute("timestamp");
			timestamp.setValue(String.valueOf(record.getTimeStamp()));
			
			sura = xmlDoc.createAttribute("sura");
			sura.setValue(record.getSuraId());
			
			aya = xmlDoc.createAttribute("aya");
			aya.setValue(String.valueOf(record.getAyaNumber()));
			
			prefix = xmlDoc.createAttribute("prefix");
			prefix.setValue(record.getPrefix());
			
			path = xmlDoc.createAttribute("path");
			path.setValue(record.getFilePath());
			
			recElement.setAttributeNode(timestamp);
			recElement.setAttributeNode(prefix);
			recElement.setAttributeNode(sura);
			recElement.setAttributeNode(aya);
			recElement.setAttributeNode(path);
			
			rootElement.appendChild(recElement);
			
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(xmlDoc);
			StreamResult result = new StreamResult(new File(context.getExternalFilesDir(null),
					RECORD_RES_NAME));
			transformer.transform(source, result);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	private void readFromXML(Context context) {
		try {
			XMLReader reader = XMLReaderFactory.createXMLReader();
			reader.setContentHandler(new RecordHandler());

			InputStream stream = new FileInputStream(new File(
					context.getExternalFilesDir(null), RECORD_RES_NAME));
			InputSource source = new InputSource(stream);

			reader.parse(source);
			stream.close();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public class RecordHandler extends DefaultHandler {
		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			// TODO Auto-generated method stub
			if (qName.equals(RECORD_ELEMENT)) {
				Record rec = new Record(Long.parseLong(attributes
						.getValue("timestamp")), attributes.getValue("sura"),
						Integer.parseInt(attributes.getValue("aya")),
						attributes.getValue("prefix"));
				rec.setFilePath(attributes.getValue("path"));
				recList.add(rec);
			}
		}
	}

}
