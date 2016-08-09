package org.gcn.plinguacore.util.psystem.rule.guard;

public interface IVisitableGuard {
	String accept(IGuardVisitor visitor);
}
