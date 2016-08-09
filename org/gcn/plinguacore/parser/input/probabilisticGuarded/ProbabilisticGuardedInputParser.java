package org.gcn.plinguacore.parser.input.probabilisticGuarded;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.gcn.plinguacore.parser.input.simplekernel.KernelInputParser;
import org.gcn.plinguacore.parser.input.simplekernel.Kernel_Simulator_Lexer;
import org.gcn.plinguacore.parser.input.simplekernel.Kernel_Simulator_Parser;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.probabilisticGuarded.DummyMarkers;
import org.gcn.plinguacore.util.psystem.probabilisticGuarded.ProbabilisticGuardedPsystem;

public class ProbabilisticGuardedInputParser extends KernelInputParser {

	@Override
	protected Psystem parsePsystemWithANTLR(CommonTokenStream tokens)
			throws RecognitionException {
		// TODO Auto-generated method stub
		Probabilistic_Guarded_Simulator_Parser parser = new Probabilistic_Guarded_Simulator_Parser(tokens);
		ProbabilisticGuardedPsystem psystem = (ProbabilisticGuardedPsystem) parser.psystem().psystem;
		psystem.removeFlag(DummyMarkers.DUMMY_FLAG);
		return psystem;
	}



}
