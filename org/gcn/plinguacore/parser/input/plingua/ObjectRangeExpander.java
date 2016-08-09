package org.gcn.plinguacore.parser.input.plingua;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Stack;

import org.gcn.plinguacore.parser.input.plingua.PlinguaJavaCcParser.Range;
import org.gcn.plinguacore.util.HashMultiSet;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.Pair;
import org.gcn.plinguacore.util.psystem.Label;
import org.gcn.plinguacore.util.psystem.rule.OuterRuleMembrane;
import org.gcn.plinguacore.util.psystem.rule.guard.AndJoinedGuard;
import org.gcn.plinguacore.util.psystem.rule.guard.LogicOperatedGuard;
import org.gcn.plinguacore.util.psystem.rule.guard.OrJoinedGuard;

public class ObjectRangeExpander {
	
	protected static boolean checkSafeMode(PlinguaEnvironment environment, List<Range> rangeList){
		if(environment.isSafeMode()){
			ListIterator<Range> rangesIterator = rangeList.listIterator();
			Range currentRange = rangesIterator.next();
			String variable = currentRange.variable;
			environment.setVariable(variable, 0);
			return true;
		}
		return false;
	}
	public static void expandObject(MultiSet<String> ms, List<Range> rangeList, Stack<Token> ranges, PlinguaEnvironment environment, Set<String> nonCheckedVariables) throws PlinguaSemanticsException, ObjectRangeException{
		if(checkSafeMode(environment, rangeList)) return;			
		MultiSet<String> objectsToAdd = new HashMultiSet<String>();
		MultiSet<String> objectsToRemove = new HashMultiSet<String>();
		for(int h=0; h<rangeList.size(); h++){

		for(String object : ms){
			ListIterator<Range> rangesIterator = rangeList.listIterator();
			while(rangesIterator.hasNext())
			{
 
				Range currentRange = rangesIterator.next();
				String variable = currentRange.variable;
				Pair<Integer, Integer> bounds = setBeginAndEnd(currentRange);
				updateMultiSet(ms, objectsToAdd, objectsToRemove, object,
						variable, bounds);
			}
				
			}
	
			
			ms.addAll(objectsToAdd);
			ms.removeAll(objectsToRemove);
		}
		for(String object : ms)
			checkCompositeValues(object);


		}
	protected static void updateMultiSet(MultiSet<String> ms,
			MultiSet<String> objectsToAdd, MultiSet<String> objectsToRemove,
			String object, String variable, Pair<Integer, Integer> bounds) {
		int begin=bounds.getFirst(), end=bounds.getSecond();
		String replacedObject;
		boolean changed=false;
		String originalObject = object; 
		for(int i= begin; i<=end; i++)
		{
			
			String previousObject=originalObject;
				replacedObject = replaceObject(variable,
						originalObject, i, previousObject);
			boolean localChanged = !replacedObject.equals(previousObject);
			changed|=localChanged;
				if(localChanged){
					objectsToAdd.add(replacedObject, ms.count(originalObject));
					objectsToAdd.remove(originalObject);
				}
				
		}
		if(changed)
				objectsToRemove.add(originalObject);
	}
	protected static String replaceObject(String variable,
			String originalObject, int i, String previousObject) {
		String replacedObject;
		replacedObject = replaceIndex(variable, i,
				previousObject);

		
		/*if (replacedObject.contains(""+(n+1)))
			throw new ObjectRangeException();*/
		if(originalObject.equals(replacedObject)){
			int n = PlinguaEnvironment.unknownValAssociations.get(variable);
			replacedObject = replaceIndex(n+"", i, previousObject);
		}
		return replacedObject;
	}
	protected static Pair<Integer, Integer> setBeginAndEnd(Range currentRange) {
		int begin=0, end=0;		
		begin = currentRange.beginNumber.intValue();
		if(currentRange.firstOperation==Range.LESS_OPERATION) begin++;
		end = currentRange.endNumber.intValue();
		if(currentRange.secondOperation==Range.LESS_OPERATION) end--;
		return new Pair<Integer, Integer>(begin, end);
	}
	

