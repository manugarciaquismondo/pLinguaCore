package org.gcn.plinguacore.util.psystem.regenerative.membrane;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.gcn.plinguacore.util.HashMultiSet;
import org.gcn.plinguacore.util.InmutableMultiSet;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Label;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.regenerative.RegenerativePsystem;
import org.gcn.plinguacore.util.psystem.simplekernel.membrane.SimpleKernelLikeMembrane;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembrane;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembraneStructure;

public class RegenerativeMembrane extends TissueLikeMembrane {

	protected Set<Integer> adjacents;
	protected String linkObject;
	private Set<String> linkObjects;
	protected RegenerativeMembrane(Membrane membrane,
			TissueLikeMembraneStructure structure, String linkObject, boolean checkLink, Set<String> linkObjects) throws PlinguaCoreException {
		super(membrane, structure);
		initializeAdjacentsAndPsystem(structure, linkObjects);
		checkLinkIfSet(structure, linkObject, checkLink);
		// TODO Auto-generated constructor stub
	}


	public RegenerativeMembrane(String label, MultiSet<String> multiSet,
			TissueLikeMembraneStructure structure, String linkObject, boolean checkLink, Set<String> linkObjects) throws PlinguaCoreException {
		super(label, multiSet, structure);
		initializeAdjacentsAndPsystem(structure, linkObjects);
		checkLinkIfSet(structure, linkObject, checkLink);
	}

	public RegenerativeMembrane(String label, MultiSet<String> multiSet,
			TissueLikeMembraneStructure structure, boolean checkLink, Set<String> linkObjects) throws PlinguaCoreException {
		super(label, multiSet, structure);
		initializeAdjacentsAndPsystem(structure, linkObjects);
		if(checkLink){
			extractLinkObject();
			setLinkObject(structure, linkObject);
		}
		// TODO Auto-generated constructor stub
	}
	
	public RegenerativeMembrane(ChangeableMembrane membrane,
			TissueLikeMembraneStructure structure, boolean checkLink, Set<String> linkObjects) throws PlinguaCoreException {
		this(membrane.getLabel(), membrane.getMultiSet(), structure, checkLink, linkObjects);
		// TODO Auto-generated constructor stub
	}
	
	public RegenerativeMembrane(ChangeableMembrane membrane,
			TissueLikeMembraneStructure structure, boolean checkLink) throws PlinguaCoreException {
		this(membrane.getLabel(), membrane.getMultiSet(), structure, checkLink, null);
		// TODO Auto-generated constructor stub
	}


	protected void initializeAdjacentsAndPsystem(
			TissueLikeMembraneStructure structure, Set<String> linkObjects) {
		adjacents=new HashSet<Integer>();
		this.linkObjects=linkObjects;
	}



	protected void checkLinkIfSet(TissueLikeMembraneStructure structure,
			String linkObject, boolean checkLink) throws PlinguaCoreException {
		if(checkLink){
			setLinkObject(structure, linkObject);
		}
		
	}
	
	

	public boolean linkObjectMatches(String inputLinkObject){
		if(inputLinkObject==null)
			return linkObject==null;
		else
			return inputLinkObject.equals(linkObject);
	}



	public void extractLinkObject() throws PlinguaCoreException {
		// TODO Auto-generated method stub
		if(linkObjects==null){
			throw new PlinguaCoreException("Link object set null in membrane during link object extraction");
		}
		for(String object:getMultiSet().entrySet()){
			if(linkObjects.contains(object)){
				if(linkObject==null){
					linkObject=object;
				}
				else{
					if(!linkObject.equals(object)){
						throw new PlinguaCoreException("Object {"+object+"} in multiset "+multiSet+" is a link object, but the membrane link object is "+linkObject);
					}
			}
			}
		}
	}






