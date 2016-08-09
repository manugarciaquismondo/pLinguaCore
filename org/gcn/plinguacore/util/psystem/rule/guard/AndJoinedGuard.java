package org.gcn.plinguacore.util.psystem.rule.guard;

public class AndJoinedGuard extends LogicOperatedGuard {

	public AndJoinedGuard() {
		super();
	}
	
	public AndJoinedGuard(String representation) {
		super(representation);
	}
	
	@Override
	public boolean evaluate() {
		for(Guard guard: guards){
			if(!guard.evaluate()) return false;
		}
		return true;
	}

	@Override
	protected String getOperator(){
		return "&&";
	}

	@Override
	protected Guard createGuardElement(String guardRepresentation) {
		return new UnitGuard(guardRepresentation);
	}
	
	@Override
	public String accept(IGuardVisitor visitor) {
		return visitor.visit(this);
	}

	@Override
	public byte getType() {
		// TODO Auto-generated method stub
		return GuardTypes.AND_JOINED_STANDARD;
	}
}
