package slab.haqq;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.amr.arabic.ArabicUtilities;
import org.jqurantree.orthography.Document;
import org.jqurantree.orthography.Verse;

import slab.haqq.lib.GlobalController;
import slab.haqq.lib.UthmaniTextProvider;
import slab.haqq.lib.adapter.model.Record;
import slab.haqq.lib.adapter.model.Sura;
import android.app.Activity;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author rasxen
 * <p><h1>ReadAya Activity</h1></p>
 * <p>Qur'an reading ui handler, for single aya only
 * <br>use for reading and recording
 * </p>
 */
public class ReadAya extends Activity {
	private int suraNumber, ayaNumber;
	private Sura sura;

	private Button nextBtn, prevBtn, recordBtn;
	private TextView arText, transText;
	// private WebView arText;
	private String recordFilename;
	private AudioRecord recorder;
	private boolean isRecording = false;
	private Thread recordingThread;
	private int bufferSize;
	private Record recordModel;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read_aya);

		suraNumber = getIntent().getExtras().getInt("suraNumber");
		ayaNumber = getIntent().getExtras().getInt("ayaNumber");
		sura = getIntent().getExtras().getParcelable("suraData");

		nextBtn = (Button) findViewById(R.id.nextButton);
		prevBtn = (Button) findViewById(R.id.prevButton);
		recordBtn = (Button) findViewById(R.id.recordButton);

		nextBtn.setOnClickListener(nextListener);
		prevBtn.setOnClickListener(prevListener);
		recordBtn.setOnClickListener(recordListener);

		// arText = (WebView) findViewById(R.id.ayaTextAR);
		arText = (TextView) findViewById(R.id.ayaTextAR);
		transText = (TextView) findViewById(R.id.ayaTextTR);

		/*
		 * String textAR =
		 * "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\" />"
		 * +
		 * "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/css/text.css\">"
		 * +"</head>" +"<body><p>"+Document.getVerse(suraNumber,
		 * ayaNumber).toUnicode()+"</p></body></html>";
		 * 
		 * arText.loadData(textAR, "text/html", "utf-8")
		 */;

		ArabicUtilities.getArabicEnabledTextView(this, arText);
		updateView();
	}

	/**
	 * TODO : Documentation
	 */
	public void updateView() {
		arText.setText(getArabicText(suraNumber, ayaNumber) + " \uFD3F"
				+ ArabicUtilities.getArabicNumber(String.valueOf(ayaNumber))
				+ "\uFD3E");
		transText.setText(UthmaniTextProvider.getTranslation(suraNumber,
				ayaNumber));

		setTitle("Q.S. " + sura.getName() + " : \uFD3E"
				+ ArabicUtilities.getArabicNumber(String.valueOf(ayaNumber))
				+ "\uFD3F");
	}

	private String getArabicText(int suraN, int ayaN) {
		String text = "";
		Verse verse = Document.getVerse(suraN, ayaN);
		if (PreferenceManager.getDefaultSharedPreferences(this)
				.getString("arabictext_pref_list", "1")
				.equals(GlobalController.WITH_WAQF_HARAKA)) {
			text = UthmaniTextProvider.getUthmaniText(suraNumber, ayaNumber);
		} else if (PreferenceManager.getDefaultSharedPreferences(this)
				.getString("arabictext_pref_list", "1")
				.equals(GlobalController.WITH_HARAKA_WITHOUT_WAQF)) {
			text = verse.toUnicode();
		} else {
			text = verse.removeDiacritics().toUnicode();
		}
		return text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.read_aya, menu);
		return true;
	}

	/**
	 * TODO : Documentation
	 */
	private OnClickListener nextListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (!isRecording) {
				if (ayaNumber + 1 > sura.getAyaCount()) {
					if (suraNumber + 1 > GlobalController.SuraList.size()) {
						return;
					} else {
						ayaNumber = 1;
						suraNumber++;
						sura = GlobalController.SuraMap.get(String
								.valueOf(suraNumber));
					}
				} else {
					ayaNumber++;
				}
				updateView();
			} else {
				Toast.makeText(
						ReadAya.this,
						"You are currently recording. Please stop the recording first before navigating to other aya.",
						Toast.LENGTH_LONG).show();
			}
		}
	};

	/**
	 * TODO : Documentation
	 */
	private OnClickListener prevListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (!isRecording) {
				if (ayaNumber - 1 < 1) {
					if (suraNumber - 1 < 1) {
						return;
					} else {
						suraNumber--;
						sura = GlobalController.SuraMap.get(String
								.valueOf(suraNumber));
						ayaNumber = sura.getAyaCount();
					}
				} else {
					ayaNumber--;
				}
				updateView();
			} else {
				Toast.makeText(
						ReadAya.this,
						"You are currently recording. Please stop the recording first before navigating to other aya.",
						Toast.LENGTH_LONG).show();
			}
		}
	};

	/**
	 * TODO : Documentation
	 */
	private OnClickListener recordListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			if (!isRecording) {
				recordModel = new Record(System.currentTimeMillis(),
						String.valueOf(suraNumber), ayaNumber,
						GlobalController.PREFIX);
				// TODO Start recording audio
				startRecording();
				//
				recordBtn.setBackgroundResource(R.drawable.ic_stoprecord);
				isRecording = true;
			} else {
				stopRecording();
				recordModel.setFilePath(getFilename());
				GlobalController.recordProvider.add(recordModel, ReadAya.this);
				recordBtn.setBackgroundResource(R.drawable.ic_record);
				isRecording = false;
			}
		}
	};

	/**
	 * TODO : Documentation
	 * 
	 * @return
	 */
	private String getFilename() {
		File file = new File(GlobalController.HAQQ_DATA_PATH, GlobalController.AUDIO_RECORDER_FOLDER);

		if (!file.exists()) {
			file.mkdirs();
		}
		recordFilename = file.getAbsolutePath() + "/" + recordModel.toString()
				+ GlobalController.AUDIO_RECORDER_FILE_EXT_WAV;
		return recordFilename;
	}

	/**
	 * TODO : Documentation
	 * 
	 * @return
	 */
	private String getTempFilename() {
		String filepath = Environment.getExternalStorageDirectory().getPath();
		File file = new File(filepath, GlobalController.AUDIO_RECORDER_FOLDER);

		if (!file.exists()) {
			file.mkdirs();
		}

		File tempFile = new File(filepath,
				GlobalController.AUDIO_RECORDER_TEMP_FILE);

		if (tempFile.exists())
			tempFile.delete();

		return (file.getAbsolutePath() + "/" + GlobalController.AUDIO_RECORDER_TEMP_FILE);
	}

	/**
	 * TODO : Documentation
	 */
	private void startRecording() {
		System.out.println("RECORDER CHNNEL 4 = "
				+ GlobalController.RECORDER_CHANNELS);
		// TODO
		/*
		 * recordModel = new Record(System.currentTimeMillis(),
		 * String.valueOf(suraNumber), ayaNumber, GlobalController.PREFIX);
		 */

		bufferSize = AudioRecord.getMinBufferSize(
				GlobalController.RECORDER_SAMPLERATE,
				GlobalController.RECORDER_CHANNELS,
				GlobalController.RECORDER_AUDIO_ENCODING);
		recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
				GlobalController.RECORDER_SAMPLERATE,
				GlobalController.RECORDER_CHANNELS,
				GlobalController.RECORDER_AUDIO_ENCODING, bufferSize);

		recorder.startRecording();

		isRecording = true;

		recordingThread = new Thread(new Runnable() {

			@Override
			public void run() {
				writeAudioDataToFile();
			}
		}, "AudioRecorder Thread");

		recordingThread.start();
	}

	/**
	 * TODO : Documentation
	 */
	private void writeAudioDataToFile() {
		byte data[] = new byte[bufferSize];
		String filename = getTempFilename();

		FileOutputStream os = null;

		try {
			os = new FileOutputStream(filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int read = 0;

		if (null != os) {
			while (isRecording) {
				read = recorder.read(data, 0, bufferSize);

				if (AudioRecord.ERROR_INVALID_OPERATION != read) {
					try {
						os.write(data);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * TODO : Documentation
	 */
	private void stopRecording() {
		if (null != recorder) {
			isRecording = false;

			recorder.stop();
			recorder.release();

			recorder = null;
			recordingThread = null;
		}

		copyWaveFile(getTempFilename(), getFilename());
		deleteTempFile();
		// TODO
		// recordModel.setFilePath(getFilename());
		// RecordController.add(recordModel, this);
	}

	/**
	 * TODO : Documentation
	 */
	private void deleteTempFile() {
		File file = new File(getTempFilename());

		file.delete();
	}

	/**
	 * TODO : Documentation
	 * 
	 * @param inFilename
	 * @param outFilename
	 */
	private void copyWaveFile(String inFilename, String outFilename) {
		FileInputStream in = null;
		FileOutputStream out = null;
		long totalAudioLen = 0;
		long totalDataLen = totalAudioLen + 36;
		long longSampleRate = GlobalController.RECORDER_SAMPLERATE;
		int channels = 2;
		long byteRate = GlobalController.RECORDER_BPP
				* GlobalController.RECORDER_SAMPLERATE * channels / 8;

		byte[] data = new byte[bufferSize];

		try {
			in = new FileInputStream(inFilename);
			out = new FileOutputStream(outFilename);
			totalAudioLen = in.getChannel().size();
			totalDataLen = totalAudioLen + 36;
			System.out.println("File size: " + totalDataLen);

			WriteWaveFileHeader(out, totalAudioLen, totalDataLen,
					longSampleRate, channels, byteRate);

			while (in.read(data) != -1) {
				out.write(data);
			}

			in.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * TODO : Documentation
	 * 
	 * @param out
	 * @param totalAudioLen
	 * @param totalDataLen
	 * @param longSampleRate
	 * @param channels
	 * @param byteRate
	 * @throws IOException
	 */
	private void WriteWaveFileHeader(FileOutputStream out, long totalAudioLen,
			long totalDataLen, long longSampleRate, int channels, long byteRate)
			throws IOException {

		byte[] header = new byte[44];

		header[0] = 'R'; // RIFF/WAVE header
		header[1] = 'I';
		header[2] = 'F';
		header[3] = 'F';
		header[4] = (byte) (totalDataLen & 0xff);
		header[5] = (byte) ((totalDataLen >> 8) & 0xff);
		header[6] = (byte) ((totalDataLen >> 16) & 0xff);
		header[7] = (byte) ((totalDataLen >> 24) & 0xff);
		header[8] = 'W';
		header[9] = 'A';
		header[10] = 'V';
		header[11] = 'E';
		header[12] = 'f'; // 'fmt ' chunk
		header[13] = 'm';
		header[14] = 't';
		header[15] = ' ';
		header[16] = 16; // 4 bytes: size of 'fmt ' chunk
		header[17] = 0;
		header[18] = 0;
		header[19] = 0;
		header[20] = 1; // format = 1
		header[21] = 0;
		header[22] = (byte) channels;
		header[23] = 0;
		header[24] = (byte) (longSampleRate & 0xff);
		header[25] = (byte) ((longSampleRate >> 8) & 0xff);
		header[26] = (byte) ((longSampleRate >> 16) & 0xff);
		header[27] = (byte) ((longSampleRate >> 24) & 0xff);
		header[28] = (byte) (byteRate & 0xff);
		header[29] = (byte) ((byteRate >> 8) & 0xff);
		header[30] = (byte) ((byteRate >> 16) & 0xff);
		header[31] = (byte) ((byteRate >> 24) & 0xff);
		header[32] = (byte) (2 * 16 / 8); // block align
		header[33] = 0;
		header[34] = GlobalController.RECORDER_BPP; // bits per sample
		header[35] = 0;
		header[36] = 'd';
		header[37] = 'a';
		header[38] = 't';
		header[39] = 'a';
		header[40] = (byte) (totalAudioLen & 0xff);
		header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
		header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
		header[43] = (byte) ((totalAudioLen >> 24) & 0xff);

		out.write(header, 0, 44);
	}
}
