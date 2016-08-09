package org.gcn.plinguacore.util.psystem.rule.guard;

public interface IGuardVisitor {
	
	String visit(UnitGuard guard);
	
	String visit(OrJoinedGuard guard);
	
	String visit(AndJoinedGuard guard);
}
