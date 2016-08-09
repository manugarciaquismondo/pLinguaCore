package org.gcn.plinguacore.util.psystem.rule.guard.probabilisticGuarded;

import java.io.ObjectInputStream.GetField;
import java.util.HashSet;
import java.util.Set;

import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.psystem.rule.guard.GuardTypes;
import org.gcn.plinguacore.util.psystem.rule.guard.UnitGuard;

public class RestrictiveUnitGuard extends RestrictiveGuard {

	
	Set<String> consideredFlags;
	private UnitGuard innerGuard;





	public RestrictiveUnitGuard(UnitGuard innerGuard) {
		consideredFlags = new HashSet<String>();
		this.innerGuard = innerGuard;
		// TODO Auto-generated constructor stub
	}

	public String getObj(){
		return innerGuard.getObj();
	}
	
	public long getMul(){
		return innerGuard.getMul();
	}
	
	public short getRelOp(){
		return innerGuard.getRelOp();
	}
	
	public short getSign(){
		return innerGuard.getSign();
	}
	
	


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "{"+restrictiveSymbol()+innerGuard.toString().substring(1);
	}
	
	@Override
	public int hashCode(){
		final int prime = 31;
		int result=super.hashCode();
		result = prime * result + innerGuard.hashCode();
		return result;
	}





	@Override
	public void setMultiSet(MultiSet<String> multiSet) {
		// TODO Auto-generated method stub
		super.setMultiSet(multiSet);
		innerGuard.setMultiSet(multiSet);
	}





	@Override
	public MultiSet<String> getMultiSet(){
		return innerGuard.getMultiSet();
	}





	@Override
	public boolean evaluate() {
		// TODO Auto-generated method stub
		if(containsFlags(multiSet)) return false;
		return innerGuard.evaluate();
	}
	
	public byte getType(){
		return GuardTypes.UNIT_RESTRICTIVE;
	}

}
