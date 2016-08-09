package org.gcn.plinguacore.util.psystem.rule.guard;

public interface GeneralUnitGuard {
	
	public String getObj();

	public short getRelOp();

	public short getSign();

	public long getMul();
	
	public boolean evaluate();
	
	public String accept(IGuardVisitor visitor);

}
