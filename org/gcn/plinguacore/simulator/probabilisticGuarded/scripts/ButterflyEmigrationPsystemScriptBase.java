package org.gcn.plinguacore.simulator.probabilisticGuarded.scripts;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.gcn.plinguacore.simulator.scripts.PsystemScript;
import org.gcn.plinguacore.simulator.scripts.PsystemScriptParameterReader;
import org.gcn.plinguacore.simulator.scripts.PsystemScriptParameters;
import org.gcn.plinguacore.util.HashMultiSet;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Configuration;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;

public class ButterflyEmigrationPsystemScriptBase implements PsystemScript {

	protected final String PARAMETERS_FILE="parameters.xml";
	protected static PsystemScriptParameters parameters;
	protected final String BUTTERFLY_LAYING="butterflylaying";
	protected final String BUTTERFLY_LAID="butterflylaid";
	protected final String BUTTERFLY_EMIGRANT = "emigrant";
	protected final String EGG="female_egg";
	protected final String LARVA="larva";
	protected String butterflyLayingArray[];
	protected String butterflyLaidArray[];
	protected String butterflyEmigrantArray[];
	protected String butterflyEggArray[];
	protected Set<String> larvaSet;
	protected long butterflySumming;
	protected float pyValues[];
	protected int factorials[];
	protected static int affectedInstars;
	protected static int genotypes;
	protected static int generations;
	protected static int numberOfPyValues;
	protected static float pkf;
	protected static int kf;
	protected static int kl;
	protected static int ef;
	protected static int b;
	protected static float k;
	protected static int klimit;
	protected static int years;
	protected static int kvalues[];
	protected static float[][] yearWeights;
	protected static float emigrantProportion;
	protected static int offsetProportion;
	protected static ButterflyValueReader valueReader;
	protected static int patches;
	


	
	@Override
	public Configuration getNextConfiguration(Configuration inputConfiguration, int membraneID) throws PlinguaCoreException {
		Membrane membrane= inputConfiguration.getMembraneStructure().getMembrane(membraneID);
		updateMembraneValues(((ChangeableMembrane)membrane).getMultiSet());
		return inputConfiguration;
	}

	private boolean readParameters() throws PlinguaCoreException {
		if(parameters==null){
			PsystemScriptParameterReader reader = new PsystemScriptParameterReader();
			reader.readParameters(PARAMETERS_FILE);
			initializeParameters();
		}
		return true;
	}

	public static void setValueReader(ButterflyValueReader inputValueReader){
		if(inputValueReader!=null)
			valueReader=inputValueReader;
	}
	
	public ButterflyEmigrationPsystemScriptBase() throws PlinguaCoreException {
		super();
		valueReader = new IndexedButterflyValueReader();
		readParameters();
		
		if(parameters!=null){
			createArrays();
			createLarvaSet();
		}
		// TODO Auto-generated constructor stub
	}

	protected static void initializeParameters() {
		affectedInstars=parameters.getIntParameter("affectedInstars");
		genotypes=parameters.getIntParameter("genotypes");
		generations=parameters.getIntParameter("generations");
		numberOfPyValues=parameters.getIntParameter("numberOfPyValues");
		pkf=parameters.getFloatParameter("pkf");
		kf=parameters.getIntParameter("kf");
		kl=parameters.getIntParameter("kl");
		ef=parameters.getIntParameter("ef");
		b=parameters.getIntParameter("b");
		klimit= parameters.getIntParameter("klimit");
		readKValues();
		years= parameters.getIntParameter("years");
		patches=parameters.getIntParameter("patches");
		readPlantWeights();
		k=parameters.getFloatParameter("k");
		emigrantProportion=parameters.getFloatParameter("emigrantProportion");
		offsetProportion=parameters.getIntParameter("offset");
		
	}

