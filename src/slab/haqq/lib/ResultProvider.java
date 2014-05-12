package slab.haqq.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import slab.haqq.lib.adapter.model.Result;
import android.content.Context;

/**
 * @author rasxen
 * <p><h1>ResultProvider</h1></p>
 * <p>a provider for reading and editing xml of {@link Result}</p>
 */
public class ResultProvider {
	public final static String RESULT_RES_NAME = "Haqq_Result.xml";
	public final static String RESULTS_LIST_ELEMENT = "results";
	public final static String RESULT_ELEMENT = "result";

	public List<Result> resultList = new ArrayList<Result>();
	public Map<String, Result> resultMap = new HashMap<String, Result>();

	/**
	 * a constructor of ResultProvider
	 * 
	 * @param context
	 */
	public ResultProvider(Context context) {
		// TODO Auto-generated constructor stub
		resultList.clear();
		if (isResultExist(context)) {
			readFromXML(context);
		} else {
			CreateResultXMLs(context);
			readFromXML(context);
		}
	}

	/**
	 * Add new result/modify a result to/in xml
	 * 
	 * @param context
	 * @param id
	 * @param p
	 * @param r
	 * @param v
	 * @param recog
	 * @param recogText
	 */
	public Result addScore(Context context, String id, double p, double r,
			double v, double recog, String recogText, String rstate) {
		Result result = new Result(id, p, r, v, recog, recogText, rstate);
		if (resultMap.containsKey(result.getRstId())) {
			result = resultMap.get(id);
			result.setScorePitch(p);
			result.setScoreRecog(recog);
			result.setScoreRhythm(r);
			result.setScoreVolume(v);	
			if(result.getResultState().equalsIgnoreCase(Result.BASIC)){
				result.setResultState(rstate);
				result.setRecogText(recogText);
			}
			resultMap.remove(result.getRstId());
			resultMap.put(result.getRstId(), result);
			int i = getResultPosition(result);
			resultList.remove(i);
			resultList.add(i, result);
			updateXML(context, result);
		} else {
			resultMap.put(result.getRstId(), result);
			resultList.add(result);
			writeXML(context, result);
		}
		return result;
		// GlobalController.resultAdapter.notifyDataSetChanged();
	}

	/**
	 * deleting result from xml
	 * 
	 * @param context
	 * @param result
	 */
	public void deleteScore(Context context, Result result) {
		if (result != null) {
			resultMap.remove(result.getRstId());
			resultList.remove(getResultPosition(result));
			deleteFromXML(context, result);
			GlobalController.resultAdapter.notifyDataSetChanged();
		}
	}

	/** 
	 * @param res
	 * @return
	 */
	private int getResultPosition(Result res) {
		int i = 0;
		for (i = 0; i < resultList.size(); i++) {
			if (resultList.get(i).getRstId().equals(res.getRstId())) {
				break;
			}
		}
		return i;
	}

