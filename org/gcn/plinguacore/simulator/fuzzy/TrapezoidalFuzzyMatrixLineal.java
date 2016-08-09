package org.gcn.plinguacore.simulator.fuzzy;

import static org.gcn.plinguacore.simulator.fuzzy.Trapezoidal.*;

public class TrapezoidalFuzzyMatrixLineal implements TrapezoidalFuzzyMatrix {
	
	public boolean timesMaxMinWithMatrix(float[] m1, float[] m2, float[] result, int m1rows, int m1cols, int m2rows, int m2cols, boolean max)
	{

		// m1 is float matrix
		// m2 is trapezoidal matrix
		// result is trapezoidal matrix


			boolean inputCondition =
					m1rows >= 1 && m2rows >= 1 &&
					m1cols >= 1 && m2cols >= 1 &&
					m1cols == m2rows;

			if(!inputCondition)
				return false;

			int r = m1rows;
			int s = m2rows;
			int t = m2cols;

			// auxiliar is trapezoidal

			float[] auxiliar = new float[TRAPL];

			int i,j;

			for(i = 0; i < r; i++)
				for(j = 0; j < t; j++)
				{
					if(max)
					{
						int caux;

						for(caux = 0; caux < TRAPL; caux++)
							auxiliar[caux] = 0.0f;	// min fuzzy value
					}
					else
					{
						int caux;

						for(caux = 0; caux < TRAPL; caux++)
							auxiliar[caux] = 1.0f;	// max fuzzy value
					}


					boolean found = false;

					int counter;

					for(counter = 0; counter < s; counter++)
					{
						float scalar = m1[i*m1cols+counter];

						if(scalar != 0)
						{
							found = true;

							float[] temp = new float[TRAPL];

							int caux;

							for(caux = 0; caux < TRAPL; caux++)
								temp[caux] = scalar*m2[(counter*m2cols+j)*TRAPL+caux];

							if(max)
							{
								caux = 0;

								for(caux = 0; caux < TRAPL; caux++)
									if(temp[caux] > auxiliar[caux])
										auxiliar[caux] = temp[caux];

							}
							else
							{
								caux = 0;

								for(caux = 0; caux < TRAPL; caux++)
									if(temp[caux] < auxiliar[caux])
										auxiliar[caux] = temp[caux];
							}

						}

					}

					if(!found)
					{
						int caux;

						for(caux = 0; caux < TRAPL; caux++)
							auxiliar[caux] = 0.0f;	// min fuzzy value
					}

					int caux;

					for(caux = 0; caux < TRAPL; caux++)
						result[(i*t+j)*TRAPL+caux] = auxiliar[caux];
				}

			return true;
		}

	public boolean timesMaxMinWithVector(float[] m1, float[] v2, float[] result, int m1rows, int m1cols, int v2rows,boolean max)
	{
		return timesMaxMinWithMatrix(m1,v2,result,m1rows,m1cols,v2rows,1,max);
	}

	public boolean diagonalMultiplication(float[] m1, float[] v2, float[] result, int m1rows, int m1cols, int v2rows)
	{

		// m1 is a trapezoidal matrix
		// v2 is a trapezoidal vector
		// result is a trapezoidal vector

		boolean inputCondition = m1rows >= 1 && v2rows >= 1 && m1cols >= 1
				//&& m2[0].length >= 1
				&& m1rows == m1cols	// m1 is a (square) diagonal matrix
				&& m1cols == v2rows;

		if(!inputCondition)
			return false;

		int r = m1rows;

		int i;

		for (i = 0; i < r; i++) {

			int caux;

			for(caux = 0; caux < TRAPL; caux++)
				result[i*TRAPL+caux] = m1[(i*r + i)*TRAPL + caux] * v2[i*TRAPL + caux];
	       }

		return true;
	}

