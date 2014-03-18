package com.ringdroid;

import com.badlogic.gdx.audio.analysis.FFT;

public class Fourier {
	/** Called when the activity is first created. */

	public Fourier() {

	}

	// fs = frequency
	public void calcSpec(int[] audioBuf, int fs) {
		fs = 8000; // untuk sementara

		int nshift = 0;// Initialise frame shift
		int nlen = 0;// Initialise frame length

		nshift = (int) Math.floor(4.0 * fs / 1000); // frame shift in samples
		nlen = audioBuf.length;

		calc(audioBuf, nshift, nlen);
		// aufioBuf = data ;nshift = nshift ; nlen = seglen

	}

	public void calc(int[] data, int nshift, int seglen) {
		count = 0;
		spec = new float[seglen];
		array2 = new float[seglen];
		seg_len = seglen;
		n_shift = nshift;
		time_array = new int[data.length];
		time_array = data;

		framed = new float[seg_len];
		framed = FrameSig();
		minmax(framed, seg_len);
		meansig(seg_len);

		int timeSize = (int) Math.pow(2, 16);

		// System.out.println("TIMESIZE = " + timeSize + " " +seg_len);
		array = new float[timeSize];
		res = new float[seg_len];
		real = new double[seg_len];
		imag = new double[seg_len];
		mag = new double[seg_len];

		for (int i = 0; i < seg_len * 2; i++) {
			array[i] = 0;
		}

		FFT fft = new FFT(timeSize, 8000);

		for (int i = 0; i < seg_len; i++) {
			array[i] = framed[i];
		}

		fft.forward(array);
		fft_cpx = fft.getSpectrum();
		tmpi = fft.getImaginaryPart();
		tmpr = fft.getRealPart();

		for (int i = 0; i < seg_len; i++) {
			// System.out.println("indeks i = " + i + "seglen = " + seg_len );
			real[i] = (double) tmpr[i];
			imag[i] = (double) tmpi[i];
			// System.out.println(real[i] + " " +imag[i]);
			mag[i] = Math.sqrt((real[i] * real[i]) + (imag[i] * imag[i]));
			// System.out.println("Magnitude Val = " + count + " = " + mag[i]);

			mag[i] = Math.abs(mag[i] / seg_len);
			// System.out.println("Magnitude abs = " + count + " = " + mag[i]);
			count = count + 1;
		}
		magnitude = new double[count];
		magnitude = mag;
		clearArray();
	}

	/**
	 * Calculates the mean of the fft magnitude spectrum
	 * 
	 * @param nsegs
	 */
	private void meanspec(int nsegs) {
		float sum = 0;
		for (int i = 0; i < seg_len; i++) {
			sum += spec[i];
		}

		sum = sum / (nsegs * seg_len);
		mux = sum;
	}

	/**
	 * Calculates the min and max of the fft magnitude spectrum
	 * 
	 * @param spec
	 * @param seglen
	 * @param nsegs
	 * @return
	 */
	public static float minmaxspec(float[][] spec, int seglen, int nsegs) {

		smin = (float) 1e35;
		smax = (float) -1e35;
		for (int j = 1; j < nsegs; j++) {
			for (int i = 0; i < seglen; i++) {

				if (smax < spec[i][j]) {
					smax = spec[i][j]; // new maximum
				} else if (smin > spec[i][j]) {
					smin = spec[i][j]; // new maximum
				}
			}
		}
		return smax;
	}

	/**
	 * Calculates the min and max value of the framed signal
	 * 
	 * @param spec
	 * @param seglen
	 * @param nsegs
	 * @return
	 */
	public static float minmax(float[] spec, int seglen) {

		min = (float) 1e35;
		max = (float) -1e35;

		for (int i = 0; i < seglen; i++) {

			if (max < spec[i]) {
				max = spec[i]; // new maximum
			} else if (min > spec[i]) {
				min = spec[i]; // new maximum
			}
		}

		return max;
	}

	/**
	 * Calculates the mean of the framed signal
	 * 
	 * @param nsegs
	 */
	private void meansig(int nsegs) {
		float sum = 0;
		for (int j = 1; j < (int) nsegs; j++) {
			for (int i = 0; i < seg_len; i++) {

				sum += framed[i];
			}
		}

		sum = sum / (nsegs * seg_len);
		smux = sum;

	}

	/**
	 * Frames up input audio
	 * 
	 * @return
	 */

	public float[] FrameSig() {
		float[] frame = new float[seg_len];
		float padlen = n_shift + seg_len;

		wn = hamming(seg_len);

		for (int i = 0; i < seg_len; i++) { // Windowing
			frame[i] = time_array[i] * wn[i];

		}
		return frame;
	}

	/**
	 * Calculates a hamming window to reduce spectral leakage
	 * 
	 * @param len
	 * @return
	 */
	public float[] hamming(int len) {
		float[] win = new float[len];
		for (int i = 0; i < len; i++) {
			win[i] = (float) (0.54 - 0.46 * Math.cos((2 * Math.PI * i)
					/ (len - 1)));
		}
		return win;
	}

	public void clearArray() {
		spec = null;
		array = null;
		array2 = null;
		time_array = null;
		framed = null;
		res = null;
		real = null;
		imag = null;
		mag = null;
		tmpi = null;
		tmpr = null;
	}

	public void clearMagnitude() {
		magnitude = null;
	}

	int count;
	float[] res = null;
	float[] fft_cpx, tmpr, tmpi;
	double[] real = null;
	double[] imag = null;
	double[] mag = null;
	double[] magnitude = null;
	double[] phase = null;
	double[] logmag = null;
	static float[] framed;
	static int n, seg_len, n_shift;
	int[] time_array;
	float[] array;
	float[] wn;
	double[] nmag;
	static float[] spec;
	float[] array2;
	static float max;
	static float min;
	static float smax;
	static float smin;
	static float mux;
	static float smux;
	/**
	 * @return the magnitude
	 */
	public double[] getMagnitude() {
		return magnitude;
	}
}