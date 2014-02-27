package com.ringdroid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.os.Environment;

public class Skoring {
	double pitScore;
	double volScore;
	double rhyScore;
	double[] energyValue;
	boolean isNewRecord;

	public Skoring() {

	}

	public double getPitchScore(double Dist) {
		int divisor = 0;
		if (Dist < 1.5) {
			divisor = 60;
		}
		if (Dist < 2) {
			divisor = 50;
		} else if (Dist < 2.5) {
			divisor = 40;
		} else if (Dist < 3) {
			divisor = 30;
		} else if (Dist < 3.5) {
			divisor = 20;
		} else if (Dist < 4) {
			divisor = 10;
		} else {
			divisor = 3;
		}

		double powValue = -1.0 * Dist / divisor;
		// System.out.println("powValue = " + powValue);
		pitScore = Math.round(100 * (Math.pow(100, (powValue))));
		// System.out.println("Pit score = " + pitScore);
		return pitScore;
	}

	public double getVolScore(double Dist) {
		int divisor = 0;
		if (Dist < 2.7) {
			divisor = 80;
		} else if (Dist < 3) {
			divisor = 60;
		} else if (Dist < 3.3) {
			divisor = 40;
		} else if (Dist < 3.5) {
			divisor = 30;
		} else if (Dist < 3.7) {
			divisor = 20;
		} else if (Dist < 3.9) {
			divisor = 10;
		} else {
			divisor = 3;
		}
		double powValue = -1.0 * Dist / divisor;
		// System.out.println("powValue = " + powValue);
		volScore = Math.round(100 * (Math.pow(100, (powValue))));
		// System.out.println("Pit score = " + pitScore);
		return volScore;
	}

	public double getRhyScore(double inBeat) {
		// System.out.println("GETRHYSCORE = " + inBeat);
		rhyScore = Math.round(100 * (inBeat));
		// System.out.println("SCORE = " + rhyScore);
		return rhyScore;
	}

	public int getTotal() {
		double total = Math.round(0.45 * pitScore + 0.16 * volScore + 0.39
				* rhyScore);
		return (int) Math.round(total);
	}

	public double[] calcEnergy(int[] framedSignal) {
		energyValue = new double[framedSignal.length];

		for (int i = 0; i < framedSignal.length; i++) {
			// find log
			if (framedSignal[i] != 0) {
				energyValue[i] = Math.log(Math.pow(framedSignal[i], 2));
			}

		}
		return energyValue;
	}

	public void clearArray() {
		energyValue = null;
	}

	public String writeLogic(String[] text, String song, String score) {
		isNewRecord = false;
		int i = 0;
		int scoreTemp = 0;
		int scoreText = 0;
		String temp = "";
		if (text == null) {
			temp = song + "\n" + score + "\n";
			isNewRecord = true;
		} else {
			while (i < text.length) {
				temp += text[i] + "\n";
				if (text[i].equals(song)) {
					scoreTemp = Integer.parseInt(score);
					scoreText = Integer.parseInt(text[i + 1]);
					// System.out.println(scoreTemp + " " +scoreText);
					if (scoreTemp > scoreText) {
						temp += score;
						isNewRecord = true;
					} else {
						temp += text[i + 1];
					}
					return temp;
				} else {
					i++;
				}
			}
			temp += song + "\n" + score;
		}
		return temp;
	}

	public void writeText(String song, String score) {
		String[] r = readText();
		String temp = writeLogic(r, song, score);
		String[] text = temp.split("\n");
		int i;
		try {
			// System.out.println("masuk try");
			File myFile = new File(Environment.getExternalStorageDirectory()
					.getPath() + "skor.txt");
			myFile.createNewFile();
			FileOutputStream fOut = new FileOutputStream(myFile);
			OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
			if (text != null) {
				for (i = 0; i < text.length; i++) {
					myOutWriter.append(text[i] + "\r\n");
				}
			}
			myOutWriter.close();
			fOut.close();
			;
		} catch (Exception e) {
		}
	}

	public String[] readText() {
		String aDataRow = "";
		String aBuffer = "";

		String[] text = null;
		try {

			File myFile = new File(Environment.getExternalStorageDirectory()
					.getPath() + "skor.txt");
			FileInputStream fIn = new FileInputStream(myFile);
			BufferedReader myReader = new BufferedReader(new InputStreamReader(
					fIn));
			while ((aDataRow = myReader.readLine()) != null) {
				aBuffer += aDataRow + "\n";
			}
			// System.out.println("ABUFFER = " + aBuffer);
			text = aBuffer.split("\n");
			myReader.close();
			return text;

		} catch (Exception e) {
			return null;
		}// onClick

	}

	public String readText2() {
		String aDataRow = "";
		String aBuffer = "";

		try {

			File myFile = new File(Environment.getExternalStorageDirectory()
					.getPath() + "skor.txt");
			FileInputStream fIn = new FileInputStream(myFile);
			BufferedReader myReader = new BufferedReader(new InputStreamReader(
					fIn));
			while ((aDataRow = myReader.readLine()) != null) {
				aBuffer += aDataRow + "\n";
			}
			myReader.close();
			return aBuffer;

		} catch (Exception e) {
			return null;
		}// onClick

	}
}
