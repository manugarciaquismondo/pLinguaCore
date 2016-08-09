package org.gcn.plinguacore.util.psystem.rule.guard;

public class ComparationMasks {
	public static final short LESS_THAN = 2;
	public static final short LESS_OR_EQUAL_THAN = 3;
	public static final short GREATER_THAN = 4;
	public static final short GREATER_OR_EQUAL_THAN = 5;
	public static final short EQUAL = 6;
	public static final short DIFF = 7;
	public static final short MINUS = 0;
	public static final short PLUS = 1;
	public static final short RESTRICTIVE = -1;
	public static final String STRING_LESS_THAN = "<";
	public static final String STRING_LESS_OR_EQUAL_THAN = "<=";
	public static final String STRING_GREATER_THAN = ">";
	public static final String STRING_GREATER_OR_EQUAL_THAN = ">=";
	public static final String STRING_EQUAL = "=";
	public static final String STRING_DIFF = "<>";
	public static final String STRING_MINUS = "-";
	public static final String STRING_PLUS = "+";
	public static final String STRING_RESTRICTIVE = "#";
	
	
	public static String operationRepresentation(short relOp) {
		// TODO Auto-generated method stub
		switch(relOp){
			case(EQUAL): return STRING_EQUAL;
			case(LESS_THAN): return STRING_LESS_THAN;
			case(LESS_OR_EQUAL_THAN): return STRING_LESS_OR_EQUAL_THAN;
			case(GREATER_OR_EQUAL_THAN): return STRING_GREATER_OR_EQUAL_THAN;
			case(GREATER_THAN): return STRING_GREATER_THAN;
			case(DIFF): return STRING_DIFF;
			case(MINUS): return STRING_MINUS;
			case(PLUS): return STRING_PLUS;
			case(RESTRICTIVE): return STRING_RESTRICTIVE;
			default: return "";
		}
	}
	
	public static short operationCode(String representation) {
		// TODO Auto-generated method stub
		if(representation.equals(STRING_GREATER_OR_EQUAL_THAN)) return GREATER_OR_EQUAL_THAN;
		if(representation.equals(STRING_LESS_OR_EQUAL_THAN)) return LESS_OR_EQUAL_THAN;
		if(representation.equals(STRING_DIFF)) return DIFF;
		if(representation.equals(STRING_EQUAL)) return EQUAL;
		if(representation.equals(STRING_LESS_THAN)) return LESS_THAN;
		if(representation.equals(STRING_GREATER_THAN)) return GREATER_THAN;
		if(representation.equals(STRING_MINUS)) return MINUS;
		if(representation.equals(STRING_PLUS)) return PLUS;
		if(representation.equals(STRING_RESTRICTIVE)) return RESTRICTIVE;
		return -1;
	}
}
