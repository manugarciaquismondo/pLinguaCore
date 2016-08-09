package org.gcn.plinguacore.util.psystem.rule.guard;

import java.util.LinkedList;
import java.util.List;

import org.gcn.plinguacore.util.HashMultiSet;
import org.gcn.plinguacore.util.MultiSet;

public abstract class LogicOperatedGuard extends Guard {

	protected List<Guard> guards;
	
	public LogicOperatedGuard(){
		guards=new LinkedList<Guard>();
	}
	
	public LogicOperatedGuard(String representation){
		this();
		String componentGuards[] = representation.split(getOperator());
		for(int i=0; i<componentGuards.length; i++)
			addGuard(createGuardElement(componentGuards[i]));
	}

	protected abstract Guard createGuardElement(String guardRepresentation);

	public void addGuard(Guard guard){
		this.guards.add(guard);
	}

	@Override
	public void setMultiSet(MultiSet<String> multiSet) {
		// TODO Auto-generated method stub
		super.setMultiSet(multiSet);
		for(Guard guard: guards)
			guard.setMultiSet(multiSet);
	}
	
	@Override
	public String toString(){
		String accumulator="";
		
		for(Guard guard: guards){
			accumulator+=guard.toString()+getOperator();
		}
		accumulator=accumulator.substring(0, accumulator.lastIndexOf(getOperator()));
		return accumulator;
		
	}
	
	protected abstract String getOperator();

	public List<Guard> getGuards() {
		return guards;
	}
	
	@Override
	public MultiSet<String> getMultiSet() {
		MultiSet<String> multiSet = new HashMultiSet<String>();
		for(Guard g : guards) {
			MultiSet<String> gMultiSet = g.getMultiSet();
			for(String obj : gMultiSet) {
				multiSet.add(obj, gMultiSet.count(obj));
			}
		}
		
		return multiSet;
	}

	@Override
	public boolean equals(Object obj) {
		if(!super.equals(obj)) return false;
		LogicOperatedGuard logicOperatedGuard = (LogicOperatedGuard) obj;
		return guardsAreEqual(logicOperatedGuard.getGuards());
	}

	protected boolean guardsAreEqual(List<Guard> comparedGuards) {
		for(Guard guard:getGuards())
			if(!comparedGuards.contains(guard)) return false;
		for(Guard guard:comparedGuards)
			if(!getGuards().contains(guard)) return false;
		return true;
	}
	
	@Override
	public int hashCode(){
		final int prime = 31;
		int result=1;
		for(Guard guard: getGuards())
			result=prime * result + ((guard == null) ? 0 : guard.hashCode());
		return result * multiSet.hashCode();

	}
	
	
}
