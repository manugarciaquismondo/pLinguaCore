package org.gcn.plinguacore.simulator.fuzzy;

public interface TrapezoidalFuzzyMatrix {
	
	boolean timesMaxMinWithMatrix(float[] m1, float[] m2, float[] result, int m1rows, int m1cols, int m2rows, int m2cols, boolean max);
	boolean timesMaxMinWithVector(float[] m1, float[] v2, float[] result, int m1rows, int m1cols, int v2rows,boolean max);
	boolean diagonalMultiplication(float[] m1, float[] v2, float[] result, int m1rows, int m1cols, int v2rows);
	boolean multiplicationWithMatrix(float[] m1, float[] m2, float[] result, int m1rows, int m1cols, int m2rows, int m2cols);
	boolean multiplicationWithVector(float[] m1, float[] v2, float[] result, int m1rows, int m1cols, int v2rows);
	boolean additionWithVector(float[] v1, float[] v2, float[] result, int v1rows, int v2rows);
	boolean transpose(float[] m, float[] result, int mrows, int mcols, int resrows, int rescols);
	void printMatrixTrap(String name, float[] m, int mrows, int mcols);
	void printMatrixDouble(String name, float[] m, int mrows, int mcols);
	void initializeMatrix(float[] m, int mrows, int mcols);
	boolean isMin(float[] v, int pos, int vrows);

}
