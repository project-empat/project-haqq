/**
 * 
 */
package slab.haqq.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jqurantree.orthography.Chapter;
import org.jqurantree.orthography.Document;

import android.content.Context;
import android.util.Log;

/**
 * @author rasxen
 *
 */
public final class GlobalController {
	
	public static List<Sura> SuraList = new ArrayList<Sura>();
	
	public static Map<String,Sura> SuraMap = new HashMap<String, Sura>();
	
	public static void init(Context context) {
		
		//init xml
		System.setProperty("org.xml.sax.driver","org.xmlpull.v1.sax2.Driver");
		new QuranPropertiesReader(context);
		Log.v("init",String.valueOf(QuranPropertiesReader.sProperties.size()));
		for (Chapter ch : Document.getChapters()) {
			Log.v("init", QuranPropertiesReader.sProperties.get(ch.getChapterNumber()-1).getTname());
			AddSura(new Sura(String.valueOf(ch.getChapterNumber()), 
					ch.getChapterNumber(), 
					QuranPropertiesReader.sProperties.get(ch.getChapterNumber()-1).getTname(),
					ch.getName().toUnicode(),
					ch.getVerseCount()));
		}
		
	}
	
	private static void AddSura(Sura item) {
		SuraList.add(item);
		SuraMap.put(item.getId(), item);
	}
	
	public static class Sura{
		private String id;
		private int idn;
		private String name;
		private String arname;
		private int ayaCount;
		private String slug;
		/**
		 * Constructor
		 * @param id
		 * @param idn
		 * @param name
		 * @param ayaCount
		 */
		public Sura(String id, int idn, String name, String arname, int ayaCount) {
			Locale l = Locale.getDefault(); 
			this.id = id;
			this.idn = idn;
			this.name = name;
			this.arname = arname;
			this.slug = name.toLowerCase(l).replaceAll("\\P{L}+", "");
			this.ayaCount = ayaCount;
		}
		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(String id) {
			this.id = id;
		}
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * @return the ayaCount
		 */
		public int getAyaCount() {
			return ayaCount;
		}
		/**
		 * @param ayaCount the ayaCount to set
		 */
		public void setAyaCount(int ayaCount) {
			this.ayaCount = ayaCount;
		}
		/**
		 * @return the idn
		 */
		public int getIdn() {
			return idn;
		}
		/**
		 * @param idn the idn to set
		 */
		public void setIdn(int idn) {
			this.idn = idn;
		}
		/**
		 * @return the slug
		 */
		public String getSlug() {
			return slug;
		}
		/**
		 * @param slug the slug to set
		 */
		public void setSlug(String slug) {
			this.slug = slug;
		}
		/**
		 * @return the arname
		 */
		public String getArname() {
			return arname;
		}
		/**
		 * @param arname the arname to set
		 */
		public void setArname(String arname) {
			this.arname = arname;
		}
	}
}