	public boolean multiplicationWithMatrix(float[] m1, float[] m2, float[] result, int m1rows, int m1cols, int m2rows, int m2cols)
	{

		// m1 is float matrix
		// m2 is Trapezoidal vector
		// result is Trapezoidal matrix

		boolean inputCondition =
				m1rows >= 1 &&
				m2rows >= 1 &&
				m1cols >= 1 &&
				m2cols >= 1 &&
				m1cols == m2rows;

		if(!inputCondition)
			return false;

		int r = m1rows;
		int s = m2rows;
		int t = m2cols;

		int i,j,k;

		for (i = 0; i < r; i++) {
	           for (j = 0; j < t; j++) {

	        	   float[] parcial = new float[TRAPL];

	        	   int caux;

	        	   for(caux = 0; caux < TRAPL; caux++)
	        		   parcial[caux] = 0;

	        	   for (k = 0; k < s; k++) {

	        		   float m1aux = m1[i*s + k];

	        		   for(caux = 0; caux < TRAPL; caux++)
	        			   parcial[caux] = parcial[caux] + m1aux * m2[(k*t+j)*TRAPL + caux];
	               }

	    		   for(caux = 0; caux < TRAPL; caux++)
	        	   	   result[(i*t + j)*TRAPL + caux] = parcial[caux];
	           }
	       }

		return true;
	}

	public boolean multiplicationWithVector(float[] m1, float[] v2, float[] result, int m1rows, int m1cols, int v2rows)
	{
		return multiplicationWithMatrix(m1,v2,result,m1rows,m1cols,v2rows,1);
	}

	public boolean additionWithVector(float[] v1, float[] v2, float[] result, int v1rows, int v2rows)
	{

		// v1 is trapezoidal vector
		// v2 is trapezoidal vector
		// result is trapezoidal vector

		boolean inputCondition =
				v1rows >= 1 && v2rows >= 1 &&
				v1rows == v2rows;

		if(!inputCondition)
			return false;

		int r = v1rows;

		int i;

		for(i = 0; i < r; i++)
		{
			int caux;

			for(caux = 0; caux < TRAPL; caux++)
				result[i*TRAPL + caux] = v1[i*TRAPL + caux] + v2[i*TRAPL + caux];
		}

		return true;

	}

	public boolean transpose(float[] m, float[] result, int mrows, int mcols, int resrows, int rescols)
	{

		// m is float matrix
		// result is float matrix

		boolean inputCondition = mrows >= 1 && mcols >= 1 && resrows >= 1 && rescols >= 1 && mrows == rescols && mcols == resrows;

		if(!inputCondition)
			return false;

		int r = mrows;
		int s = mcols;

		int i,j;

		for (i = 0; i < r; i++)
	           for (j = 0; j < s; j++)
	        	   result[j*r + i] = m[i*s + j];

		return true;
	}

	public void printMatrixTrap(String name, float[] m, int mrows, int mcols)
	{

		// m is a trapezoidal matrix

		System.out.print(name + " =\n");

	        int rows = mrows;
	        int columns = mcols;

	        int i,j;

	        for(i=0;i<rows;i++){
	        	System.out.print("|\t");
	            for(j=0;j<columns;j++){

	                System.out.print("(");

	                int caux;

	                for(caux = 0; caux < TRAPL - 1; caux++)
	                	System.out.print(m[(i*columns + j)*TRAPL + caux] + ",");

	                System.out.print(m[(i*columns + j)*TRAPL + TRAPL - 1]);

	                System.out.print(")\t");
	            }
	            System.out.print("|\n");
	        }

	}

	public void printMatrixDouble(String name, float[] m, int mrows, int mcols)
	{

		System.out.print(name + " =\n");

	        int rows = mrows;
	        int columns = mcols;

	        int i,j;

	        for(i=0;i<rows;i++){
	        	System.out.print("|\t");
	            for(j=0;j<columns;j++){
	                System.out.print(m[i*columns + j] + "\t");
	            }
	            System.out.print("|\n");
	        }

	}

	public void initializeMatrix(float[] m, int mrows, int mcols)
	{
		boolean inputCondition =
					mrows >= 1 && mcols >= 1;

		if(!inputCondition)
				return;

		int i = 0;
		int max = mrows*mcols*TRAPL;

		for(i = 0; i < max; i++)
			m[i] = 0.0f;

	}

	public boolean isMin(float[] v, int pos, int vrows)
	{
		boolean isMin = true;

		int caux = 0;

		while(caux < TRAPL && isMin)
		{
			isMin = (v[pos*TRAPL + caux] == 0.0);
			caux++;
		}

		return isMin;
	}

}
