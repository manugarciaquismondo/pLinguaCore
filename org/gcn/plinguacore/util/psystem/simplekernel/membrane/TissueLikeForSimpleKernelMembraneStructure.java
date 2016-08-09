/* pLinguaCore: A JAVA library for Membrane Computing
 *              http://www.p-lingua.org
 *
 * Copyright (C) 2009  Research Group on Natural Computing
 *                     http://www.gcn.us.es
 *                      
 * This file is part of pLinguaCore.
 *
 * pLinguaCore is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * pLinguaCore is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with pLinguaCore.  If not, see <http://www.gnu.org/licenses/>.
 */


package org.gcn.plinguacore.util.psystem.simplekernel.membrane;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.gcn.plinguacore.util.InfiniteMultiSet;
import org.gcn.plinguacore.util.ShuffleIterator;
import org.gcn.plinguacore.util.UnionIterator;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.cellLike.membrane.CellLikeSkinMembrane;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.membrane.MembraneStructure;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembrane;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembraneStructure;

public class TissueLikeForSimpleKernelMembraneStructure extends TissueLikeMembraneStructure {

	private final static byte KernelBase=1;
	
	
	
	public TissueLikeForSimpleKernelMembraneStructure(MembraneStructure membrane)
	{
		super(membrane);		
	}


	public TissueLikeForSimpleKernelMembraneStructure(
			MembraneStructure membrane, Psystem psystem) {
		super(membrane, psystem);
		// TODO Auto-generated constructor stub
	}


	@Override
	protected byte getClassId(){
		return KernelBase;
	}

	@Override
	protected int getNextId()
	{
		int maxId=0;
		for(int id: cellsById.keySet())
			maxId=Math.max(id, maxId);
		return maxId+1;
			
			//return super.getNextId();//cellsById.size();
	}
	

	@Override
	public Object clone()
	{
		return new TissueLikeForSimpleKernelMembraneStructure(this);
	}


	private boolean secureRemove(TissueLikeMembrane arg0)
	{
		String label = arg0.getLabel();
		List<TissueLikeMembrane>l;
		if (!cells.containsKey(label))
			return false;
		else {
			l=cells.get(label);
			l.remove(arg0);
			cellsById.remove(arg0.getId());
		}
		
		return true;
	}


	protected boolean remove(TissueLikeMembrane arg0)
	{
		// TODO Auto-generated method stub
		
		if (arg0.getLabel().equals(getEnvironmentLabel()))
			throw new IllegalArgumentException("Environment label");
		return secureRemove(arg0);
		
	}
   
	protected int getFirstId(){
		int firstId=Integer.MAX_VALUE;
		for(int key: cellsById.keySet())
			firstId=Math.min(firstId, key);
		return firstId;
	}
	
	public Membrane getFirstMembrane(){
		return cellsById.get(getFirstId());
	}
	

}
