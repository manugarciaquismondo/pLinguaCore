package org.gcn.plinguacore.util.psystem.rule.guard;

import org.gcn.plinguacore.util.HashMultiSet;
import org.gcn.plinguacore.util.MultiSet;

public class UnitGuard extends Guard implements GeneralUnitGuard{
	
	protected short relOp; 
	protected short sign;
	protected String obj;
	protected long mul;
	protected String currentString="";

	public UnitGuard(short relOp, short sign, String obj, long mul) {
		super();
		this.relOp = relOp;
		this.sign = sign;
		this.obj = obj;
		this.mul = mul;
	}
	
	public UnitGuard(String representation){
		currentString="";
		String trimmedRepresentation = representation.substring(1, representation.length()-1);
		int endOp = trimmedRepresentation.indexOf(ComparationMasks.STRING_PLUS);
		if (endOp == -1)
			endOp = trimmedRepresentation.indexOf(ComparationMasks.STRING_MINUS);
		relOp = ComparationMasks.operationCode(trimmedRepresentation.substring(0, endOp));
		sign = ComparationMasks.operationCode(trimmedRepresentation.substring(endOp,endOp+1));
		int endObj = trimmedRepresentation.indexOf("*");
				
		if (endObj == -1){
			obj = trimmedRepresentation.substring(endOp+1);
			mul = 1;
		}
		else {
			obj = trimmedRepresentation.substring(endOp+1,endObj);
			mul = Long.parseLong(trimmedRepresentation.substring(endObj+1));
		}
	}


	@Override
	public boolean evaluate() {
		// TODO Auto-generated method stub
		boolean result=false;
		switch(relOp){
			case(ComparationMasks.DIFF):
				result = multiSet.count(obj)!=mul;
				break;
			case(ComparationMasks.GREATER_OR_EQUAL_THAN):
				result = multiSet.count(obj)>=mul;
				break;
			case(ComparationMasks.LESS_OR_EQUAL_THAN):
				result = multiSet.count(obj)<=mul;
				break;
			case(ComparationMasks.LESS_THAN):
				result = multiSet.count(obj)<mul;
				break;
			case(ComparationMasks.GREATER_THAN):
				result = multiSet.count(obj)>mul;
				break;
			case(ComparationMasks.EQUAL):
				result = multiSet.count(obj)==mul;
				break;
		}
		if(sign==ComparationMasks.MINUS)
			result=!result;
		return result;
		
	}
	
	public String getObj() {
		return obj;
	}

	public short getRelOp() {
		return relOp;
	}

	public short getSign() {
		return sign;
	}

	public long getMul() {
		return mul;
	}

	@Override
	public String toString(){
		String accumulator= "{" + operationRepresentation(relOp) + operationRepresentation(sign) + obj;
		if(mul!=1)
			accumulator+="*"+mul;
		accumulator+="}";
		return accumulator;
	}

	private String operationRepresentation(short relOp2) {
		return ComparationMasks.operationRepresentation(relOp2);
	}

	@Override
	public String accept(IGuardVisitor visitor) {
		return visitor.visit(this);
	}

	@Override
	public MultiSet<String> getMultiSet() {
		MultiSet<String> multiSet = new HashMultiSet<String>();
		multiSet.add(obj, mul);
		return multiSet;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(!super.equals(obj)) return false;
		UnitGuard unitGuard = (UnitGuard)obj;
		return unitGuard.getObj().equals(this.obj) && unitGuard.mul==mul && unitGuard.relOp==relOp && unitGuard.sign==sign;
	}
	
	@Override
	public int hashCode(){
		final int prime = 31;
		int result=1;
		result = prime * result + relOp;
		result = prime * result + sign;
		result = prime * result + (int)mul;
		result = prime * result + ((obj == null) ? 0 : obj.hashCode());
		return result;
	}
	
	@Override
	public byte getType() {
		// TODO Auto-generated method stub
		return GuardTypes.UNIT_STANDARD;
	}
}