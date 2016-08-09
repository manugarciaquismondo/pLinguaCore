package org.gcn.plinguacore.util.psystem.regenerative;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Configuration;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.membrane.MembraneStructure;
import org.gcn.plinguacore.util.psystem.regenerative.membrane.RegenerativeMembrane;
import org.gcn.plinguacore.util.psystem.regenerative.membrane.RegenerativeMembraneStructure;
import org.gcn.plinguacore.util.psystem.simplekernel.SimpleKernelLikePsystem;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembraneStructure;

public class RegenerativePsystem extends SimpleKernelLikePsystem {


	protected Map<Integer,List<Integer>> initialLinks;
	protected Set<String> linkObjects;
	@Override
	public void setMembraneStructure(MembraneStructure membraneStructure) {
		// TODO Auto-generated method stub
		if(membraneStructure instanceof RegenerativeMembraneStructure){
			super.setMembraneStructure(membraneStructure);
		}
		else{
			super.setMembraneStructure(createMembraneStructure(membraneStructure));
		}
	}
	protected RegenerativeMembraneStructure createMembraneStructure(
			MembraneStructure membraneStructure) {
		return new RegenerativeMembraneStructure(membraneStructure);
	}
	@Override
	public boolean addProperty(String object, String property) {
		// TODO Auto-generated method stub
		boolean propertyAdded= super.addProperty(object, property);
		if(property.equals("link")){
			propertyAdded= propertyAdded&&addLinkObject(object);
		}
		return propertyAdded;

	}

	public RegenerativePsystem() {
		super();
		initialLinks=new HashMap<Integer,List<Integer>>();
		linkObjects=new HashSet<String>();
		// TODO Auto-generated constructor stub
	}
	public boolean isLinkObject(String linkObject) {
		// TODO Auto-generated method stub
		return linkObjects.contains(linkObject);
	}
	
	public boolean addLinkObject(String linkObject) {
		if(linkObjects.contains(linkObject)){
			return false;
		}
		else{
			return linkObjects.add(linkObject);
		}
	}
	
	public boolean removeLinkObject(String linkObject) {
		if(!linkObjects.contains(linkObject)){
			return false;
		}
		else{
			return linkObjects.remove(linkObject);
		}
	}
	
	public Set<String> getLinkObjects(){
		return new HashSet<String>(linkObjects);
	}

	public void updateInitialLinks(String linkObject1, String linkObject2) throws PlinguaCoreException{
		if(testLinkObject(linkObject1)&&testLinkObject(linkObject2)){
			linkMembranes(linkObject1, linkObject2);
		}
	}
	
	
	  protected boolean testLinkObject(String l) throws PlinguaCoreException
	  {
	    return isLinkObject(l);
	    
	  }
	  
	protected void linkMembranes(String linkObject1, String linkObject2)
			throws PlinguaCoreException {
		Map<String, MultiSet<String>> initialMultiSets=getInitialMultiSets();
		List<RegenerativeMembrane> membraneList1= checkFoundMembrane(linkObject1, initialMultiSets);
		List<RegenerativeMembrane> membraneList2= checkFoundMembrane(linkObject2, initialMultiSets);
		linkMembranes(membraneList1, membraneList2);
	}
	
	protected List<RegenerativeMembrane> checkFoundMembrane(String linkObject, Map<String, MultiSet<String>> initialMultiSets) throws PlinguaCoreException{
		List<RegenerativeMembrane> membraneList = findMembraneByLinkObject(linkObject, initialMultiSets);
		testMembraneListByLinkObject(linkObject, membraneList);
		return membraneList;
	}
	protected void testMembraneListByLinkObject(String linkObject,
			List<RegenerativeMembrane> membraneList)
			throws PlinguaCoreException {
		if(membraneList==null||membraneList.isEmpty())
			throw new PlinguaCoreException("The specified linking object {"+linkObject+"} is not in any initial multiset");
	}
	