	public static void expandObject(Set<String> set, List<Range> rangeList, Stack<Token> ranges, PlinguaEnvironment environment, Set<String> nonCheckedVariables) throws PlinguaSemanticsException, ObjectRangeException{
		if(checkSafeMode(environment, rangeList)) return;			
		Set<String> objectsToAdd = new HashSet<String>();
		Set<String> objectsToRemove = new HashSet<String>();
		for(int h=0; h<rangeList.size(); h++){

		for(String object : set){
			ListIterator<Range> rangesIterator = rangeList.listIterator();
			while(rangesIterator.hasNext())
			{
 
				Range currentRange = rangesIterator.next();
				String variable = currentRange.variable;
				//nonCheckedVariables.remove(variable);
				Pair<Integer, Integer> bounds = setBeginAndEnd(currentRange);
				updateSet(objectsToAdd, objectsToRemove, object, variable, bounds);
			}
				
			}
	
			
			set.addAll(objectsToAdd);
			set.removeAll(objectsToRemove);
		}
		for(String object : set)
			checkCompositeValues(object);


		}
	protected static void updateSet(Set<String> objectsToAdd,
			Set<String> objectsToRemove, String object, String variable, Pair<Integer, Integer> bounds) {
		int begin=bounds.getFirst(), end=bounds.getSecond();
		String replacedObject="";
		boolean changed=false;
		String originalObject = object; 
		for(int i= begin; i<=end; i++)
		{
			
			String previousObject=originalObject;
				replacedObject = replaceObject(variable,
						originalObject, i, previousObject);
			boolean localChanged = !replacedObject.equals(previousObject);
			changed|=localChanged;
				if(localChanged){
					objectsToAdd.add(replacedObject);
					objectsToAdd.remove(originalObject);
				}
				
		}
		if(changed)
				objectsToRemove.add(originalObject);
	}

	protected static void checkCompositeValues(String variable) throws ObjectRangeException{
		for(int value : PlinguaEnvironment.unknownValAssociations.values()){
			if(variable.contains(value+""))
				throw new ObjectRangeException("Rule ranged indexes should be simple variables");
			
		}
	}
	protected static String replaceIndex(String variable, int i,
			String previousObject) {
		String replacedObject;
		replacedObject = previousObject.replaceAll("\\{"+variable+"\\}", "\\{"+i+"\\}");
		replacedObject = replacedObject.replaceAll("\\{"+variable+",", "\\{"+i+",");
		replacedObject = replacedObject.replaceAll(","+variable+",", ","+i+",");
		replacedObject = replacedObject.replaceAll(","+variable+"\\}", ","+i+"\\}");
		return replacedObject;
	}
	
	
	public static Range processRanges(Stack<Token> ranges, List<Range> rangeList, PlinguaEnvironment environment, boolean diff, boolean op1, boolean op2, Token rangeToken, Number n1, Number n2, String variable){
	    if (ranges != null)
	    {
	      if (!diff) ranges.push(rangeToken);
	      else ranges.add(0, rangeToken);
	    }
	    Range builtRange = new Range(n1, op1, variable, op2, n2);
	    if(rangeList!=null) rangeList.add(builtRange);
	    return environment.isSafeMode() ? null : builtRange;
	}

	public static LogicOperatedGuard expandGuard(LogicOperatedGuard logicOperatedGuard,
			List<Range> rangeList, Stack<Token> ranges,
			PlinguaEnvironment currentEnvironment,
			Set<String> nonCheckedVariables, boolean joinByAnd) throws PlinguaSemanticsException, ObjectRangeException{
		if(checkSafeMode(currentEnvironment, rangeList)) return null;	
		
		ListIterator<Range> rangesIterator = rangeList.listIterator();
		List<String> guardStrings = new LinkedList<String>();
		guardStrings.add(logicOperatedGuard.toString());
		while(rangesIterator.hasNext()){
			Range currentRange = rangesIterator.next();
			String variable = currentRange.variable;
			Pair<Integer, Integer> bounds=setBeginAndEnd(currentRange);
			
			guardStrings = updateGuards(guardStrings, variable, bounds);

			
		}
		
		LogicOperatedGuard resultingGuard = createResultingGuard(joinByAnd,
				guardStrings);
		
		return resultingGuard;
		
	}
	protected static LogicOperatedGuard createResultingGuard(boolean joinByAnd,
			List<String> guardStrings) throws ObjectRangeException {
		LogicOperatedGuard resultingGuard = null;
		if (!joinByAnd)
			resultingGuard = new OrJoinedGuard();
		else
			resultingGuard = new AndJoinedGuard();
		for(String guardString : guardStrings)
			checkCompositeValues(guardString);
		for (String guardString : guardStrings){
			resultingGuard.addGuard(new AndJoinedGuard(guardString));
		}
		return resultingGuard;
	}
	protected static List<String> updateGuards(List<String> guardStrings,
			String variable, Pair<Integer, Integer> bounds) throws ObjectRangeException {
		int begin=bounds.getFirst(), end=bounds.getSecond();
		List<String> newGuardStrings = new LinkedList<String>();
		for(int i= begin; i<=end; i++)
		{
			for (String currentGuard : guardStrings){
				if (currentGuard == null){
					throw new ObjectRangeException("Range operation <> not allowed in guard ranges");
				}
				String newGuard = replaceIndex(variable, i, currentGuard);
				if(newGuard.equals(currentGuard)){
				int n = PlinguaEnvironment.unknownValAssociations.get(variable);
					newGuard = replaceIndex(n+"", i, newGuard);
				}
				newGuardStrings.add(newGuard);
			}				
		}
		return newGuardStrings;
	}
	