	private static void readPlantWeights() {
		setYears();
		for(int i=0; i<years; i++){
			for(int j=0; j<patches; j++){
				float yearWeight=parameters.getFloatParameter("y{"+(i+1)+","+(j+1)+"}");
				setPlantWeight(i, j, yearWeight);
			}
		}
		
	}

	protected static void readKValues() {
		kvalues= new int[klimit];
		for(int i=0; i<klimit; i++){
			kvalues[i]=parameters.getIntParameter("k"+"{"+i+"}");
		}
	}

	public static void setParameters(PsystemScriptParameters parameters){
		if(parameters!=null)
			ButterflyEmigrationPsystemScriptBase.parameters=parameters;
		
	}
	private void createLarvaSet() {
		// TODO Auto-generated method stub
		larvaSet =  new HashSet<String>();	
		for(int k=1; k<=affectedInstars; k++){
			for(int i=1; i<=generations; i++){
				for (int j = 1; j<=genotypes; j++) {
					String larvaString=LARVA+"{"+k+","+i+","+j+"}";
					larvaSet.add(larvaString);					
				}
			}
		}
		
	}

	private void createArrays() {
		butterflyLayingArray=new String[genotypes];
		butterflyLaidArray=new String[genotypes];
		butterflyEmigrantArray=new String[genotypes];
		butterflyEggArray= new String[genotypes];
		pyValues=new float[numberOfPyValues];
		factorials= new int[numberOfPyValues];
		for(int i=0; i<numberOfPyValues; i++){
			factorials[i]=1;
		}
		for(int i=1; i<=genotypes; i++){
			butterflyLayingArray[i-1]=BUTTERFLY_LAYING+"{"+i+"}";
			butterflyLaidArray[i-1]=BUTTERFLY_LAID+"{"+i+"}";
			butterflyEmigrantArray[i-1]=BUTTERFLY_EMIGRANT+"{"+i+"}";
			butterflyEggArray[i-1]=EGG+"{"+i+"}";
			
					
		}
		
		
	}

	public void updateMembraneValues(MultiSet<String> multiSet) {
		valueReader.restartValues();
		float mu=calculateMu(multiSet);
		calculatePyValues(multiSet, mu);
		//float omega=1.0f-pyValues[0]-pyValues[1];
		removeEmigratingFemales(multiSet);
		distributeEggs(multiSet);
		//removeEmigratingLarvae(multiSet, omega, mu);
				
	}

	public void removeEmigratingLarvae(MultiSet<String> multiSet, float omega, float mu) {
		float emigratingFactor=emigrationFactor(omega, mu);
		for(String larva: larvaSet){
			long numberOfLarvae=multiSet.count(larva);
			long removedLarvae=(long) Math.floor(numberOfLarvae*emigratingFactor);
			if(removedLarvae>0)
				multiSet.remove(larva, removedLarvae);
		}

	}

	protected float emigrationFactor(float omega, float mu) {
		return (float) (omega*(1-Math.exp(-mu)));
	}

	public void removeEmigratingFemales(MultiSet<String> multiSet) {
		float F=calculateF(multiSet);
		updateEmigratingButterflies(multiSet, F);
		
	}

	protected float calculateF(MultiSet<String> multiSet) {
		int kfValue=getKFValue(multiSet);
		if(kfValue<=0)
			return 0;
		return (float) Math.max((pyValues[0]-(pyValues[0]-pkf)*1.0f*Math.pow(((1.0f*butterflySumming)/(1.0f*kfValue)),b)),0.0f);
	}
	
	protected int getKFValue(MultiSet<String> multiSet) {
		// TODO Auto-generated method stub
		return valueReader.getKFValue(multiSet);
	}

