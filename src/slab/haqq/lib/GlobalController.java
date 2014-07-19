/**
 * 
 */
package slab.haqq.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jqurantree.orthography.Chapter;
import org.jqurantree.orthography.Document;

import slab.haqq.SplashHaqq;
import slab.haqq.SplashHaqq.SplashTask;
import slab.haqq.lib.adapter.RecordAdapter;
import slab.haqq.lib.adapter.ResultAdapter;
import slab.haqq.lib.adapter.model.Sura;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

/**
 * @author rasxen
 * <p><h1>GlobalController</h1></p>
 * <p>A Global controller of this Application</p>
 */
public final class GlobalController {
	public final static int PREPARING_CODE = 0x00;
	public final static int PARSING_XML_CODE = 0x01;
	public final static int BULDING_MODEL_CODE = 0x02;
	public final static int FINISHING_CODE = 0x03;

	public final static String PREPARING = "Preparing...";
	public final static String PARSING_XML = "Parsing XML";
	public final static String BUILDING_MODEL = "Adding Data to Model";
	public final static String FINISHING = "Put Finishing Touch";

	public final static String AUDIO_RECORDER_FOLDER = "record";

	public final static String AUDIO_RECORDER_FILE_EXT_WAV = ".wav";

	public final static String AUDIO_RECORDER_TEMP_FILE = "record_temp.raw";
	public final static int RECORDER_CHANNELS = 0xc;
	public final static int RECORDER_SAMPLERATE = 0xac44;
	public final static int RECORDER_AUDIO_ENCODING = 0x2;
	public final static int RECORDER_BPP = 0x10;

	public static String PREFIX = "HAQQ";
	
	public final static String WITH_WAQF_HARAKA = "1";
	public final static String WITH_HARAKA_WITHOUT_WAQF = "0";
	public final static String WITHOUT_ALL = "-1";
	
	public final static String NOTIFICATION = "1";
	public final static String DIRECT = "0";
	

	public static String APP_EXT_PATH;

	// Splash screen timer
	public static int SPLASH_TIME_OUT = 3000;
	// SuraList
	public static List<Sura> SuraList = new ArrayList<Sura>();
	// SuraMap
	public static Map<String, Sura> SuraMap = new HashMap<String, Sura>();
	// Init Code
	private static int initCode = PREPARING_CODE;
	// Init Message
	private static String initTag = PREPARING;
	private static String initMessage = "";
	// ResultSnapshot
	public final static String RES_SNAPSHOT_FOLDER = "HaqqSnapshot";
	
	public final static String HAQQ_DATA_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/HaqqData"; 
	
	public final static int startSuraId = 77;

	// Handler list
	public static RecordProvider recordProvider;
	public static ResultProvider resultProvider;
	public static RecordAdapter recordAdapter;
	public static ResultAdapter resultAdapter;
	public static MurattalProvider murattalProvider;

	/**
	 * init this application, called only in {@link SplashHaqq} activity (default init)
	 * @param context
	 * @param task
	 */
	public static void init(SplashTask task, Context context) {
		if (initCode != FINISHING_CODE) {
			boolean oneTime = false;
			
			APP_EXT_PATH = context.getExternalFilesDir(null).getAbsolutePath();
			// init xml
			System.setProperty("org.xml.sax.driver",
					"org.xmlpull.v1.sax2.Driver");
			initTag = PARSING_XML;
			initMessage = "Parsing Qur'an Properties";
			initCode = PARSING_XML_CODE;
			task.updateProgress();
			
			new QuranPropertiesProvider(context);
			
			Log.v("init",
					String.valueOf(QuranPropertiesProvider.sProperties.size()));
			initMessage = "Parsing Qur'an Data From Tanzil";
			task.updateProgress();
			
			new UthmaniTextProvider(context);
			for (Chapter ch : Document.getChapters()) {
				if (oneTime == false) {
					initTag = BUILDING_MODEL;
					initCode = BULDING_MODEL_CODE;
					oneTime = true;
					task.updateProgress();
				}
				Log.v("init",
						QuranPropertiesProvider.sProperties.get(
								ch.getChapterNumber() - 1).getTname());
				initMessage = "Adding "
						+ QuranPropertiesProvider.sProperties.get(
								ch.getChapterNumber() - 1).getTname();
				task.updateProgress();
				AddSura(new Sura(String.valueOf(ch.getChapterNumber()),
						ch.getChapterNumber(),
						QuranPropertiesProvider.sProperties.get(
								ch.getChapterNumber() - 1).getTname(), ch
								.getName().toUnicode(), ch.getVerseCount()));
				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			murattalProvider = new MurattalProvider();
			murattalProvider.refreshMurattalData(context);
			task.updateProgress();
			recordProvider = new RecordProvider(context);
			resultProvider = new ResultProvider(context);
			task.updateProgress();
			initTag = FINISHING;
			initMessage = "";
			initCode = FINISHING_CODE;
			task.updateProgress();
		}
	}
	
	/**
	 * init this application, called everywhere activity (only if needed)
	 * @param context
	 */
	public static void init(Context context) {
		if (initCode != FINISHING_CODE) {
			boolean oneTime = false;

			APP_EXT_PATH = context.getExternalFilesDir(null).getAbsolutePath();
			// init xml
			System.setProperty("org.xml.sax.driver",
					"org.xmlpull.v1.sax2.Driver");
			initTag = PARSING_XML;
			initMessage = "Parsing Qur'an Properties";
			initCode = PARSING_XML_CODE;

			new QuranPropertiesProvider(context);
			
			Log.v("init",
					String.valueOf(QuranPropertiesProvider.sProperties.size()));
			initMessage = "Parsing Qur'an Data From Tanzil";
			
			new UthmaniTextProvider(context);
			for (Chapter ch : Document.getChapters()) {
				if (oneTime == false) {
					initTag = BUILDING_MODEL;
					initCode = BULDING_MODEL_CODE;
					oneTime = true;
				}
				Log.v("init",
						QuranPropertiesProvider.sProperties.get(
								ch.getChapterNumber() - 1).getTname());
				initMessage = "Adding "
						+ QuranPropertiesProvider.sProperties.get(
								ch.getChapterNumber() - 1).getTname();

				AddSura(new Sura(String.valueOf(ch.getChapterNumber()),
						ch.getChapterNumber(),
						QuranPropertiesProvider.sProperties.get(
								ch.getChapterNumber() - 1).getTname(), ch
								.getName().toUnicode(), ch.getVerseCount()));
			}
			
			murattalProvider = new MurattalProvider();
			murattalProvider.refreshMurattalData(context);
			
			recordProvider = new RecordProvider(context);
			resultProvider = new ResultProvider(context);

			initTag = FINISHING;
			initMessage = "";
			initCode = FINISHING_CODE;
		}
	}

	/**
	 * @param item
	 */
	private static void AddSura(Sura item) {
		SuraList.add(item);
		SuraMap.put(item.getId(), item);
	}

	/**
	 * @return the initCode
	 */
	public static int getInitCode() {
		return initCode;
	}

	/**
	 * @return the initTag
	 */
	public static String getInitTag() {
		return initTag;
	}

	/**
	 * @return the initMessage
	 */
	public static String getInitMessage() {
		return initMessage;
	}
}
