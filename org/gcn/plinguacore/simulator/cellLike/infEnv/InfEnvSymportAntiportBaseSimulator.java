package org.gcn.plinguacore.simulator.cellLike.infEnv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gcn.plinguacore.simulator.cellLike.CellLikeSimulator;
import org.gcn.plinguacore.util.HashInfiniteMultiSet;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.Pair;
import org.gcn.plinguacore.util.psystem.Configuration;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.cellLike.membrane.CellLikeMembrane;
import org.gcn.plinguacore.util.psystem.cellLike.membrane.CellLikeNoSkinMembrane;
import org.gcn.plinguacore.util.psystem.cellLike.membrane.CellLikeSkinMembrane;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.membrane.MembraneStructure;
import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.LeftHandRule;
import org.gcn.plinguacore.util.psystem.rule.checkRule.CheckRule;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.NoDivision;
import org.gcn.plinguacore.util.psystem.rule.tissueLike.Communication;
import org.gcn.plinguacore.util.psystem.rule.tissueLike.DoubleCommunicationTissueLikeRule;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembrane;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembraneStructure;


public class InfEnvSymportAntiportBaseSimulator extends CellLikeSimulator {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -1556761252509150318L;
	private static final int STATE_SELECT_DIVISION=10;
	private static final int STATE_SELECT_COMMUNICATION=20;
	private int state;
	private static final CheckRule noDivision= new NoDivision();
	//private List<Communication>communications;
	private Set<Integer>membranesToCommunicate;
	private Map<Integer,Pair<IRule,ChangeableMembrane>>membranesToDivide;
	
	

	public InfEnvSymportAntiportBaseSimulator(Psystem psystem) {
		super(psystem);
		//communications = new ArrayList<Communication>();
		membranesToCommunicate = new HashSet<Integer>();
		membranesToDivide=new HashMap<Integer,Pair<IRule,ChangeableMembrane>>();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getHead(ChangeableMembrane membrane) {
		if (!(membrane instanceof CellLikeMembrane))
			throw new IllegalArgumentException("Illegal arguments");
		CellLikeMembrane m = (CellLikeMembrane)membrane;
		String str = "";
		if (m.isSkinMembrane())
			str += "ENVIRONMENT MEMBRANE ID: ";
		else
			str += "MEMBRANE ID: ";
		str += m.getId() + ", Label: " + m.getLabelObj() + ", Charge: "
				+ Membrane.getChargeSymbol(m.getCharge());
		return str;
	}
	
	@Override
	protected void printInfoMembrane(ChangeableMembrane membrane) {
		if (!(membrane instanceof CellLikeMembrane))
			throw new IllegalArgumentException("Illegal arguments");
		CellLikeMembrane m = (CellLikeMembrane)membrane;
		getInfoChannel().println("    " + getHead(m));
		
		String strMultiSet = null;
		
		if(m.isSkinMembrane())
		{
			MultiSet<String> multi = m.getMultiSet();
			HashInfiniteMultiSet<String> infMulti = (HashInfiniteMultiSet<String>) multi;
			strMultiSet = infMulti.toStringExcludingInfMult();
		}
		else
			strMultiSet = m.getMultiSet().toString();
		
		getInfoChannel().println("    Multiset: " + strMultiSet);
		
		if (!m.getChildMembranes().isEmpty())
			getInfoChannel().println(
					"    Internal membranes count: "
							+ m.getChildMembranes().size());
		if (!m.isSkinMembrane())
			getInfoChannel().println(
					"    Parent membrane ID: "
							+ ((CellLikeNoSkinMembrane) m).getParentMembrane()
									.getId());

		getInfoChannel().println();

	}
	

	@Override
	protected void microStepInit() {
		// TODO Auto-generated method stub
		super.microStepInit();
		//communications.clear();
		membranesToCommunicate.clear();
		membranesToDivide.clear();
		state=STATE_SELECT_COMMUNICATION;
	}

	@Override
	protected void microStepSelectRules(Configuration cnf, Configuration tmpCnf) {
		// TODO Auto-generated method stub
		for (int i=0;i<2;i++)
		{
			if (i==1)
				state = STATE_SELECT_DIVISION;
			Iterator<? extends Membrane> it = tmpCnf.getMembraneStructure().getAllMembranes().iterator();
			Iterator<? extends Membrane> it1 = cnf.getMembraneStructure().getAllMembranes().iterator();
			while(it.hasNext())
			{
				ChangeableMembrane tempMembrane = (ChangeableMembrane) it.next();
				ChangeableMembrane m = (ChangeableMembrane)it1.next();
				microStepSelectRules(m,tempMembrane);
			}
		}
	}

	@Override
	protected void microStepSelectRules(ChangeableMembrane m,
			ChangeableMembrane temp) {
				
		Iterator<IRule> it = getPsystem().getRules().iterator(temp.getLabel(),
				temp.getCharge(),true);

		while (it.hasNext()) {
			IRule r = it.next();
			
			
			if (state==STATE_SELECT_COMMUNICATION 
					//&& (r instanceof DoubleCommunicationTissueLikeRule) 
					&& noDivision.checkRule(r))
			{
				//DoubleCommunicationTissueLikeRule r1 = (DoubleCommunicationTissueLikeRule)r;
				
				long count = r.countExecutions(temp);
				if (count>0)
				{
					
					/*
					TissueLikeMembraneStructure structure = ((TissueLikeMembrane)m).getStructure();
					membranesToCommunicate.add(m.getId());
					Iterator<Communication>it1 = r1.getCommunications().iterator();
					while(it1.hasNext())
					{
						Communication com = it1.next();
						membranesToCommunicate.add(com.getSecondMembrane().getId());
						Communication c = new Communication(structure.getCell(com.getFirstMembrane().getId()),structure.getCell(com.getSecondMembrane().getId()),com.getRule(),com.getExecutions());
						communications.add(c);
					}
					*/
					
					membranesToCommunicate.add(m.getId());
					
					// this piece of code is inserted to follow the philosophy of tissue-like P systems
					
					/*
					 CellLikeMembrane cm = (CellLikeMembrane) m;
					 

					if (!cm.isSkinMembrane()) {
						
						CellLikeNoSkinMembrane noSkin = (CellLikeNoSkinMembrane) m;
						
						CellLikeMembrane parent = noSkin.getParentMembrane();
						
						membranesToCommunicate.add(parent.getId());
						
					}
					*/
					
					
					selectRule(r, m, count);
					//removeLeftHandRuleObjects(((TissueLikeMembrane)temp).getStructure(), r1.getCommunications());
					removeLeftHandRuleObjects(temp, r, count);
				}
			}
			else
			if (state==STATE_SELECT_DIVISION && !noDivision.checkRule(r) && !membranesToCommunicate.contains(m.getId()) && !membranesToDivide.containsKey(m.getId()))
			{
				if(m.getLabel().equals("environment"))
					throw new IllegalArgumentException("No rules allowed for the 'environment'");
				
				long count = r.countExecutions(temp);
				if (count>0)
				{
						
					count=1;
					selectRule(r, m, count);
					membranesToDivide.put(m.getId(),new Pair<IRule,ChangeableMembrane>(r,m));
					removeLeftHandRuleObjects(temp, r, count);
					
				}
			}
		}
		
	}
	
	
}