	public static void expandMembranes(List<OuterRuleMembrane> nonLabelledMembranes,
			List<Range> rangeList, Stack<Token> ranges,
			PlinguaEnvironment currentEnvironment,
			Set<String> nonCheckedVariables) throws PlinguaSemanticsException, ObjectRangeException, CloneNotSupportedException{
		if(checkSafeMode(currentEnvironment, rangeList)) return;	
		List<OuterRuleMembrane> membranesToAdd = new LinkedList<OuterRuleMembrane>();
		List<OuterRuleMembrane> membranesToRemove = new LinkedList<OuterRuleMembrane>();
		for(int h=0; h<rangeList.size(); h++){

			for(OuterRuleMembrane membrane : nonLabelledMembranes){
				ListIterator<Range> rangesIterator = rangeList.listIterator();
				while(rangesIterator.hasNext())
				{
	 
					Range currentRange = rangesIterator.next();
					String variable = currentRange.variable;
					//nonCheckedVariables.remove(variable);
					Pair<Integer, Integer> bounds=setBeginAndEnd(currentRange);
					updateMembrane(membranesToAdd, membranesToRemove, membrane,
							variable, bounds);
				}
				
			}
	
			
			nonLabelledMembranes.addAll(membranesToAdd);
			nonLabelledMembranes.removeAll(membranesToRemove);
		}
		for(OuterRuleMembrane membrane : nonLabelledMembranes)
			checkCompositeValues(membrane.getLabel());
	}
	protected static void updateMembrane(
			List<OuterRuleMembrane> membranesToAdd,
			List<OuterRuleMembrane> membranesToRemove,
			OuterRuleMembrane membrane, String variable, Pair<Integer, Integer> bounds)
			throws CloneNotSupportedException {
		int begin=bounds.getFirst(), end=bounds.getSecond();
		boolean changed=false;
		OuterRuleMembrane originalMembrane = membrane; 
		for(int i= begin; i<=end; i++)
		{
			String previousLabel = originalMembrane.getLabel();
			OuterRuleMembrane previousMembrane=(OuterRuleMembrane)originalMembrane.clone();
			replaceMembrane(variable, i, previousMembrane);
			if(previousLabel.equals(previousMembrane.getLabel())){
				if(PlinguaEnvironment.unknownValAssociations.containsKey(variable)){
					int n = PlinguaEnvironment
							.unknownValAssociations
							.get(variable);
					replaceMembrane(n+"", i, previousMembrane);
				}
			}
			boolean localChanged = !previousLabel.equals(previousMembrane.getLabel());
			changed|=localChanged;
			if(localChanged){
				membranesToAdd.add(previousMembrane);
				membranesToAdd.remove(originalMembrane);
			}
			
		}
		if(changed)
			membranesToRemove.add(originalMembrane);
	}
	private static void replaceMembrane(String variable, int i,
			OuterRuleMembrane previousMembrane) {
		// TODO Auto-generated method stub
		previousMembrane.setLabel(new Label(previousMembrane.getLabel().replaceAll(variable, ""+i)));
	}


}
