package org.gcn.plinguacore.util.psystem.rule.guard;

import org.gcn.plinguacore.util.MultiSet;

public abstract class Guard implements IVisitableGuard {
	
	protected MultiSet<String> multiSet; 
	
	public Guard() {
		super();
	}
	
	public void setMultiSet(MultiSet<String> multiSet) {
		this.multiSet = multiSet;
	}
	
	public abstract MultiSet<String> getMultiSet();
	
	public abstract boolean evaluate();
	
	@Override
	public boolean equals(Object obj){
		if(obj==this) return true;
		if(obj==null) return false;
		if(!getClass().equals(obj.getClass())) return false;
		
		Guard castObj = (Guard)obj;
		return (getMultiSet().equals(castObj.getMultiSet()) &&
				castObj.getType() == this.getType());
		
	}
	
	public abstract byte getType();

}