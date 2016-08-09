/* 
 * pLinguaCore: A JAVA library for Membrane Computing
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

package org.gcn.plinguacore.util.psystem.spiking.membrane;

import java.util.ArrayList;
import java.util.List;

import org.gcn.plinguacore.util.Pair;
import org.gcn.plinguacore.util.psystem.spiking.SpikingConstants;


public class ArcInfo {

	// Attributes
	
	private Long weight = 1L;

	private List<String> astList = null;	// should be converted to a Map<String,Long> to implement budding & division
	private List<String> astCtrlList = null;
	private String astType = null;
	private Long spikesInput = 0L;
	private Long spikesOutput = 0L;
	private boolean inhibited = false;
	private String object = null;
	
	private Integer sourceId = -1;
	private Integer targetId = -1;
	
	public ArcInfo(Integer sourceId, Integer targetId)
	{
		
		if(sourceId >= 0 && targetId >= 0)
		{
			this.setSourceId(sourceId);
			this.setTargetId(targetId);
			this.astList = new ArrayList<String>();
			this.astCtrlList = new ArrayList<String>();
			this.astType = new String("none");
			this.object = new String(SpikingConstants.spikeSymbol);
		}
		else
			throw new IllegalArgumentException("Membrane Identifiers must be equal or greater than zero.");
		
		
	}
	
	public ArcInfo(Integer sourceId, Integer targetId, ArcInfo a)
	{
		// this method has to be a real clone
		
		this.setSourceId(sourceId);
		this.setTargetId(targetId);
		this.weight = new Long(a.weight);
		this.astType = new String(a.astType);
		this.object = new String(a.object);

		this.astList = (List<String>) ((ArrayList<String>)a.astList).clone();
		this.astCtrlList = (List<String>) ((ArrayList<String>)a.astCtrlList).clone();

		// we can assign these attributes safely since the clone is done when the original arc is not involved in a firing
		
		this.spikesInput = new Long(a.spikesInput);
		this.spikesOutput = new Long(a.spikesOutput);
		this.inhibited = new Boolean(a.inhibited);

		
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	public Integer getSourceId() {
		return sourceId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public Integer getTargetId() {
		return targetId;
	}
	
	public Long getSpikesInput() {
		return spikesInput;
	}

	public void setSpikesInput(Long spikesInput) {
		this.spikesInput = spikesInput;
	}

	public Long getSpikesOutput() {
		return spikesOutput;
	}

	public void setSpikesOutput(Long spikesOutput) {
		this.spikesOutput = spikesOutput;
	}

	public boolean getInhibited() {
		return inhibited;
	}

	public void setInhibited(boolean inhibited) {
		this.inhibited = inhibited;
	}

	
	public void setAstrocyteList(List<String> astList) {
		this.astList = astList;
	}

	public List<String> getAstrocyteList() {
		return this.astList;
	}
	
	public void setAstrocyteCtrlList(List<String> astCtrlList) {
		this.astCtrlList = astCtrlList;
	}

	public List<String> getAstrocyteCtrlList() {
		return this.astCtrlList;
	}

	public String getAstType()
	{
		return astType;
	}
	
	public void setAstType(String astType)
	{
		this.astType = astType;
	}
	
	public String getObject()
	{
		return object;
	}
	
	public void setObject(String object)
	{
		this.object = object;
	}
	
	public void setWeight(Long weight) {
		this.weight = weight;
	}

	public Long getWeight() {
		return weight;
	}
	
	public void setArcContents(Pair<Long,String> content)
	{
		Long spikes = content.getFirst();
		String object = content.getSecond();
		
		this.spikesInput = spikes*this.weight;
		this.object = object;
	}
	
	public Pair<Long,String> restartArcState()
	{
		if(this.getAstType().equals("none"))				// if there is no astrocyte, we bypass from input to output buffer
			this.setSpikesOutput(this.getSpikesInput());
		
		Long spikes = this.getSpikesOutput();
		String object = this.getObject();
		boolean inhibited = this.getInhibited();
		
		this.setSpikesInput(0L);
		this.setSpikesOutput(0L);
		this.setObject(SpikingConstants.spikeSymbol);
		this.setInhibited(false);
		
		if(inhibited)
			spikes = 0L;
		
		Pair<Long,String> result = new Pair<Long,String>(spikes,object);
		
		return result;
	}

	@Override
	public String toString()
	{
		String str = new String("");
		
		str += "<" + "edges=" + "<" + sourceId + "," + targetId + ">" + "," + "w=" + weight + "," + "ast=" + astList + ">";
		
		return str;
		
	}

	
}
