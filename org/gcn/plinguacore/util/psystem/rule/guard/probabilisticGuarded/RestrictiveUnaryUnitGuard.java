package org.gcn.plinguacore.util.psystem.rule.guard.probabilisticGuarded;

import org.gcn.plinguacore.util.psystem.rule.guard.ComparationMasks;
import org.gcn.plinguacore.util.psystem.rule.guard.GuardTypes;
import org.gcn.plinguacore.util.psystem.rule.guard.UnitGuard;

public class RestrictiveUnaryUnitGuard extends RestrictiveUnitGuard {

	String obj;
	
	public RestrictiveUnaryUnitGuard(String obj) {
		super(new UnitGuard(ComparationMasks.EQUAL, ComparationMasks.PLUS, obj, 1));
		this.obj = obj;
		// TODO Auto-generated constructor stub
	}
	
	public String getObj(){
		return this.obj;
	}
	
	@Override
	public int hashCode(){
		final int prime = 31;
		int result=super.hashCode();
		result = prime * result + obj.hashCode();
		return result;
	}
	
	@Override
	public byte getType() {
		// TODO Auto-generated method stub
		return GuardTypes.UNARY_UNIT_RESTRICTIVE;
	}

	
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof RestrictiveUnaryUnitGuard)) return false;
		if(obj==this) return true;
		String objectFlag = ((RestrictiveUnaryUnitGuard)obj).getObj();
		return objectFlag.equals(this.obj);
	}
}
