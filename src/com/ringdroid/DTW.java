package com.ringdroid;

public class DTW {

	private double[] seq1;
	private double[] seq2;

	private int[][] warpingPath;
	// protected double[][] dummy;
	private int n;
	private int m;
	private int K;

	private double warpingDistance;
	private double Distance;
	// private double[] DistArray;
	private double inBeat;
	private int beat;

	public void empty() {
	}

	/**
	 * Constructor
	 * 
	 * @param query
	 * @param templete
	 */

	public DTW(double[] sample, double[] template) {
		inBeat = 0;
		beat = 0;
		seq1 = sample;
		seq2 = template;
		warpingDistance = 0.0;
		Distance = 0.0;
		double distanceTemp = 0;
		n = seq1.length;
		m = seq2.length;
		System.out.println("NILAI n dan m = " + n + " " + m);

		if (n < 1000 && m < 1000) {
			K = 1;
			warpingPath = new int[n + m][2];
			compute();
			// System.out.println("NILAI 1 Distance = " + getDistance());

			// System.out.println("NILAI 1 Distance = " + getDistance());
			clearArray();
		} else {
			int nTemp;
			int mTemp;
			double[] temp;
			double[] temp2;

			int perKilo = n / 1000;
			int perKilo2 = m / 1000;
			int minim = perKilo;
			if (perKilo2 < perKilo) {
				minim = perKilo2;
			}
			System.out.println("perKilo 1 2= " + perKilo + " " + perKilo2);
			int count;

			for (count = 0; count <= minim; count++) {
				K = 1;
				// System.out.println("Count = " + count + " minim = " + minim);
				if (count == perKilo) {
					// System.out.println("Masuk 0");
					nTemp = sample.length - (perKilo * 1000);
					mTemp = template.length - (perKilo * 1000);

					temp = new double[nTemp];
					temp2 = new double[mTemp];
					// System.out.println("sample.length = " + sample.length +
					// " template.length = " + template.length);
					// System.out.println("temp = " + nTemp + " temp2 = " +
					// mTemp);
					for (int i = 0; i < temp.length; i++) {
						temp[i] = sample[i + (perKilo * 1000)];
						// System.out.println("i dan nilai " + i + " " +
						// (i+(perKilo*1000)) );
					}
					for (int i = 0; i < temp2.length; i++) {
						temp2[i] = template[i + (perKilo * 1000)];
					}
				} else {
					// System.out.println("Masuk else");
					nTemp = 1000;
					temp = new double[nTemp];
					temp2 = new double[nTemp];
					for (int i = 0; i < nTemp; i++) {
						temp[i] = sample[i + (count * 1000)];
					}
					for (int i = 0; i < nTemp && i < template.length; i++) {
						temp2[i] = template[i + (count * 1000)];
					}
				}
				seq1 = temp;
				seq2 = temp2;
				n = seq1.length;
				m = seq2.length;
				K = 1;
				warpingPath = new int[n + m][2];
				compute();
				distanceTemp += getDistance();

				// System.out.println("NILAI Distance = " + getDistance());
				// System.out.println("NILAI Total Distance = " + distanceTemp);
				warpingDistance = distanceTemp;
				temp = null;
				temp2 = null;
				clearArray();
			}// end looping
		}
	}

