package org.gcn.plinguacore.simulator.probabilisticGuarded.scripts;

import org.gcn.plinguacore.util.PlinguaCoreException;

public class ButterflyEmigrationPsystemScriptG2 extends
		ButterflyEmigrationPsystemScript {

	
	protected float thirdMultiplicand;
	protected float kFactorial;
	
	protected ButterflyEmigrationPsystemScriptG2() throws PlinguaCoreException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	protected float calculatePyValue(float mu, int i) {
		kFactorial=factorial(k);
		float muFactorial=factorial(mu);
		float yFactorial=factorial(1.0f*i);
		float kyFactorial=factorial(k+1.0f*i-1.0f);
		float kPlusMuFactorial=factorial(k+mu);
		float pyMultiplicand=kyFactorial/yFactorial;
		float firstMultiplicand=pyMultiplicand/(k-1.0f);	
		float secondAuxMultiplicand=muFactorial/(kPlusMuFactorial*kFactorial);
		float secondMultiplicand=(float) Math.pow(secondAuxMultiplicand, i);
		thirdMultiplicand=0.0f;
		thirdMultiplicand=kFactorial/(kPlusMuFactorial*muFactorial);
		thirdMultiplicand=(float) Math.pow(thirdMultiplicand, k);
		float result=firstMultiplicand*secondMultiplicand*thirdMultiplicand;
		return result;
	}
	


	
	protected float emigrationFactor(float omega, float mu) {
		return (float) (omega*(1-thirdMultiplicand));
	}

	static float logGamma(float x) {
		 double tmp = (x - 0.5) * Math.log(x + 4.5) - (x + 4.5);
		 double ser = 1.0 + 76.18009173    / (x + 0)   - 86.50532033    / (x + 1)
		          + 24.01409822    / (x + 2)   -  1.231739516   / (x + 3)
		          +  0.00120858003 / (x + 4)   -  0.00000536382 / (x + 5);
		  return (float) (tmp + Math.log(ser * Math.sqrt(2 * Math.PI)));
	}
	static float gamma(float x) { return (float)Math.exp(logGamma(x)); }
	
	static float factorial(float x) { 
		if(x==0.0f||x==1.0f)
			return 1.0f;
		else
			return x*gamma(x); }
}
