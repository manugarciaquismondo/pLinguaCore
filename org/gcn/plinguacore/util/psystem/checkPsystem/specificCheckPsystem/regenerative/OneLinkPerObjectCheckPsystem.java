package org.gcn.plinguacore.util.psystem.checkPsystem.specificCheckPsystem.regenerative;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.checkPsystem.CheckPsystem;
import org.gcn.plinguacore.util.psystem.checkPsystem.DecoratorCheckPsystem;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.membrane.MembraneStructure;
import org.gcn.plinguacore.util.psystem.regenerative.RegenerativePsystem;
import org.gcn.plinguacore.util.psystem.regenerative.membrane.RegenerativeMembrane;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembraneStructure;

public class OneLinkPerObjectCheckPsystem extends DecoratorCheckPsystem {

	protected String causesAsString;
	protected RegenerativePsystem psystem;


	public OneLinkPerObjectCheckPsystem(CheckPsystem cr) {
		super(cr);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean checkSpecificPart(Psystem r) {
		// TODO Auto-generated method stub
		if(r instanceof RegenerativePsystem){
			psystem=(RegenerativePsystem)r;
		}
		causesAsString="";
		TissueLikeMembraneStructure structure=(TissueLikeMembraneStructure)r.getMembraneStructure();
		boolean checked=true;
		for(Entry<String, MultiSet<String>> entry :r.getInitialMultiSets().entrySet()){
			checked = testMembraneAndConcatenateErrorMessages(structure,
					checked, entry.getKey(), entry.getValue());
		}
		return checked;
	}

	protected boolean testMembraneAndConcatenateErrorMessages(
			TissueLikeMembraneStructure structure, boolean checked,
			String label, MultiSet<String> multiset) {
		try{
			testMembraneForLinkObject(structure, multiset, label);
		}
		catch(PlinguaCoreException e){
			causesAsString+="\n"+e.getMessage();
			checked=false;
		}
		return checked;
	}

	protected void testMembraneForLinkObject(
			TissueLikeMembraneStructure structure, MultiSet<String> multiset, String label)
					throws PlinguaCoreException {
		if((!isEnvironmentLabel(structure, label))&&structure.iterator(label).hasNext()){
			testForOneLinkObjectPerMultiSet(multiset, null);
		}
		if(isEnvironmentLabel(structure, label)){
			checkNoEnvironmentLinkObject(multiset, null, label);
		}
	}

	protected boolean isEnvironmentLabel(
			TissueLikeMembraneStructure structure, String label) {
		return label.equals(structure.getEnvironmentLabel());
	}

	private void testForOneLinkObjectPerMultiSet(MultiSet<String> multiset, String linkObject) throws PlinguaCoreException {

		if(linkObject==null||linkObject.isEmpty()){
			String foundLinkObject=null;
			checkForLinkObject(multiset, foundLinkObject);
		}
		else{
			checkForLinkObject(multiset, linkObject);
		}


	}

	protected void checkForLinkObject(MultiSet<String> multiset, String foundLinkObject)
			throws PlinguaCoreException {
		String localeLinkObject=foundLinkObject;
		localeLinkObject = searchLinkObject(multiset, localeLinkObject);
		if(localeLinkObject==null){
			throw new PlinguaCoreException("No link object has been found for multiset "+multiset);
		}
	}

	protected String searchLinkObject(MultiSet<String> multiset,
			String localeLinkObject) throws PlinguaCoreException {
		for(String object: multiset.entrySet()){
			if(psystem.isLinkObject(object)){
				if(localeLinkObject==null)
					localeLinkObject=object;
				else{
					throw new PlinguaCoreException("Multiset "+multiset+" can only have one link object, but has "+localeLinkObject+" and "+object);
				}
			}
		}
		return localeLinkObject;
	}

	protected void checkNoEnvironmentLinkObject(MultiSet<String> multiset, String foundLinkObject, String label)
			throws PlinguaCoreException {
		if(foundLinkObject!=null&&!foundLinkObject.isEmpty()){
			throw new PlinguaCoreException("The environment (labelled as "+label+") cannot have a link object: "+foundLinkObject);
		}
		String environmentLinkObject=searchLinkObject(multiset, foundLinkObject);
		if(environmentLinkObject!=null&&!environmentLinkObject.isEmpty()){
			throw new PlinguaCoreException("The environment (labelled as "+label+") cannot have a link object: "+environmentLinkObject);
		}

	}

	@Override
	protected String getSpecificCause() {
		// TODO Auto-generated method stub
		return causesAsString;
	}



}
