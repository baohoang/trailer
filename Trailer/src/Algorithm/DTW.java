package Algorithm;

import Const.Const;

public class DTW {
	public static double DTW_betwween(listPoint t1, listPoint t2) {
		int size = Const.winsize;
		double matrix[][] = new double[200][200];
		int i, j;
		for (i = 0; i < size; i++) {
			for (j = 0; j < size; j++) {
				matrix[i][j] = DistanceFunction.calcDistance(t1.get(i),
						t2.get(j));
			}
		}
		double result[][] = new double[200][200];
		result[0][0] = matrix[0][0];
		for (i = 1; i < size; i++) {
			result[i][0] = matrix[i][0] + result[i - 1][0];
			result[0][i] = matrix[0][i] + result[0][i - 1];
		}
		for (i = 1; i < size; i++) {
			for (j = 1; j < size; j++) {
				result[i][j] = matrix[i][j]
						+ Math.min(
								Math.min(result[i - 1][j], result[i][j - 1]),
								result[i - 1][j - 1]);
			}
		}
		return result[size - 1][size - 1];
	}

}
