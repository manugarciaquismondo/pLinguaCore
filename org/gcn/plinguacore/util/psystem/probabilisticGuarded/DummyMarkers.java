package org.gcn.plinguacore.util.psystem.probabilisticGuarded;

public abstract class DummyMarkers {
	
	
	public static final byte NO_DUMMY_MODE = 0;
	public static final byte WEAK_DUMMY_MODE = 1;
	public static final byte STRONG_DUMMY_MODE = 2;
	
	public static final String STRING_NO_DUMMY_MODE = "no";
	public static final String STRING_WEAK_DUMMY_MODE = "weak";
	public static final String STRING_STRONG_DUMMY_MODE = "strong";
	public static final String DUMMY_FLAG="dummyflag";
	
	public static byte getMode(String mode){
		if(mode.equals(STRING_WEAK_DUMMY_MODE)) return WEAK_DUMMY_MODE;
		if(mode.equals(STRING_STRONG_DUMMY_MODE)) return STRONG_DUMMY_MODE;
		return NO_DUMMY_MODE;
		
	}

}
