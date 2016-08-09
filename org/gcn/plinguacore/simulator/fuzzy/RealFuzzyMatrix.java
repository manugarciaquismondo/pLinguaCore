package org.gcn.plinguacore.simulator.fuzzy;

public interface RealFuzzyMatrix {


	boolean timesMaxMinWithMatrix(float[] m1, float[] m2, float[] result, int m1rows, int m1cols, int m2rows, int m2cols, boolean max);
	boolean maxMinWithMatrix(float[] m1, float[] m2, float[] result, int m1rows, int m1cols, int m2rows, int m2cols, boolean max);
	boolean fire(float[] alpha, float[] a, float[] lambda, float[] out, float[] result, int alpha_rows, int a_rows, int lambda_rows, int out_rows);
	boolean update(float[] alpha, float[] a, float[] lambda, float[] out, float[] result, int alpha_rows, int a_rows, int lambda_rows, int out_rows);
	boolean additionWithMatrix(float[] m1, float[] m2, float[] result, int m1rows, int m1cols, int m2rows, int m2cols);
	boolean multiplicationWithMatrix(float[] m1, float[] m2, float[] result, int m1rows, int m1cols, int m2rows, int m2cols);
	boolean diagonal(float[] v, float[] result, int vrows);
	boolean transpose(float[] m, float[] result, int mrows, int mcols, int resrows, int rescols);
	boolean makeUnitVector(float[] result, int dim);
	boolean makeZeroVector(float[] result, int dim);
	void printMatrix(String name, float[] m, int mrows, int mcols);
	
}
