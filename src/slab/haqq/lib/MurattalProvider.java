/**
 * 
 */
package slab.haqq.lib;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

/**
 * @author rasxen
 * <p><h1>MurattalProvider</h1></p>
 * <p>A provider of murattal used for evaluation</p>
 */
public class MurattalProvider {
	final static private String MurattalPath = "HaqqData/murattal/";
	private static List<MurattalProperties> murattalList = new ArrayList<MurattalProvider.MurattalProperties>();
	private static Map<String, MurattalProperties> murattalMap = new HashMap<String, MurattalProvider.MurattalProperties>();

	/**
	 * a MurattalProvider constructor, clearing collection
	 * @param context
	 */
	public MurattalProvider() {
		// TODO Auto-generated constructor stub
		murattalList.clear();
		murattalMap.clear();
	}

	/**
	 * init murattaldata from murattal folder
	 * @param context
	 */
	public int refreshMurattalData(Context context) {
		murattalList.clear();
		murattalMap.clear();
		int count = 0;
		File files = new File(Environment.getExternalStorageDirectory()+"/"
				+ MurattalPath);
		if (!files.exists()) {
			boolean created = files.mkdirs();
			Log.v("Murattal", "Not exist, create new folder"+files.getAbsolutePath()+String.valueOf(created));
			return -3;
		} else if (!files.isDirectory()) {
			Log.v("Murattal", "Not a folder");
			return -2;
		} else {
			File[] murattalFolder =  files.listFiles(new FileFilter() {
				
				@Override
				public boolean accept(File arg0) {
					// TODO Auto-generated method stub
					if(arg0.isDirectory()){
						return true;
					}
					return false;
				}
			});
			Log.v("Murattal", String.valueOf(murattalFolder.length));
			for(int i=0; i < murattalFolder.length;i++){
				File properties = new File(murattalFolder[i].getAbsolutePath(),"properties.xml");
				if(properties.exists()){
					try {
						DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance()
								.newDocumentBuilder();
						Document xmlDoc = docBuilder.parse(properties);
						
						Element rootElement = xmlDoc.getDocumentElement();
						if(rootElement.getTagName().equals("murattal")){
							NodeList childs = rootElement.getChildNodes();
							MurattalProperties newProperties = new MurattalProperties();
							boolean skip = false;
							for(int j = 0; j< childs.getLength(); j++){
								Node el = (Node)childs.item(j);
								if(el.getNodeName().equals("name")){
									newProperties.setName(el.getTextContent());
									Log.v("Murattal name", el.getTextContent());
								}
								if(el.getNodeName().equals("value")){
									newProperties.setValue(el.getTextContent());
									Log.v("Murattal value", el.getTextContent());
								}
								if(el.getNodeName().equals("format")){
									if(el.getTextContent().equals("mp3")||el.getTextContent().equals("wav")||el.getTextContent().equals("amr")||el.getTextContent().equals("aac")){	
										skip = false;
										Log.v("Murattal format", "supported format, not skipped");
									}
									else{
										skip = true;
										Log.v("Murattal format", "unsupported format, skipped");
									}
									newProperties.setFormat(el.getTextContent());
									Log.v("Murattal format", el.getTextContent());
								}
								if(el.getNodeName().equals("bismillah")){
									if(el.getTextContent().equals("1"))
										newProperties.setBismillah(true);
									else
										newProperties.setBismillah(false);
									Log.v("Murattal", el.getTextContent());
								}
								if(el.getNodeName().equals("audhubillah")){
									if(el.getTextContent().equals("1"))
										newProperties.setAudhubillah(true);
									else
										newProperties.setAudhubillah(false);
									Log.v("Murattal", el.getTextContent());
								}
							}
							if(!skip){
								Log.v("murattal", "add murrattal"+newProperties.getValue());
								murattalList.add(newProperties);
								murattalMap.put(newProperties.getValue(), newProperties);
							}
							count++;
						}
						else{
							continue;
						}
						
					} catch (ParserConfigurationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SAXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return count;
	}

	/**
	 * get murattal sound from active murattal for selected sura and aya
	 * @param id
	 * @param sura
	 * @param aya
	 * @return
	 */
	public String getMurattalFilePath(String id, int sura, int aya) {
		Log.v("murattal", Environment.getExternalStorageDirectory().getAbsolutePath() +"/"
				+ MurattalPath + id + "/"
				+ convertNumberToStringWithPrefix(sura) + "/"
				+ convertNumberToStringWithPrefix(aya) + "."
				+ murattalMap.get(id).getFormat());
		return Environment.getExternalStorageDirectory().getAbsolutePath() +"/"
				+ MurattalPath + id + "/"
				+ convertNumberToStringWithPrefix(sura) + "/"
				+ convertNumberToStringWithPrefix(aya) + "."
				+ murattalMap.get(id).getFormat();
	}

	/**
	 * get Bismillah sound from active murattal
	 * @param id
	 * @return
	 */
	public String getMurattalBismillah(String id) {
		return (murattalMap.get(id).isBismillah()) ? Environment
				.getExternalStorageDirectory().getAbsolutePath() +"/"
				+ MurattalPath
				+ id
				+ "/bismillah." + murattalMap.get(id).getFormat() : "?";
	}

	/**
	 * get Audhubillah sound from active murattal
	 * @param id
	 * @return
	 */
	public String getMurattalAudhubillah(String id) {
		return (murattalMap.get(id).isAudhubillah()) ? Environment
				.getExternalStorageDirectory().getAbsolutePath() +"/"
				+ MurattalPath
				+ id
				+ "/audhubillah." + murattalMap.get(id).getFormat() : "?";
	}

	/**
	 * @param num
	 * @return
	 */
	private String convertNumberToStringWithPrefix(int num) {
		String result = String.valueOf(num);
		while (result.length() < 3) {
			result = "0".concat(result);
		}
		return result;
	}

	/**
	 * @author rasxen
	 * a murattal model data
	 */
	public class MurattalProperties {
		private String name;
		private String value;
		private String format;
		private boolean bismillah;
		private boolean audhubillah;

		/**
		 * @param name
		 * @param value
		 * @param format
		 * @param bismillah
		 * @param audhubillah
		 */
		public MurattalProperties(String name, String value, String format,
				boolean bismillah, boolean audhubillah) {
			this.name = name;
			this.value = value;
			this.format = format;
			this.bismillah = bismillah;
			this.audhubillah = audhubillah;
		}
		
		/**
		 * 
		 */
		public MurattalProperties() {
			
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * @param value
		 *            the value to set
		 */
		public void setValue(String value) {
			this.value = value;
		}

		/**
		 * @return the format
		 */
		public String getFormat() {
			return format;
		}

		/**
		 * @param format
		 *            the format to set
		 */
		public void setFormat(String format) {
			this.format = format;
		}

		/**
		 * @return the bismillah
		 */
		public boolean isBismillah() {
			return bismillah;
		}

		/**
		 * @param bismillah
		 *            the bismillah to set
		 */
		public void setBismillah(boolean bismillah) {
			this.bismillah = bismillah;
		}

		/**
		 * @return the audhubillah
		 */
		public boolean isAudhubillah() {
			return audhubillah;
		}

		/**
		 * @param audhubillah
		 *            the audhubillah to set
		 */
		public void setAudhubillah(boolean audhubillah) {
			this.audhubillah = audhubillah;
		}

	}

}