	protected List<RegenerativeMembrane> findMembraneByLinkObject(String linkObject,
			Map<String, MultiSet<String>> initialMultiSets) {
		List<RegenerativeMembrane> membraneList=new LinkedList<RegenerativeMembrane>();
		for(Entry<String, MultiSet<String>> entry: initialMultiSets.entrySet()){
			String membraneLabel=entry.getKey();
			MultiSet<String> multiSet=entry.getValue();
			RegenerativeMembraneStructure membraneStructure=(RegenerativeMembraneStructure)getMembraneStructure();
			if(!membraneStructure.isEnvironmentLabel(membraneLabel)&&multiSet.contains(linkObject)){
				membraneList.add((RegenerativeMembrane)membraneStructure.iterator(membraneLabel).next());
				
			}
		}
		return membraneList;
	}
	
	protected void linkMembranes(List<RegenerativeMembrane> membraneList1,
			List<RegenerativeMembrane> membraneList2) {
		Set<RegenerativeMembrane> membraneBuffer=new HashSet<RegenerativeMembrane>();
		for(RegenerativeMembrane membrane1: membraneList1){
			for(RegenerativeMembrane membrane2: membraneList2){
				if(!membrane1.equals(membrane2)){
					linkMembranes(membrane1, membrane2);
				}
			}
		}
	}
	protected void linkMembranes(RegenerativeMembrane membrane1, RegenerativeMembrane membrane2){

		int membraneId1=membrane1.getId();
		int membraneId2=membrane2.getId();
		List<Integer> linkedMembranes1=getLinkedMembranes(membraneId1);
		List<Integer> linkedMembranes2=getLinkedMembranes(membraneId2);
		linkedMembranes1.add(membraneId2);
		linkedMembranes2.add(membraneId1);
		initialLinks.put(membraneId1, linkedMembranes1);
		initialLinks.put(membraneId2, linkedMembranes2);
	}


	@Override
	public Configuration getFirstConfiguration() {
		// TODO Auto-generated method stub
		Configuration configuration= super.getFirstConfiguration();
		extractLinkObjectsAndAddLinks(configuration);
		return configuration;
	}
	protected void extractLinkObjectsAndAddLinks(Configuration configuration) {
		RegenerativeMembraneStructure structure=(RegenerativeMembraneStructure)configuration.getMembraneStructure();
		Set<RegenerativeMembrane> modifiedMembranes = buildModifiedMembranesBuffer(structure);
		extractLinkObjectsAndAddLinks(structure, modifiedMembranes);
	}
	protected Set<RegenerativeMembrane> buildModifiedMembranesBuffer(
			RegenerativeMembraneStructure structure) {
		Set<RegenerativeMembrane> modifiedMembranes=new HashSet<RegenerativeMembrane>();
		for(Membrane membrane:structure.getAllMembranes()){
			modifiedMembranes.add((RegenerativeMembrane)membrane);
			
		}
		return modifiedMembranes;
	}
	protected void extractLinkObjectsAndAddLinks(
			RegenerativeMembraneStructure structure,
			Set<RegenerativeMembrane> modifiedMembranes) {
		for(RegenerativeMembrane membrane: modifiedMembranes){
			if(!structure.isEnvironmentLabel(membrane.getLabel())){
				structure.remove(membrane);
				try {
					membrane.setLinkObjects(getLinkObjects());
					membrane.extractLinkObject();
					addLinks(membrane);
				} catch (PlinguaCoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				structure.add(membrane);
			}
		}
	}
	public List<Integer> getLinkedMembranes(int membraneId) {
		if(!this.initialLinks.containsKey(membraneId)){
			initialLinks.put(membraneId, new LinkedList<Integer>());
		}
		return initialLinks.get(membraneId);
	}
	
	protected void addLinks(RegenerativeMembrane membrane) {
		int localeMembraneId=membrane.getId();
		if(initialLinks.containsKey(localeMembraneId)){
			List<Integer> adjacents=initialLinks.get(localeMembraneId);
			for(Integer membraneId: adjacents){
				membrane.addLink(membraneId);
			}
		}
	}
	
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return coreString()+printLinkObjects()+printInitialLinks();
	}
	protected String printInitialLinks() {
		// TODO Auto-generated method stub
		return "\nInitial links:\n"+initialLinks+"\n";
	}
	private String printLinkObjects() {
		// TODO Auto-generated method stub
		return "\nLink objects:\n"+linkObjects+"\n";
	}
	protected String coreString() {
		return super.toString();
	}
}