	protected void updateEmigratingButterflies(MultiSet<String> multiSet, float F) {		
		float emigrants=1.0f-F;
		if(emigrants<=0) return;
		for(int genotype=0; genotype<genotypes; genotype++){
			String object=butterflyLayingArray[genotype];
			long butterflies=multiSet.count(object);
			long removedButterflies=(long) Math.floor(butterflies*(emigrants));
			if(removedButterflies>0){
				multiSet.remove(object, removedButterflies);
				long stayingEmigrants=(long) Math.floor(removedButterflies*emigrantProportion);
				if(stayingEmigrants>0)
					multiSet.add(butterflyEmigrantArray[genotype], stayingEmigrants);
			}
			
		}
		// TODO Auto-generated method stub
		
	}

	public void calculatePyValues(MultiSet<String> multiSet, float mu) {
		
		for (int i = 0; i < numberOfPyValues; i++) {
			pyValues[i]=calculatePyValue(mu, i);
			
		}
		butterflySumming=calculatePhenotypeSumming(multiSet);
		
	}

	protected float calculatePyValue(float mu, int i) {
		return (float) (Math.pow(mu, i)*Math.exp(-mu)/factorials[i]);
	}

	protected float calculateMu(MultiSet<String> multiSet) {
		long larvae=sumLarvae(multiSet);
		int klValue = getKLValue(multiSet);
		if(klValue<=0)
			return 0;
		return larvae*1.0f/klValue*1.0f;
	}

	protected int getKLValue(MultiSet<String> multiSet) {
		return valueReader.getKLValue(multiSet);
	}

	private long sumLarvae(MultiSet<String> multiSet) {
		// TODO Auto-generated method stub
		long sum=0;
		for (String larva : larvaSet) {
			sum+=multiSet.count(larva);
		}
		return sum;
	}

	public void distributeEggs(MultiSet<String> membraneMultiSet) {

		butterflySumming=calculatePhenotypeSumming(membraneMultiSet);
		if(butterflySumming>0){
			float p=calculateP(membraneMultiSet, butterflySumming);
			float q=1.0f-p;
			float proportions[]=new float[genotypes];
			proportions[1]=(float)Math.pow(p, 2);
			proportions[2]=(float)Math.pow(q, 2);
			proportions[0]=2.0f*p*q;
			long numberOfEggs[]=new long[genotypes];
			for(int genotype=0; genotype<genotypes; genotype++){
				numberOfEggs[genotype]=(long)Math.floor(butterflySumming*proportions[genotype]*ef);
				membraneMultiSet.add(butterflyEggArray[genotype], numberOfEggs[genotype]);
			}			
		}
		
	}


	protected float sumProportions(float[] proportions) {
		float proportionSumming=0;
		for(int i=0; i<genotypes; i++){
			proportionSumming+=proportions[i];
		}
		return proportionSumming;
	}

	protected float calculateP(MultiSet<String> membraneMultiSet, long summing) {
		if(summing==0)
			return 0.0f;
		long genotypesArray[]= new long[genotypes];
		MultiSet<String> multiSetToRemove = new HashMultiSet<String>();
		for(int i=0; i<genotypes; i++){
			genotypesArray[i]=membraneMultiSet.count(butterflyLayingArray[i]);
			multiSetToRemove.add(butterflyLayingArray[i], genotypesArray[i]);
			membraneMultiSet.add(butterflyLaidArray[i], genotypesArray[i]);
		}
		membraneMultiSet.removeAll(multiSetToRemove);
		return (2.0f*genotypesArray[1]+genotypesArray[0])/(2.0f*summing);
	}

	protected long calculatePhenotypeSumming(MultiSet<String> multiSet) {		
		long summing=0;
		for(int genotype=0; genotype<genotypes; genotype++){
			String object=butterflyLayingArray[genotype];
			summing+=multiSet.count(object);
		}
		return summing;
		// TODO Auto-generated method stub
		
	}
	
	public static void setPlantWeight(int i, int j, float yearWeight) {
		if(i<years)
			yearWeights[i][j]=yearWeight;
		
	}

	public static void setYears() {
		yearWeights=new float[years][patches];
		for(int i=0; i<years; i++)
			for(int j=0; j<patches; j++)
				yearWeights[i][j]=1.0f;
		
	}

}