	protected TissueLikeMembrane copyWithStructure() {
		try {
			return createRegenerativeMembrane(this,structure, linkObject, true, linkObjects);
		} catch (PlinguaCoreException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}



	public void setLinkObject(String linkObject, Set<String> linkObjects){
		if(linkObjects!=null&&linkObjects.contains(linkObject)){
			if(this.linkObject!=null&&multiSet.contains(this.linkObject)){
				multiSet.remove(this.linkObject);
				multiSet.add(linkObject);
			}
			this.linkObject=linkObject;
		}
		
	}
	
	


	private void setLinkObject(TissueLikeMembraneStructure structure,
			String linkObject) throws PlinguaCoreException {
			RegenerativeMembraneStructure regenerativeStructure= checkAndConvertStructure(structure);
			regenerativeStructure.add(this);
			initializeAdjacentsAndPsystem(structure, linkObjects);
			extractAndRemoveLinkObject(linkObject);

	}


	protected void extractAndRemoveLinkObject(String linkObject)
			throws PlinguaCoreException {
		if(linkObject!=null&&!linkObject.isEmpty()){
			checkAndRemoveLinkObject(linkObject);
		}
		else{
			extractLinkObject();
			if(linkObject==null||linkObject.isEmpty()){
				throw new PlinguaCoreException("The link object for a regenerative membrane cannot be null nor empty");
			}
		}
		checkExtraLinkObjects();
	}






	protected void checkAndRemoveLinkObject(String linkObject)
			throws PlinguaCoreException {
		boolean isLinkObject=linkObjects.contains(linkObject);
		if(isLinkObject){
			this.linkObject=linkObject;
		}
		else{
			throw new PlinguaCoreException("Object "+linkObject+" is not a link object");
		}
	}






	protected RegenerativeMembraneStructure checkAndConvertStructure(
			TissueLikeMembraneStructure bufferStructure)
			throws PlinguaCoreException {
		if(!(bufferStructure instanceof TissueLikeMembraneStructure)){
			throw new PlinguaCoreException("The input membrane structure must be a tissue-like membrane structure");
		}
		if(!(bufferStructure instanceof RegenerativeMembraneStructure)){
			return createMembraneStructure(bufferStructure);
		}
		else{
			return (RegenerativeMembraneStructure)bufferStructure;
		}
	}


	protected RegenerativeMembraneStructure createMembraneStructure(
			TissueLikeMembraneStructure bufferStructure) {
		return new RegenerativeMembraneStructure(bufferStructure);
	}
	
	private void checkExtraLinkObjects(){
		Set<String> extraLinkObjects=new HashSet<String>();
		if(multiSet instanceof InmutableMultiSet){
			multiSet=new HashMultiSet<String>(multiSet);
		}
		for(String object:multiSet.entrySet()){
			if(linkObjects.contains(object)&&!object.equals(linkObject)){
				extraLinkObjects.add(object);
			}
		}
		for(String linkObject:extraLinkObjects){
			multiSet.remove(linkObject, multiSet.count(linkObject));
		}
		if(linkObject!=null){
			if(multiSet.contains(linkObject)){
				multiSet.remove(linkObject, multiSet.count(linkObject));
			}
			multiSet.add(linkObject);
			
		}
		// TODO Auto-generated method stub
		
	}




	public boolean isLinkable(RegenerativeMembrane linkedMembrane, String linkObject){
		boolean isLinkable= !isAdjacent(linkedMembrane);
		if(isLinkable){
			checkAndExtractLinkObject(linkObject);
			isLinkable=getLinkObject().equals(linkObject);
		}
		return isLinkable;
	}

	public boolean addLink(RegenerativeMembrane linkedMembrane, String linkObject){
		if(!adjacents.contains(linkedMembrane.getId())){
			checkAndExtractLinkObject(linkObject);
			if(testLinkObject(linkObject)){
				adjacents.add(linkedMembrane.getId());
				linkedMembrane.addLink(this);
				return true;
			}
		}
		return false;
	}


	protected boolean testLinkObject(String linkObject) {
		return this.linkObject.equals(linkObject);
	}


	protected void checkAndExtractLinkObject(String linkObject) {
		if(linkObject==null)
			if(linkObjects==null){
				setLinkObjects(((RegenerativePsystem)this.structure.getPsystem()).getLinkObjects());
			}
			try {
				extractLinkObject();
			} catch (PlinguaCoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public String getLinkObject(){
		return linkObject;
	}
	
	public void setLinkObjects(Set<String> linkObjects){
		if(linkObjects==null)
			throw new IllegalArgumentException("The set of link objects cannot be null");
		if(this.linkObjects==null){
			this.linkObjects=linkObjects;
			if(linkObject==null){
				try{
					extractLinkObject();
				}
				catch(PlinguaCoreException e){
					throw new IllegalArgumentException("The set of objects "+linkObjects+" is not valid");
				}
			}
		}
		checkExtraLinkObjects();
	}
	public List<Integer> getLinkedMembranes(){
		return new LinkedList<Integer>(adjacents);
	}
	
	public List<RegenerativeMembrane> getLinkedMembrane(Label label){
		List<RegenerativeMembrane> bufferMembranes=new LinkedList<RegenerativeMembrane>();
		for(int membraneId: adjacents){
			RegenerativeMembrane membrane=(RegenerativeMembrane)structure.getCell(membraneId);
			if(membrane.getLabelObj().equals(label)){
				bufferMembranes.add(membrane);
			}
		}
		return bufferMembranes;
	}
	
	
	public void removeLink(RegenerativeMembrane linkedMembrane){
		removeLinkedMembrane(linkedMembrane.getId(), this);
		removeLinkedMembrane(getId(), linkedMembrane);
	}


	protected void removeLinkedMembrane(int membraneId, RegenerativeMembrane linkedMembrane) {
		if(linkedMembrane.adjacents.contains(membraneId)){
			linkedMembrane.adjacents.remove(membraneId);
		}
	}
	
	public void removeLink(int membraneId){
		removeLinkedMembrane(membraneId, this);
	}
	
	public void addLink(RegenerativeMembrane linkedMembrane){
		addLinkedMembrane(linkedMembrane.getId(), this);
		addLinkedMembrane(getId(), linkedMembrane);
	}


	protected void addLinkedMembrane(int membraneId, RegenerativeMembrane linkedMembrane) {
		if(!linkedMembrane.adjacents.contains(membraneId)){
			linkedMembrane.adjacents.add(membraneId);
		}
	}
	
	public void addLink(int membraneId){
		addLinkedMembrane(membraneId, this);
	}

	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		RegenerativeMembrane membrane=null;
		try{
			membrane=createRegenerativeMembrane(this, getStructure(), linkObject, false, linkObjects);
		}
		catch(PlinguaCoreException e){};
		membrane.setId(this.getId());
		membrane.adjacents=copyAdjacents();
		membrane.linkObject=new String(linkObject);
		return membrane;
	}

	protected RegenerativeMembrane createRegenerativeMembrane(
			RegenerativeMembrane regenerativeMembrane,
			TissueLikeMembraneStructure structure, String linkObject2,
			boolean b, Set<String> linkObjects2) throws PlinguaCoreException {
		// TODO Auto-generated method stub
		return new RegenerativeMembrane(regenerativeMembrane,
				structure, linkObject2,b, linkObjects2);
	}


	protected Set<Integer> copyAdjacents() {
		// TODO Auto-generated method stub
		Set<Integer> copiedAdjancents=new HashSet<Integer>();
		for(int membrane: adjacents){
			copiedAdjancents.add(membrane);
		}
		return copiedAdjancents;
	}
	
	public void copyAdjacents(RegenerativeMembrane linkedMembrane) {
		// TODO Auto-generated method stub
		for(int membraneId: adjacents){
			RegenerativeMembrane membrane=(RegenerativeMembrane)structure.getCell(membraneId);
			linkedMembrane.addLink(membrane);
		}
	}
	
	public boolean isAdjacent(RegenerativeMembrane linkedMembrane){
		return adjacents.contains(linkedMembrane.getId());
	}






	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return printLinkObject()+toStringMembraneCore()+printAdjacents();
	}


	private String printLinkObject() {
		// TODO Auto-generated method stub
		return "<"+linkObject+">";
	}


	protected String printAdjacents() {
		// TODO Auto-generated method stub
		return printAdjacentsHeader()+printAdjacentsBody();
	}
	
	protected String printAdjacentsBody(){
		return adjacents+"";
	}
	
	protected String printAdjacentsHeader(){
		return "("+getId()+"):";
	}


	protected String toStringMembraneCore() {
		return super.toString();
	}






	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode() *prime +
				+ ((linkObject == null) ? 0 : linkObject.hashCode());
	}






	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		boolean superEquals=super.equals(obj);
		if(!superEquals) return false;
		String targetLinkObject=((RegenerativeMembrane)obj).linkObject;
		if((linkObject==null)!=(targetLinkObject==null)){
			return false;
		}
		else{
			if(targetLinkObject!=null)
				return targetLinkObject.equals(linkObject);
			return true;
		}
		
	}
	
	
}