	public void compute() {
		double accumulatedDistance = 0.0;

		System.out.println("Inisiasi N dan M = " + n + " " + m);
		double[][] d = new double[n][m]; // local distances
		double[][] D = new double[n][m]; // global distances

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				d[i][j] = distanceBetween(seq1[i], seq2[j]);
			}
			// System.out.println("Distance " + seq1[i] + " " +seq2[i] + " "
			// +d[i][i] );
		}

		D[0][0] = d[0][0];
		for (int i = 1; i < n; i++) {
			D[i][0] = d[i][0] + D[i - 1][0];
		}

		for (int j = 1; j < m; j++) {
			D[0][j] = d[0][j] + D[0][j - 1];
			// D[0][j] = 0;
		}

		// DistArray = new double[n*m];
		for (int i = 1; i < n; i++) {
			for (int j = 1; j < m; j++) {
				accumulatedDistance = Math.min(
						Math.min(D[i - 1][j], D[i - 1][j - 1]), D[i][j - 1]);
				accumulatedDistance += d[i][j];
				D[i][j] = accumulatedDistance;

			}
		}
		accumulatedDistance = D[n - 1][m - 1];
		// dummy = D;
		int i = n - 1;
		int j = m - 1;
		int minIndex = 1;

		warpingPath[K - 1][0] = i;
		warpingPath[K - 1][1] = j;

		while ((i + j) != 0) {
			// System.out.println("Nilai index minimum d = " +d[i][j] + " " +i +
			// " " +j);
			// System.out.println("Nilai index minimum = " +D[i][j] + " " +i +
			// " " +j);
			// System.out.println("seq = " + seq1[i] + " " + seq2[j] + " " + i +
			// " " +j);

			if (d[i][j] <= 5 && (seq1[i] > 0 && seq2[j] > 0)) {
				inBeat += 1;
				// System.out.println("COUNT 1");
			} else if (seq1[i] == 0 && seq2[j] == 0) {
				beat -= 1;
				// System.out.println("COUNT 2");
			} else {
				// System.out.println("COUNT 3");
			}

			beat++;

			if (i == 0) {
				j -= 1;
			} else if (j == 0) {
				i -= 1;
			} else { // i != 0 && j != 0
				double[] array = { D[i - 1][j], D[i][j - 1], D[i - 1][j - 1] };
				minIndex = this.getIndexOfMinimum(array);

				if (minIndex == 0) {
					i -= 1;
				} else if (minIndex == 1) {
					j -= 1;
				} else if (minIndex == 2) {
					i -= 1;
					j -= 1;
				}
			} // end else
			K++;
			warpingPath[K - 1][0] = i;
			warpingPath[K - 1][1] = j;
		} // end while
		Distance = accumulatedDistance;
		warpingDistance = accumulatedDistance / K;
		// System.out.println("Warping = " + warpingDistance);
		d = null;
		D = null;
		clearArray();
		// this.reversePath(warpingPath);
	}

	protected void reversePath(int[][] path) {
		int[][] newPath = new int[K][2];
		for (int i = 0; i < K; i++) {
			for (int j = 0; j < 2; j++) {
				newPath[i][j] = path[K - i - 1][j];
			}
		}
		warpingPath = newPath;
	}

	/**
	 * Returns the warping distance
	 * 
	 * @return
	 */
	public double getDistance() {
		return warpingDistance;
	}

	public double getDistanceDTW() {
		return Distance;
	}

	public double getInBeat() {
		System.out.println("Get beat = " + inBeat + " " + beat);
		return inBeat / beat;
	}

	/**
	 * Computes a distance between two points
	 * 
	 * @param p1
	 *            the point 1
	 * @param p2
	 *            the point 2
	 * @return the distance between two points
	 */
	protected double distanceBetween(double p1, double p2) {
		// System.out.println("NILAI p1 dan p2 = " + p1 + " " +p2);
		// System.out.println(Math.sqrt((p1 - p2) * (p1 - p2)));
		return Math.sqrt((p1 - p2) * (p1 - p2));
	}

	/**
	 * Finds the index of the minimum element from the given array
	 * 
	 * @param array
	 *            the array containing numeric values
	 * @return the min value among elements
	 */
	protected int getIndexOfMinimum(double[] array) {
		int index = 0;
		double val = array[0];

		for (int i = 1; i < array.length; i++) {
			if (array[i] < val) {
				val = array[i];
				index = i;
			}
		}
		return index;
	}

	public void clearArray() {
		// DistArray = null;
		seq1 = null;
		seq2 = null;
		warpingPath = null;

	}
	/**
	 * Returns a string that displays the warping distance and path
	 */
	// public String toString() {
	// String retVal = "Warping Distance: " + warpingDistance + "\n";
	// retVal += "DTW Distance : " + Distance + "\n";
	// retVal += "Warping Path: {";
	// for (int i = 0; i < K; i++) {
	// retVal += "(" + warpingPath[i][0] + ", " +warpingPath[i][1] + ")";
	// retVal += (i == K - 1) ? "}\n" : ", ";
	// }
	// retVal += "Nilai Warping Path: {";
	// for (int i = 0; i < K; i++) {
	// retVal += "(" + dummy[warpingPath[i][0]][warpingPath[i][1]] + ")";
	// retVal += (i == K - 1) ? "}" : ", ";
	// }
	// return retVal;
	// }
}
