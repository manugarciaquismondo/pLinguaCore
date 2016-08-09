package org.gcn.plinguacore.parser.output.promela;

import java.util.*;

import org.gcn.plinguacore.util.psystem.rule.guard.*;

import org.gcn.plinguacore.parser.output.promela.PromelaTemplateFactory;

public class PromelaGuardVisitor implements IGuardVisitor {
	
	private PromelaTemplateFactory templateFactory;
	
	public PromelaGuardVisitor(PromelaTemplateFactory templateFactory) {
		this.templateFactory = templateFactory;
	}

	@Override
	public String visit(UnitGuard guard) {
		String currentRelOp = getStringRelOp(guard.getRelOp());
		String relOp = guard.getSign() == ComparationMasks.MINUS ? getOpposedRelOp(currentRelOp) : currentRelOp;
		return templateFactory.buildRelOpTemplate( 
				relOp, 
				templateFactory.buildSymbolTempate(guard.getObj()).render(), ((Long)guard.getMul()).toString()).render();
	}

	@Override
	public String visit(OrJoinedGuard guard) {
		List<String> promelaGuards = new ArrayList<String>();
		for(Guard g : guard.getGuards()) {
			promelaGuards.add(g.accept(this));
		}
		
		return templateFactory.buildOrJoinedTemplate(promelaGuards).render();
	}

	@Override
	public String visit(AndJoinedGuard guard) {
		List<String> promelaGuards = new ArrayList<String>();
		for(Guard g : guard.getGuards()) {
			promelaGuards.add(g.accept(this));
		}
		
		return templateFactory.buildAndJoinedTemplate(promelaGuards).render();
	}
	
	protected String getStringRelOp(short relOp) {
		switch(relOp){
			case(ComparationMasks.EQUAL): return "==";
			case(ComparationMasks.LESS_THAN): return "<";
			case(ComparationMasks.LESS_OR_EQUAL_THAN): return "<=";
			case(ComparationMasks.GREATER_OR_EQUAL_THAN): return ">=";
			case(ComparationMasks.GREATER_THAN): return ">";
			case(ComparationMasks.DIFF): return "!=";
			default: return "";
		}
	}
	
	protected String getOpposedRelOp(String relOp) {
		if (relOp.equals(">"))
			return "<=";
		else if (relOp.equals(">="))
			return "<";
		else if (relOp.equals("<"))
			return ">=";
		else if (relOp.equals("<="))
			return ">";
		else if (relOp.equals("=="))
			return "!=";
		else
			return "==";		
	}
}
