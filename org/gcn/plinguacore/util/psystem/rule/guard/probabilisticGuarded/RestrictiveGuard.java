package org.gcn.plinguacore.util.psystem.rule.guard.probabilisticGuarded;

import java.util.HashSet;
import java.util.Set;

import org.gcn.plinguacore.util.HashMultiSet;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.psystem.rule.guard.ComparationMasks;
import org.gcn.plinguacore.util.psystem.rule.guard.GeneralUnitGuard;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;
import org.gcn.plinguacore.util.psystem.rule.guard.GuardTypes;
import org.gcn.plinguacore.util.psystem.rule.guard.IGuardVisitor;

public class RestrictiveGuard extends Guard implements GeneralUnitGuard{

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(!(super.equals(obj))) return false;
		RestrictiveGuard restrictiveGuard = (RestrictiveGuard) obj;
		return flagsAreEqual(restrictiveGuard.flags);
				
	}

	protected boolean flagsAreEqual(Set<String> comparedFlags) {
		for(String flag: flags)
			if(!comparedFlags.contains(flag)) return false;
		for(String flag:comparedFlags)
			if(!flags.contains(flag)) return false;
		return true;
	}

	protected Set<String> flags;

	public void addFlag(String flag){
		this.flags.add(flag);
	}

	public void setFlags(Set<String> flags){
		this.flags=copySet(flags);
	}
	
	public RestrictiveGuard() {
		this.flags=new HashSet<String>();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String accept(IGuardVisitor visitor) {
		// TODO Auto-generated method stub
		return "This is a restrictive guard";
	}

	@Override
	public MultiSet<String> getMultiSet() {
		// TODO Auto-generated method stub
		return new HashMultiSet<String>();
	}

	@Override
	public boolean evaluate() {
		// TODO Auto-generated method stub
		return !containsFlags(multiSet);
	}
	
	protected Set<String> copySet(Set<String> flags) {
		Set<String> copiedSet= new HashSet<String>();
		for(String object: flags){
			copiedSet.add(object+"");
		}
		return copiedSet;
	}
	
	boolean containsFlags(MultiSet<String> multiSet){
		Set<String> disabledFlags=copySet(flags);
		disabledFlags.removeAll(getMultiSet());
		for(String object:disabledFlags)
			if(multiSet.contains(object)) return true;
		return false;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "{"+restrictiveSymbol()+"}";
	}
	
	@Override
	public int hashCode(){
		final int prime = 31;
		int result=1;
		result = prime * result + restrictiveSymbol().hashCode();
		return result;
	}
	
	
	public String restrictiveSymbol(){
		return "/";
	}

	@Override
	public String getObj() {
		// TODO Auto-generated method stub
		return "#";
	}

	@Override
	public short getRelOp() {
		// TODO Auto-generated method stub
		return ComparationMasks.EQUAL;
	}

	@Override
	public short getSign() {
		// TODO Auto-generated method stub
		return ComparationMasks.PLUS;
	}

	@Override
	public long getMul() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isAFlag(String generatedObject) {
		// TODO Auto-generated method stub
		return flags.contains(generatedObject);
	}

	public void removeFlag(String flag) {
		this.flags.remove(flag);
		
	}
	
	public byte getType(){
		return GuardTypes.SIMPLE_RESTRICTIVE;
	}
	
	
	

}
