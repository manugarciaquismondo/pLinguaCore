package org.gcn.plinguacore.simulator.fuzzy;

public class RealFuzzyMatrixLineal implements RealFuzzyMatrix {
	
	
	public boolean maxMinWithMatrix(float[] m1, float[] m2, float[] result, int m1rows, int m1cols, int m2rows, int m2cols, boolean max)
	{
			boolean inputCondition = m1rows >= 1 && m2rows >= 1 && m1cols >= 1 && m2cols >= 1
					&& m1rows == m2rows && m1cols == m2cols;

			if(!inputCondition)
				return false;

			int r = m1rows;
			int s = m1cols;

			int i,j;

			for(i = 0; i < r; i++)
				for(j = 0; j < s; j++)
				{
					float a = m1[i*s + j];
					float b = m2[i*s + j];
					float res = 0.0f;
					
					if(a == 0 && b == 0)
						res = 0;
					else if(a > 0 && b == 0)
						res = a;
					else if(a == 0 && b > 0)
						res = b;
					else if(a > 0 && b > 0)
					{
						if(max)
						{
							if(a > b) res = a; else res = b;
						}
						else	// min
						{
							if(a < b) res = a; else res = b;
						}
					}
					
					result[i*s + j] = res;
					
				}

			return true;
	}

	public boolean timesMaxMinWithMatrix(float[] m1, float[] m2, float[] result, int m1rows, int m1cols, int m2rows, int m2cols, boolean max)
	{

			boolean inputCondition =
					m1rows >= 1 && m2rows >= 1 &&
					m1cols >= 1 && m2cols >= 1 &&
					m1cols == m2rows;

			if(!inputCondition)
				return false;

			int r = m1rows;
			int s = m2rows;
			int t = m2cols;

			float auxiliar = 0.0f;

			int i,j;

			for(i = 0; i < r; i++)
				for(j = 0; j < t; j++)
				{
					if(max)
						auxiliar = 0.0f; // min fuzzy value
					else
						auxiliar = 1.0f; // max fuzzy value

					boolean found = false;

					int counter;

					for(counter = 0; counter < s; counter++)
					{
						float scalar = m1[i*m1cols+counter];

						if(scalar != 0)
						{
							found = true;

							float temp = scalar*m2[counter*m2cols+j];

							if(max)
							{
								if(temp > auxiliar) auxiliar = temp;
							}
							else
							{
								if(temp < auxiliar) auxiliar = temp;
							}

						}

					}

					if(!found)
						auxiliar = 0;

					result[i*t+j] = auxiliar;
				}

			return true;
		}

	public boolean fire(float[] alpha, float[] a, float[] lambda, float[] out, float[] result, int alpha_rows, int a_rows, int lambda_rows, int out_rows)
	{
		boolean inputCondition = alpha_rows >= 1 && a_rows >= 1 && lambda_rows >= 1 && out_rows >= 1 &&
									alpha_rows == a_rows && a_rows == lambda_rows && lambda_rows == out_rows;

		if(!inputCondition)
			return false;

		int r = alpha_rows;

		int i;

		for(i = 0; i < r; i++)
		{
			float ali = alpha[i];
			float ai = a[i];
			float li = lambda[i];
			float oi = out[i];
			
			if(ai == li && oi == 1)
				result[i] = 0;
			else if(ai == li && oi == 0)
				result[i] = ali;
			else if(ai < li)
				result[i] = 0;
		}

		return true;

	}

	public boolean update(float[] alpha, float[] a, float[] lambda, float[] out, float[] result, int alpha_rows, int a_rows, int lambda_rows, int out_rows)
	{
		boolean inputCondition = alpha_rows >= 1 && a_rows >= 1 && lambda_rows >= 1 && out_rows >= 1 &&
				alpha_rows == a_rows && a_rows == lambda_rows && lambda_rows == out_rows;
		
		if(!inputCondition)
			return false;

		int r = alpha_rows;

		int i;

		for(i = 0; i < r; i++)
		{
			float ali = alpha[i];
			float ai = a[i];
			float li = lambda[i];
			float oi = out[i];
		
			if(ai == 0)
				result[i] = 0;
			else if(0 < ai && ai < li)
				result[i] = ali;
			else if(ai == li && oi == 1)
				result[i] = ali;
			else if(ai == li && oi == 0)
				result[i] = 0;
			
		}

		return true;

	}

	public boolean diagonal(float[] v, float[] result, int vrows)
	{
		boolean inputCondition = vrows >= 1;

		if(!inputCondition)
			return false;

		int r = vrows;

		int i,j;

		for(i = 0; i < r; i++)
			for(j = 0; j < r; j ++)
				if(i == j)
					result[i*r + j] = v[i];
				else
					result[i*r + j] = 0;

		return true;
	}

	public boolean additionWithMatrix(float[] m1, float[] m2, float[] result, int m1rows, int m1cols, int m2rows, int m2cols)
	{
		boolean inputCondition = m1rows >= 1 && m2rows >= 1 && m1cols >= 1 && m2cols >= 1
				&& m1rows == m2rows && m1cols == m2cols;

		if(!inputCondition)
			return false;

		int r = m1rows;
		int s = m1cols;

		int i,j;

		for(i = 0; i < r; i++)
			for(j = 0; j < s; j++)
			{
				result[i*s + j] = m1[i*s + j] + m2[i*s + j];
			}

		return true;

	}

	public boolean multiplicationWithMatrix(float[] m1, float[] m2, float[] result, int m1rows, int m1cols, int m2rows, int m2cols)
	{

		boolean inputCondition = m1rows >= 1 && m2rows >= 1 && m1cols >= 1 && m2cols >= 1 &&
				m1cols == m2rows;

		if(!inputCondition)
			return false;
		
		
		int r = m1rows;
		int s = m2rows;
		int t = m2cols;

		int i,j,k;

		for (i = 0; i < r; i++) {
	           for (j = 0; j < t; j++) {

	        	   float parcial = 0;

	        	   for (k = 0; k < s; k++) {
	                   parcial = parcial + m1[i*s + k] * m2[k*t + j];
	               }

	    		   result[i*t + j] = parcial;
	           }
	       }


		return true;
	}

	public boolean transpose(float[] m, float[] result, int mrows, int mcols, int resrows, int rescols)
	{

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

	public boolean makeUnitVector(float[] result, int dim)
	{
		boolean inputCondition = dim >= 1;

		if(!inputCondition)
			return false;

		int i;

		for(i = 0; i < dim; i++)
			result[i] = 1;

		return true;
	}

	public boolean makeZeroVector(float[] result, int dim)
	{
		boolean inputCondition = dim >= 1;

		if(!inputCondition)
			return false;

		int i;

		for(i = 0; i < dim; i++)
			result[i] = 0;

		return true;
	}

	public void printMatrix(String name, float[] m, int mrows, int mcols){

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


}
