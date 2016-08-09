package org.gcn.plinguacore.util.psystem.rule.guard;

public abstract class GuardTypes {
	
	public static final byte UNIT_STANDARD=0;
	public static final byte AND_JOINED_STANDARD=1;
	public static final byte OR_JOINED_STANDARD=2;
	public static final byte SIMPLE_RESTRICTIVE=3;
	public static final byte UNIT_RESTRICTIVE=4;
	public static final byte UNARY_UNIT_RESTRICTIVE=5;
	public static boolean isRestrictive(byte type) {
		// TODO Auto-generated method stub
		return type==SIMPLE_RESTRICTIVE||
				type==UNIT_RESTRICTIVE||
				type==UNARY_UNIT_RESTRICTIVE;
	}

}
