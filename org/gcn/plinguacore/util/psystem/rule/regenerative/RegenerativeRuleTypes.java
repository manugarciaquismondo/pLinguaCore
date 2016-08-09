package org.gcn.plinguacore.util.psystem.rule.regenerative;

import org.gcn.plinguacore.util.PlinguaCoreException;

public class RegenerativeRuleTypes {
	public static final String DIVISION = "division";
	public static final String GEMMATION = "gemmation";
	public static final String LINKING = "linking";
	public static final String COMMUNICATION = "communication";
	public static final String BUDDING = "budding";
	public static final String UNLINKING = "unlinking";
	public static final byte DIVISION_BYTE = 0;
	public static final byte GEMMATION_BYTE = 1;
	public static final byte LINKING_BYTE = 2;
	public static final byte COMMUNICATION_BYTE = 3;
	public static final byte BUDDING_BYTE = 4;
	public static final byte UNLINKING_BYTE = 5;
	
	public static byte getLabelAsByte(String stringLabel) throws PlinguaCoreException{
		switch(stringLabel){
			case(DIVISION): return DIVISION_BYTE;
			case(GEMMATION): return GEMMATION_BYTE;
			case(LINKING): return LINKING_BYTE;
			case(COMMUNICATION): return COMMUNICATION_BYTE;
			case(BUDDING): return BUDDING_BYTE;
			case(UNLINKING): return UNLINKING_BYTE;
			default: throw new PlinguaCoreException("String label "+stringLabel+" does not correspond to any regenerative rule type");
		}
	}

}