	/**
	 * the actual processing function for modifying {@link Result} in xml
	 * using DOM and XPath
	 * @param context
	 * @param result
	 */
	private void updateXML(Context context, Result result) {

		try {
			DocumentBuilder docBuilder;
			docBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document xmlDoc;
			File file = new File(GlobalController.HAQQ_DATA_PATH,
					RESULT_RES_NAME);

			if (!file.exists()) {
				xmlDoc = docBuilder.newDocument();
				xmlDoc.appendChild(xmlDoc.createElement(RESULTS_LIST_ELEMENT));
			} else {
				xmlDoc = docBuilder.parse(new File(
						GlobalController.HAQQ_DATA_PATH, RESULT_RES_NAME));
			}

			XPath xpath = XPathFactory.newInstance().newXPath();
			XPathExpression expr = xpath.compile("//" + RESULT_ELEMENT
					+ "[@id=\"" + result.getRstId() + "\"]");
			Node node = (Node) expr.evaluate(xmlDoc, XPathConstants.NODE);

			NamedNodeMap attrs = node.getAttributes();
			attrs.getNamedItem("pitch").setNodeValue(
					String.valueOf(result.getScorePitch()));
			attrs.getNamedItem("rhythm").setNodeValue(
					String.valueOf(result.getScoreRhythm()));
			attrs.getNamedItem("volume").setNodeValue(
					String.valueOf(result.getScoreVolume()));
			attrs.getNamedItem("recog").setNodeValue(
					String.valueOf(result.getScoreRecog()));
			attrs.getNamedItem("recogText").setNodeValue(result.getRecogText());
			attrs.getNamedItem("resultState").setNodeValue(result.getResultState());

			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(xmlDoc);
			StreamResult outresult = new StreamResult(new File(
					GlobalController.HAQQ_DATA_PATH, RESULT_RES_NAME));
			transformer.transform(source, outresult);
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
	 * the actual processing function for adding {@link Result} in xml
	 * using DOM and XPath
	 * @param context
	 * @param result
	 */
	private void writeXML(Context context, Result result) {
		try {
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document xmlDoc;
			File file = new File(GlobalController.HAQQ_DATA_PATH,
					RESULT_RES_NAME);

			if (!file.exists()) {
				xmlDoc = docBuilder.newDocument();
				xmlDoc.appendChild(xmlDoc.createElement(RESULTS_LIST_ELEMENT));
			} else {
				xmlDoc = docBuilder.parse(new File(GlobalController.HAQQ_DATA_PATH,
						RESULT_RES_NAME));
			}

			Element rootElement = xmlDoc.getDocumentElement();
			Element recElement = xmlDoc.createElement(RESULT_ELEMENT);

			Attr id, pitch, rhythm, vol, recog, recogText, resultState;

			id = xmlDoc.createAttribute("id");
			id.setValue(result.getRstId());

			pitch = xmlDoc.createAttribute("pitch");
			pitch.setValue(String.valueOf(result.getScorePitch()));

			rhythm = xmlDoc.createAttribute("rhythm");
			rhythm.setValue(String.valueOf(result.getScoreRhythm()));

			vol = xmlDoc.createAttribute("volume");
			vol.setValue(String.valueOf(result.getScoreVolume()));

			recog = xmlDoc.createAttribute("recog");
			recog.setValue(String.valueOf(result.getScoreRecog()));

			recogText = xmlDoc.createAttribute("recogText");
			recogText.setValue(result.getRecogText());
			
			resultState = xmlDoc.createAttribute("resultState");
			resultState.setValue(result.getResultState());

			recElement.setAttributeNode(id);
			recElement.setAttributeNode(pitch);
			recElement.setAttributeNode(rhythm);
			recElement.setAttributeNode(vol);
			recElement.setAttributeNode(recog);
			recElement.setAttributeNode(recogText);
			recElement.setAttributeNode(resultState);

			rootElement.appendChild(recElement);

			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(xmlDoc);
			StreamResult outresult = new StreamResult(new File(
					GlobalController.HAQQ_DATA_PATH, RESULT_RES_NAME));
			transformer.transform(source, outresult);
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
	 * the actual processing function for deleting {@link Result} in xml
	 * using DOM and XPath
	 * @param context
	 * @param result
	 */
	public void deleteFromXML(Context context, Result result) {
		try {
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document xmlDoc;
			File file = new File(GlobalController.HAQQ_DATA_PATH,
					RESULT_RES_NAME);

			if (!file.exists()) {
				xmlDoc = docBuilder.newDocument();
				xmlDoc.appendChild(xmlDoc.createElement(RESULTS_LIST_ELEMENT));
			} else {
				xmlDoc = docBuilder.parse(new File(GlobalController.HAQQ_DATA_PATH, RESULT_RES_NAME));
			}

			Element rootElement = xmlDoc.getDocumentElement();
			XPath xpath = XPathFactory.newInstance().newXPath();
			XPathExpression expr = xpath.compile("//" + RESULT_ELEMENT
					+ "[@id=\"" + result.getRstId() + "\"]");
			Node node = (Node) expr.evaluate(xmlDoc, XPathConstants.NODE);

			rootElement.removeChild(node);
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(xmlDoc);
			StreamResult outresult = new StreamResult(new File(
					GlobalController.HAQQ_DATA_PATH, RESULT_RES_NAME));
			transformer.transform(source, outresult);
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
	 * init the xml for {@link Result}, only if the file is not exist/deleted
	 * 
	 * @param context
	 */
	private void CreateResultXMLs(Context context) {
		try {
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document xmlDoc = docBuilder.newDocument();
			xmlDoc.appendChild(xmlDoc.createElement(RESULTS_LIST_ELEMENT));

			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(xmlDoc);
			StreamResult result = new StreamResult(new File(
					GlobalController.HAQQ_DATA_PATH, RESULT_RES_NAME));
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
	 * check if {@link Result} xml is exist
	 * 
	 * @param context
	 * @return
	 */
	private boolean isResultExist(Context context) {
		File file = new File(GlobalController.HAQQ_DATA_PATH, RESULT_RES_NAME);
		return file.exists();
	}

	/**
	 * @param context
	 */
	private void readFromXML(Context context) {
		try {
			XMLReader reader = XMLReaderFactory.createXMLReader();
			reader.setContentHandler(new ResultHandler());

			InputStream stream = new FileInputStream(new File(
					GlobalController.HAQQ_DATA_PATH, RESULT_RES_NAME));
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
	 * a sax event handler when reading {@link Result} xml
	 */
	public class ResultHandler extends DefaultHandler {
		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			if (qName.equals(RESULT_ELEMENT)) {
				Result res = new Result(attributes.getValue("id"),
						Double.parseDouble(attributes.getValue("pitch")),
						Double.parseDouble(attributes.getValue("rhythm")),
						Double.parseDouble(attributes.getValue("volume")),
						Double.parseDouble(attributes.getValue("recog")),
						attributes.getValue("recogText"),attributes.getValue("resultState"));
				resultList.add(res);
				resultMap.put(res.getRstId(), res);
			}
		}
	}
	
	public File getResultFile() {
		return new File(GlobalController.HAQQ_DATA_PATH,RESULT_RES_NAME);
	}

}
