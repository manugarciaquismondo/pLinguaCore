package org.gcn.plinguacore.simulator.probabilisticGuarded.scripts;

import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.PlinguaCoreException;

public class ButterflyEmigrationPsystemScript extends
		ButterflyEmigrationPsystemScriptBase {
	protected final String CURRENT_GENERATION="current_generation";
	protected final String BUTTERFLY_LAID_TEMP="butterflylaidtemp";
	protected final String IMMIGRANT="immigrant";
	protected float normalizedImmigrants[];
	protected long immigrants[];
	protected String generationArray[];
	protected String immigrantArray[];
	protected int generation;

	public ButterflyEmigrationPsystemScript() throws PlinguaCoreException {
		super();
		resetGeneration();
		initializeOffsetArrays();
		// TODO Auto-generated constructor stub
	}

	private void resetGeneration() {
		generation=0;
		
	}

	protected void initializeOffsetArrays() {
		normalizedImmigrants=new float[genotypes];
		immigrants=new long[genotypes];
		generationArray=new String[generations];
		immigrantArray=new String[genotypes];
		for(int i=0; i<generations; i++){
			generationArray[i]=CURRENT_GENERATION+"{"+(i+1)+"}";
		}
		for(int i=0; i<genotypes; i++)
			immigrantArray[i]=IMMIGRANT+"{"+(i+1)+"}";
	}

	@Override
	public void updateMembraneValues(MultiSet<String> multiSet) {
		// TODO Auto-generated method stub
		super.updateMembraneValues(multiSet);
		updateImmigrants(multiSet);
		
	}

	protected void updateImmigrants(MultiSet<String> multiSet) {
		resetGeneration();
		int currentGeneration=getCurrentGeneration(multiSet);
		if(currentGeneration>0){					
			long sumImmigrants=countAndSumImmigrants(multiSet);
			if(sumImmigrants>0){
				normalizeImmigrants(sumImmigrants);
				updateSumming(currentGeneration, multiSet);
				long extraCapacity=(long)(1.0f*(getKFValue(multiSet)/Math.max(1,butterflySumming))*offsetProportion);
				long limitingFactor=Math.min(extraCapacity,sumImmigrants);
				if(limitingFactor>0){
					updateMultisetOffset(multiSet, limitingFactor, currentGeneration);
				}
			}
		}
	}


	private void updateSumming(int currentGeneration, MultiSet<String> multiSet) {
		for(int i=1; i<=genotypes; i++){
			String laidObject=BUTTERFLY_LAID_TEMP+"{"+currentGeneration+","+i+"}";
			long laidCardinality=multiSet.count(laidObject);
			if(laidCardinality>0)
				butterflySumming+=laidCardinality;
		}
		
	}

	protected void updateMultisetOffset(MultiSet<String> multiSet,
			long limitingFactor, int currentGeneration) {
		for(int genotype=0; genotype<genotypes; genotype++){
			float genotypedNormalizedImmigrants=normalizedImmigrants[genotype];
			if(genotypedNormalizedImmigrants>0.0f){
				long immigrants=(long)(genotypedNormalizedImmigrants*(1.0f*limitingFactor));
				multiSet.remove(immigrantArray[genotype],multiSet.count(immigrantArray[genotype]));
				multiSet.add(butterflyLayingArray[genotype],immigrants);
				String emigrantObject=BUTTERFLY_EMIGRANT+"{"+currentGeneration+","+(genotype+1)+"}";
				multiSet.add(emigrantObject,immigrants);
			}
			
		}
	}

	protected void normalizeImmigrants(long sumEmigrants) {
		for(int genotype=0; genotype<genotypes; genotype++){
			normalizedImmigrants[genotype]=(1.0f*immigrants[genotype])/(sumEmigrants*1.0f);
		}
	}

	private int getCurrentGeneration(MultiSet<String> multiSet) {
		if(generation>0)
			return generation;
		for(int i=0; i<generations; i++)
			if(multiSet.contains(generationArray[i])){
				generation=i+1;
				return generation;
			}
		return 0;
	}

	protected long countAndSumImmigrants(MultiSet<String> multiSet) {
		long sumImmigrants=0;
		for(int genotype=0; genotype<genotypes; genotype++){
			immigrants[genotype]=multiSet.count(immigrantArray[genotype]);
			sumImmigrants+=immigrants[genotype];
			
		}
		return sumImmigrants;
	}

}
