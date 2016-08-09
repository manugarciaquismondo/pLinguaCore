package org.gcn.plinguacore.util.psystem.rule.guard;

public class OrJoinedGuard extends LogicOperatedGuard {

	public OrJoinedGuard() {
		super();
	}
	
	public OrJoinedGuard(String representation) {
		super(representation);
	}
	
	@Override
	public boolean evaluate() {
		for(Guard guard: guards){
			if(guard.evaluate()) return true;
		}
		return false;
	}
	
	@Override
	protected String getOperator(){
		return "||";
	}
	
	@Override
	protected Guard createGuardElement(String guardRepresentation) {
		return new AndJoinedGuard(guardRepresentation);
	}
	
	@Override
	public String accept(IGuardVisitor visitor) {
		return visitor.visit(this);
	}
	
	@Override
	public byte getType() {
		return GuardTypes.OR_JOINED_STANDARD;
	}
}
