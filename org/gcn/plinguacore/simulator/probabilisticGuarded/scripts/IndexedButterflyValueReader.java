package org.gcn.plinguacore.simulator.probabilisticGuarded.scripts;

import org.gcn.plinguacore.util.MultiSet;

public class IndexedButterflyValueReader extends SimpleButterflyValueReader {

	private final String PLANTS_BASE="plants";
	
	private int bufferValue;
	private int year;
	private int patch;
	
	@Override
	public int getKLValue(MultiSet<String> multiSet) {
		// TODO Auto-generated method stub
		return getPlantValue(multiSet, ButterflyEmigrationPsystemScriptBase.kl);
		
			
	}
	public int getPlantValue(MultiSet<String> multiSet, int baseValue){
		int plantSize=fetchPlantObject(multiSet);
		float yearWeight=getPlantWeight(multiSet);
		int base=baseValue;
		if(plantSize>=0)
			base=plantSize;
		return (int)(((float)base)*yearWeight);
	}
	

	private float getPlantWeight(MultiSet<String> multiSet) {
		// TODO Auto-generated method stub
		int currentYear=getCurrentYear(multiSet);
		int currentPatch=getCurrentPatch(multiSet);
		if(checkYear(currentYear)&&checkPatch(currentPatch))
			return ButterflyEmigrationPsystemScriptBase.yearWeights[currentYear-1][currentPatch-1];
		else
			return 1.0f;
	}
	private boolean checkPatch(int currentPatch) {
		// TODO Auto-generated method stub
		return currentPatch>0&&currentPatch<=ButterflyEmigrationPsystemScriptBase.patches;
	}
	protected boolean checkYear(int currentYear) {
		return currentYear>0&&currentYear<=ButterflyEmigrationPsystemScriptBase.years;
	}
	
	private int getCurrentElem(MultiSet<String> multiSet, String baseString, int limit, int baseElem){
		if(baseElem>0)
			return baseElem;
		for(int i=0; i<limit; i++){
			String elemName=baseString+"{"+(i+1)+"}";
			long elemCardinality = multiSet.count(elemName);
			if(elemCardinality>0){
				return i+1;
			}
		}
		return 0;
		
	}
	private int getCurrentPatch(MultiSet<String> multiSet) {
		// TODO Auto-generated method stub
		patch=getCurrentElem(multiSet, "patch", ButterflyEmigrationPsystemScriptBase.patches, patch);
		return patch;
	}
	private int getCurrentYear(MultiSet<String> multiSet) {
		// TODO Auto-generated method stub
		year=getCurrentElem(multiSet, "current_year", ButterflyEmigrationPsystemScriptBase.years, year);
		return year;
	}
	protected int fetchPlantObject(MultiSet<String> multiSet) {
		if(bufferValue>=0)
			return bufferValue;
		int klimit = ButterflyEmigrationPsystemScriptBase.klimit;
		for(int i=0; i<klimit; i++){
			String plantName=PLANTS_BASE+"{"+i+"}";
			if(multiSet.contains(plantName)){
				//multiSet.remove(plantName,1);
				bufferValue= ButterflyEmigrationPsystemScriptBase.kvalues[i];
				return bufferValue;
			}
		}
		return -1;
	}

	@Override
	public int getKFValue(MultiSet<String> multiSet) {
		// TODO Auto-generated method stub
		return getPlantValue(multiSet, ButterflyEmigrationPsystemScriptBase.kf);
	}

	@Override
	public void restartValues() {
		bufferValue=-1;
		year=0;
		patch=0;
		
	}
	public IndexedButterflyValueReader() {
		super();
		restartValues();
		// TODO Auto-generated constructor stub
	}

}
