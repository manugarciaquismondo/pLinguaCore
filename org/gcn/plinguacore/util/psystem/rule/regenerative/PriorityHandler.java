package org.gcn.plinguacore.util.psystem.rule.regenerative;

public class PriorityHandler {
	protected int priority;
	public PriorityHandler(int priority) {
		super();
		this.priority = priority;
	}
	public void setPriority(int priority){
		this.priority=priority;
	}

	public int getPriority(){
		return priority;
	}
	public String printRuleWithPriority(String baseString) {
		if(getPriority()!=0)
			baseString="("+getPriority()+")"+baseString;
		return baseString;
			
	}

}
