package org.gcn.plinguacore.parser.output.simplekernel;

import org.gcn.plinguacore.parser.output.simplekernel.KernelMapper;
import org.gcn.plinguacore.util.psystem.rule.guard.AndJoinedGuard;
import org.gcn.plinguacore.util.psystem.rule.guard.ComparationMasks;
import org.gcn.plinguacore.util.psystem.rule.guard.GeneralUnitGuard;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;
import org.gcn.plinguacore.util.psystem.rule.guard.LogicOperatedGuard;
import org.gcn.plinguacore.util.psystem.rule.guard.OrJoinedGuard;
import org.gcn.plinguacore.util.psystem.rule.guard.UnitGuard;

public class KernelNumberWriter {
	
	KernelMapper mapper;
	
	
	public KernelNumberWriter() {
		super();

	}
	
	

	public void setMapper(KernelMapper mapper) {
		this.mapper = mapper;
	}



	public void writeNumber(int number, StringBuffer psystemDescription) {
		psystemDescription.append("" + number + " : ");
	}
	
	public void writeGuard(Guard guard, StringBuffer psystemDescription) {
		if(guard!=null){			
			GeneralUnitGuard unitGuard=getGeneralUnitGuard(guard);
			String sign = (unitGuard.getSign()==ComparationMasks.MINUS)?"1":"0";
			sign+=":";
			psystemDescription.append(sign+getObjectRepresentation(unitGuard)+":");
		}

		
		// TODO Auto-generated method stub
		
	}



	private Integer getObjectRepresentation(GeneralUnitGuard unitGuard) {
		String obj = unitGuard.getObj();
		if (obj.equals(ComparationMasks.STRING_RESTRICTIVE))
			return -1;
		else
			return mapper.getObjectID(unitGuard.getObj());			
	}



	private GeneralUnitGuard getGeneralUnitGuard(Guard guard) {
		Guard generalUnitGuard = guard;
		while(! (generalUnitGuard instanceof GeneralUnitGuard))
			generalUnitGuard = ((LogicOperatedGuard)generalUnitGuard).getGuards().get(0);
		return (GeneralUnitGuard)generalUnitGuard;
	}



	public void writeNumber(float number, StringBuffer psystemDescription) {
		psystemDescription.append("" + number + " : ");
		
	}
	
}
