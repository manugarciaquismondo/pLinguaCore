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
package org.gcn.plinguacore.parser.output.promela;


import java.io.OutputStream;
import java.io.Writer;

import org.gcn.plinguacore.util.psystem.Psystem;

import org.gcn.plinguacore.parser.output.promela.BasePromelaOutputParser;
import org.gcn.plinguacore.parser.output.promela.PromelaOutputParser;
/**
 * This class creates the output parser for a Kernel P-System
 * 
 *  @author Research Group on Natural Computing (http://www.gcn.us.es)
 * 
 */
public class PromelaOutputParserFactory extends BasePromelaOutputParser {

	@Override
	public final boolean parse(Psystem psystem, OutputStream stream) {
		throw new UnsupportedOperationException();
	}

	@Override
	public final boolean parse(Psystem psystem, Writer stream) {
		if (psystem==null) 
			return false;
		if (psystem.getAbstractPsystemFactory().getModelName().equals("simple_kernel_psystems")) {
			BasePromelaOutputParser op = new PromelaOutputParser();
			op.setSimulationStepCount(this.getSimulationStepCount());
			return op.parse(psystem, stream);
		}
			
		return false;
	}
}
