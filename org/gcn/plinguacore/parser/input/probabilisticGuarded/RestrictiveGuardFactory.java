package org.gcn.plinguacore.parser.input.probabilisticGuarded;

import org.gcn.plinguacore.util.psystem.rule.guard.ComparationMasks;
import org.gcn.plinguacore.util.psystem.rule.guard.probabilisticGuarded.RestrictiveGuard;
import org.gcn.plinguacore.util.psystem.rule.guard.probabilisticGuarded.RestrictiveUnaryUnitGuard;

public class RestrictiveGuardFactory {
	public static RestrictiveGuard createRestrictiveGuard(String object){
		if(object.equals(ComparationMasks.RESTRICTIVE+"")||object.equals(ComparationMasks.STRING_RESTRICTIVE))
			return new RestrictiveGuard();
		else
			return new RestrictiveUnaryUnitGuard(object);
		
		
	}

}
