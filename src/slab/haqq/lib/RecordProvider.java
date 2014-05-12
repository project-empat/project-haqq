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
import java.util.HashMap;
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
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import slab.haqq.lib.adapter.model.Record;
import android.content.Context;

/**
 * @author rasxen
 * <p><h1>RecordProvider</h1></p>
 * <p>a provider for reading and editing xml {@link Record}</p>
 */
public class RecordProvider {
	public final static String RECORD_RES_NAME = "Haqq_Record.xml";
	public final static String RECORDS_LIST_ELEMENT = "records";
	public final static String RECORD_ELEMENT = "record";

	public List<Record> recordList = new ArrayList<Record>();
	public HashMap<String, Record> recordMap = new HashMap<String, Record>();

	/**
	 * a constructor of RecordProvider
	 * 
	 * @param context
	 */
	public RecordProvider(Context context) {
		recordList.clear();
		if (isRecordExist(context)) {
			readFromXML(context);
		} else {
			CreateRecordXMLs(context);
			readFromXML(context);
		}
	}

	/**
	 * init the xml for {@link Record}, only if the file is not exist/deleted
	 * 
	 * @param context
	 */
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
			StreamResult result = new StreamResult(new File(
					GlobalController.HAQQ_DATA_PATH, RECORD_RES_NAME));
			transformer.transform(source, result);
			// docBuilder.parse(new File(context.getExternalFilesDir(null),
			// RECORD_RES_NAME));
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

	/**
	 * check if {@link Record} xml is exist
	 * 
	 * @param context
	 * @return
	 */
	private boolean isRecordExist(Context context) {
		File file = new File(GlobalController.HAQQ_DATA_PATH, RECORD_RES_NAME);
		return file.exists();
	}

	/**
	 * add a new {@link Record} to xml
	 * 
	 * @param record
	 * @param context
	 */
	public void add(Record record, Context context) {
		recordList.add(record);
		recordMap.put(record.toString(), record);
		writeToXML(context, record);
		GlobalController.recordAdapter.notifyDataSetChanged();
	}

	/**
	 * delete {@link Record} from xml
	 * 
	 * @param record
	 * @param context
	 */
	public void deleteRecord(Record record, Context context) {
		recordList.remove(getRecordPosition(record));
		recordMap.remove(record.toString());
		deleteFromXML(context, record);
		File file = new File(record.getFilePath());
		if (file.exists()) {
			file.delete();
		}
		GlobalController.recordAdapter.notifyDataSetChanged();
		GlobalController.resultProvider.deleteScore(context,
				GlobalController.resultProvider.resultMap.get(record.toString()));
	}

	/**
	 * get {@link Record} position in the collection
	 * 
	 * @param record
	 * @return
	 */
	private int getRecordPosition(Record record) {
		int i = 0;
		for (i = 0; i < recordList.size(); i++) {
			if (recordList.get(i).toString().equals(record.toString())) {
				break;
			}
		}
		return i;
	}

	/**
	 * the actual processing function to write a new {@link Record} to xml
	 * 
	 * @param context
	 * @param record
	 */
	public void writeToXML(Context context, Record record) {
		try {
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document xmlDoc;
			File file = new File(GlobalController.HAQQ_DATA_PATH,
					RECORD_RES_NAME);

			if (!file.exists()) {
				xmlDoc = docBuilder.newDocument();
				xmlDoc.appendChild(xmlDoc.createElement(RECORDS_LIST_ELEMENT));
			} else {
				xmlDoc = docBuilder.parse(new File(
						GlobalController.HAQQ_DATA_PATH, RECORD_RES_NAME));
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
			StreamResult result = new StreamResult(new File(
					GlobalController.HAQQ_DATA_PATH, RECORD_RES_NAME));
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

	/**
	 * the actual processing function to delete {@link Record} from xml
	 * 
	 * @param context
	 * @param record
	 */
	public void deleteFromXML(Context context, Record record) {
		try {
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document xmlDoc;
			File file = new File(GlobalController.HAQQ_DATA_PATH,
					RECORD_RES_NAME);

			if (!file.exists()) {
				xmlDoc = docBuilder.newDocument();
				xmlDoc.appendChild(xmlDoc.createElement(RECORDS_LIST_ELEMENT));
			} else {
				xmlDoc = docBuilder.parse(new File(
						GlobalController.HAQQ_DATA_PATH, RECORD_RES_NAME));
			}

			Element rootElement = xmlDoc.getDocumentElement();
			XPath xpath = XPathFactory.newInstance().newXPath();
			XPathExpression expr = xpath.compile("//" + RECORD_ELEMENT
					+ "[@timestamp=\"" + String.valueOf(record.getTimeStamp())
					+ "\"]");
			Node node = (Node) expr.evaluate(xmlDoc, XPathConstants.NODE);

			rootElement.removeChild(node);
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(xmlDoc);
			StreamResult result = new StreamResult(new File(
					GlobalController.HAQQ_DATA_PATH, RECORD_RES_NAME));
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
		} catch (XPathExpressionException e) {
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

	/**
	 * @param context
	 */
	private void readFromXML(Context context) {
		try {
			XMLReader reader = XMLReaderFactory.createXMLReader();
			reader.setContentHandler(new RecordHandler());

			InputStream stream = new FileInputStream(new File(
					GlobalController.HAQQ_DATA_PATH, RECORD_RES_NAME));
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

	/**
	 * @author rasxen
	 * a sax event handler when reading {@link Record} xml
	 */
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
				recordList.add(rec);
				recordMap.put(rec.toString(), rec);
			}
		}
	}

}
